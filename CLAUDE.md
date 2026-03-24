# FFDC 2026 - Finn Found Da Chest: The Land of Ooo Chronicles

## Project Overview
2D top-down Adventure Time-themed game built with LibGDX (Java). Three game modes: Speedrun, Story, Exploration.

## Build Commands
- **Run:** `./gradlew desktop:run`
- **Compile:** `./gradlew compileJava`
- **Test:** `./gradlew test`
- **Package JAR:** `./gradlew desktop:dist`
- **Clean:** `./gradlew clean`

## Tech Stack
- **Language:** Java 21 (compiled with `--release 21`, runs on JDK 21+)
- **Framework:** LibGDX 1.13.0
- **Build:** Gradle 8.14 (Kotlin DSL)
- **Audio:** LibGDX Audio (OpenAL)
- **Maps:** Tiled (.tmx) format
- **CI:** GitHub Actions

## Project Structure
```
core/src/main/java/dev/gaspard/ffdc/   # Platform-agnostic game code
  FFDCGame.java          # Main ApplicationListener
  core/                  # GameContext, EventBus, GameConstants
  screen/                # LibGDX Screens (one per game state)
  world/                 # Map loading, tile registry
  entity/                # Entity, Player, NPC
  system/                # CollisionSystem, AnimationSystem, etc.
  input/                 # GameInputProcessor, KeyBindings, InputAction
  audio/                 # AudioManager
  asset/                 # AssetRegistry
  ui/                    # HUD, DialogueBox, PauseMenu
  gamemode/              # GameMode interface + SpeedrunMode, StoryMode, ExplorationMode

desktop/src/.../desktop/ # Desktop launcher (Lwjgl3Application)
assets/                  # All game assets (sprites, audio, maps, fonts)
```

## Coding Conventions
1. **No magic numbers** — all constants in `GameConstants.java`
2. **Enums over Strings** — Direction, GameObjectType, InputAction, SoundEffect, MusicTrack
3. **EventBus for cross-system communication** — UI never mutates entity state directly
4. **One Screen per game state** — no integer state tracking
5. **All assets loaded via AssetRegistry** — no direct file loading elsewhere
6. **ZQSD + WASD + Arrow keys** — always support all three input schemes

## Gradle Config Notes
- JDK 21 path configured in `gradle.properties` (`org.gradle.java.home`)
- LWJGL natives: only x64 Windows (x86 excluded due to Windows Defender false positive)
- Assets directory is `assets/` at project root, referenced by `desktop/build.gradle.kts`

## Asset Organization
```
assets/
  sprites/tiles/       # 67 tile PNGs
  sprites/objects/     # 22 object PNGs (key, door, chest, boots, sword, etc.)
  sprites/player/      # Walking, attacking, guarding sprites (boy, finn, cat)
  sprites/pnjs/        # NPC sprites (jake)
  audio/music/         # Background music WAVs
  audio/sfx/           # Sound effect WAVs
  maps/                # Map data files (migrating from .txt to .tmx)
```

## Agent Roles (for multi-agent workflow)
| Agent | Model | Responsibility |
|-------|-------|---------------|
| PO FFDC | sonnet | User stories, backlog, feature acceptance |
| Architect | sonnet | Architecture review, interface design, ADRs |
| Dev Core | sonnet | Game systems, entities, game logic |
| Dev UI | sonnet | Screens, HUD, menus, Scene2D |
| Asset Pipeline | haiku | Gradle tasks, TexturePacker, asset organization |
| QA | haiku | JUnit tests, bug reports, CI |
| Doc Writer | haiku | CLAUDE.md, README, CHANGELOG |
