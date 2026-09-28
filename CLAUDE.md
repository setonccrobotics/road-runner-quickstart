# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

An FTC (FIRST Tech Challenge) robot codebase built on the [Road Runner 1.0 quickstart](https://rr.brott.dev/docs/v1-0/tuning/) (FTC SDK 12.0). The team-specific code lives in `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/SCC/`. Everything else in `teamcode/` (drive classes, localizers, `tuning/`, `messages/`) is Road Runner quickstart code, tuned for this robot.

## Build commands

Gradle wrapper at the repo root (Android Gradle Plugin 8.13, Gradle 9.1; needs JDK 17+ — Android Studio's bundled `jbr` works, set `JAVA_HOME` to it for command-line builds; no unit tests in the project):

- Build the robot app: `./gradlew :TeamCode:assembleDebug`
- Install to a connected Control Hub / phone over ADB: `./gradlew :TeamCode:installDebug`
- Run a MeepMeep path visualization (desktop Java, no robot needed): run the `main` of a class in `MeepMeepTesting/src/main/java/com/example/meepmeeptesting/` from Android Studio, or `./gradlew :MeepMeepTesting:compileJava` to just check it compiles.

`FtcRobotController/` is the stock FTC SDK app module — don't modify it (other than enabling samples). SDK updates are merged from FIRST's `FtcRobotController` release tags (e.g. `v12.0`); the repo shares history with it, and Road Runner quickstart updates come from `acmerobotics/road-runner-quickstart`. `build.common.gradle` / `build.dependencies.gradle` are shared SDK build files; project dependency additions go in `TeamCode/build.gradle`.

## Architecture

**OpModes** are discovered by annotation: `@Autonomous(name=..., group="SCC")` or `@TeleOp(...)` on a `LinearOpMode` subclass. `@Disabled` hides one from the Driver Station (many old-season autos are disabled rather than deleted). The `name` is what appears on the Driver Station.

**Drive / localization** (quickstart layer):
- `MecanumDrive` is the drive used everywhere. Its `Params` (`PARAMS`) hold the tuned feedforward/feedback gains and constraints; it's `@Config`, so values can be live-tuned from FTC Dashboard (`http://192.168.43.1:8080/dash`) but must be copied back into source to persist.
- The localizer is selected in the `MecanumDrive` constructor — currently `ThreeDeadWheelLocalizer`. Alternatives (`TwoDeadWheelLocalizer`, `PinpointLocalizer`, `OTOSLocalizer`, `DriveLocalizer`) exist for other setups.
- `tuning/TuningOpModes` registers the Road Runner tuning OpModes against `DRIVE_CLASS`.

**Team code layering** (`SCC/`):
- *Hardware subsystems* (`RobotConveyor`, `RobotLiftServo`, `RobotVision`, `LedStrip`, etc.) wrap `hardwareMap.get(...)` calls and expose imperative methods. Hardware device names in these constructors must match the Driver Station robot configuration exactly.
- *`RobotVision`* wraps the SDK AprilTag processor. Since SDK 12, `getDetections()` returns `AprilTagSingleDetection` or `AprilTagClusterDetection`; `id`, `metadata` and `center` exist only on single detections, so check with `instanceof` before reading them.
- *`RobotControl`* adapts subsystem methods into Road Runner `Action`s (inner classes implementing `Action.run`, plus factory methods like `launchBalls()`, `conveyorOn()`) so they can be sequenced with drive trajectories. Note these actions block with `Thread.sleep` and return `false` (run once), so they don't run concurrently with driving inside a `ParallelAction`.
- *Autonomous OpModes* (`BlueGoal`, `RedLong`, …) follow a common pattern: define `Pose2d` field positions, build each leg with `drive.actionBuilder(pose)...build()` (with custom `TranslationalVelConstraint`/`ProfileAccelConstraint` for slow intake passes), then `waitForStart()` and `Actions.runBlocking(new SequentialAction(...))` interleaving drive legs with `RobotControl` actions. Red/Blue and variant autos (`6BALL`, `NOPark`, `Backup`, `FAR`, …) are copy-and-modify siblings — a change to one usually needs mirroring in its counterparts.
- *TeleOp OpModes* (`Peacemaker` and variants) instantiate `MecanumDrive` plus subsystems directly and drive via `drive.setDrivePowers(new PoseVelocity2d(...))` from gamepad input.

**MeepMeepTesting** is a separate desktop module that mirrors auto paths using `DriveShim` so trajectories can be previewed off-robot. It has no dependency on `TeamCode`, so poses/paths are duplicated by hand and can drift from the real autos.
