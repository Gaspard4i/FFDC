# FFDC 2026 - Finn Found Da Chest: The Land of Ooo Chronicles

## Project Overview
2D top-down Adventure Time-themed game built with LibGDX (Java). Three game modes: Speedrun, Story, Exploration.

## Build Commands
- **Run:** `./gradlew desktop:run`
- **Compile:** `./gradlew compileJava`
- **Test:** `./gradlew test`
- **Coverage:** `./gradlew jacocoTestCoverageVerification`
- **Style check:** `./gradlew spotlessCheck`
- **Package JAR:** `./gradlew desktop:dist`
- **Clean:** `./gradlew clean`

## Tech Stack
- **Language:** Java 21 (compiled with `--release 21`, runs on JDK 21+)
- **Framework:** LibGDX 1.13.0
- **Build:** Gradle 8.14 (Kotlin DSL)
- **Tests:** JUnit 5 + Mockito + AssertJ
- **Coverage:** JaCoCo (90% instruction minimum on entity/, system/, gamemode/, core/event/)
- **Style:** Spotless (Google Java Style) + Checkstyle
- **Audio:** LibGDX Audio (OpenAL)
- **Maps:** Tiled (.tmx) format
- **CI:** GitHub Actions (quality gates: spotless → checkstyle → compile → test → JaCoCo)

## Key Documents — READ THESE
- **`docs/STANDARDS.md`** — Design patterns, coding conventions, architecture rules, testing strategy
- **`docs/agile/WORKFLOW.md`** — Scrum process, ceremonies, sprint structure
- **`docs/agile/BACKLOG.md`** — Product backlog with all user stories
- **`docs/agile/sprints/sprint-N-plan.md`** — Current sprint plan

## Project Structure
```
core/src/main/java/dev/gaspard/ffdc/
  FFDCGame.java          # Main ApplicationListener
  GameConstants.java     # ALL constants here
  core/                  # GameContext, EventBus
  core/event/            # GameEvent, EventBus, all event classes
  screen/                # One Screen per game state (LibGDX State Pattern)
  world/                 # GameMap, TileRegistry, TmxLoader
  entity/                # Entity, Player, NPC, GameObject, EntityFactory
  entity/component/      # Records: Transform, Velocity, Collision, Health, Inventory
  entity/state/          # EntityState interface + Idle, Walking, Attacking, Guarding
  system/                # CollisionSystem, MovementSystem, AnimationSystem, InteractionSystem
  input/                 # GameInputProcessor, KeyBindings, InputAction enum
  audio/                 # AudioManager
  asset/                 # AssetRegistry
  ui/                    # HudRenderer, DialogueBox, PauseMenuOverlay
  gamemode/              # GameMode interface + SpeedrunMode, StoryMode, ExplorationMode

desktop/src/.../desktop/ # DesktopLauncher (Lwjgl3Application)
assets/                  # sprites/, audio/, maps/, fonts/
docs/                    # STANDARDS.md, agile/, adr/
```

## Coding Rules (enforced by CI)
1. **No magic numbers** — all constants in `GameConstants.java`
2. **Enums over Strings** — Direction, GameObjectType, InputAction, SoundEffect, MusicTrack
3. **EventBus for cross-system** — UI NEVER mutates entity state directly
4. **One Screen per state** — no `int gameState`, use `Game.setScreen()`
5. **All assets via AssetRegistry** — no `new Texture()` or `Gdx.files` elsewhere
6. **ZQSD + WASD + Arrows** — all three input schemes simultaneously
7. **Max 30 lines per method**
8. **Javadoc on all public interfaces/classes/methods**
9. **Tests: Given/When/Then, 90% coverage on core packages**
10. **Conventional Commits with scope** — `feat(entity): add health component`

## Commit Convention
```
<type>(<scope>): <imperative description, lowercase, no period>
```
Types: `feat|fix|refactor|test|docs|chore|style|perf|asset`
Scopes: `core|desktop|assets|ci|docs|screen|entity|audio|world|gamemode`

## Agent Roles (5 agents, no duplicates)
| Agent | Model | Responsibility | RACI |
|-------|-------|---------------|------|
| PO FFDC | sonnet | Stories, backlog, acceptance | Accountable: what |
| Architect | sonnet | Interfaces, ADRs, review core/ PRs | Accountable: how |
| Dev Core | sonnet | entity/, system/, gamemode/, world/, audio/, input/ | Responsible: logic |
| Dev UI | sonnet | screen/, ui/, Scene2D, HUD, menus | Responsible: rendering |
| QA+Pipeline | haiku | Tests, JaCoCo, Checkstyle, Spotless, Gradle, assets, docs | Responsible: quality |

## Gradle Config Notes
- JDK 21 path in `gradle.properties` (`org.gradle.java.home`)
- LWJGL natives: x64 Windows only (x86 excluded — Windows Defender false positive)
- Assets: `assets/` at project root

## Git Workflow
- **Branches:** `main` (release) ← `2026` (dev) ← `feature/US-XXX-description`
- **Merge:** squash merge on 2026, merge commit for releases
- **Never push directly to main**
- **Tags:** SemVer (v0.3.0, v0.5.0, v1.0.0)
