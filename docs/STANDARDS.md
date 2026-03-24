# FFDC 2026 — Standards Techniques

> Document de référence architecturale. **Lire avant de coder.**

---

## 1. Design Patterns Obligatoires

| Pattern | Où | Usage |
|---------|-----|-------|
| **State** | `screen/` | Chaque état = un `Screen` LibGDX. Zéro `int gameState`. |
| **State** | `entity/state/` | Entity behavior: `EntityState` interface (enter/update/exit) |
| **Observer** | `core/event/` | `EventBus` synchrone. UI ne mute jamais l'entity directement. |
| **Factory** | `entity/` | `EntityFactory` crée Player, NPC, GameObject. Pas de `new Player()` ailleurs. |
| **Strategy** | `gamemode/` | `GameMode` interface. PlayScreen ignore quel mode est actif. |
| **Component** | `entity/component/` | Composants légers (records) sur les entities. Pas de full ECS. |

### Interdictions absolues
- `int gameState` ou `switch(gameState)` → utiliser `Game.setScreen()`
- `direction = "up"` → utiliser `Direction.UP` (enum)
- `new Texture(...)` hors `AssetRegistry` → interdit
- `System.out.println` → utiliser `Gdx.app.log/error/debug`

---

## 2. Conventions de Codage

### Naming

| Element | Convention | Exemple |
|---------|-----------|---------|
| Classes | PascalCase | `CollisionSystem` |
| Interfaces | PascalCase, pas de `I` | `GameMode` |
| Méthodes | camelCase, verbe d'abord | `pickUpItem()` |
| Variables | camelCase | `playerSpeed` |
| Constantes | SCREAMING_SNAKE | `TILE_SIZE_PX` |
| Packages | lowercase | `dev.gaspard.ffdc.entity.state` |
| Enums | PascalCase type, SCREAMING vals | `Direction.UP` |

### Structure d'une classe (ordre)
1. Constantes `static final`
2. Champs `final` puis mutables
3. Constructeur(s)
4. Override methods
5. Public methods
6. Package-private (pour tests)
7. Private methods

### Règles
- **Javadoc** obligatoire sur toutes les interfaces, classes publiques, méthodes publiques
- **Null** : `@NonNull`/`@Nullable` sur params publics. `Objects.requireNonNull()` en constructeur.
- **Immutabilité** : `record` pour value objects. Champs `final` par défaut.
- **Collections** : `Array<>` de LibGDX dans la game loop (pas `ArrayList`)
- **Exceptions** : runtime only dans update/render. Hiérarchie : `FfdcException` → `AssetLoadException`, `MapParseException`
- **Max 30 lignes** par méthode

---

## 3. Convention de Commits

**Conventional Commits 1.0** strict. Scope obligatoire. Anglais.

```
<type>(<scope>): <description impérative, minuscule, sans point>
```

### Types
`feat` | `fix` | `refactor` | `test` | `docs` | `chore` | `style` | `perf` | `asset`

### Scopes
`core` | `desktop` | `assets` | `ci` | `docs` | `screen` | `entity` | `audio` | `world` | `gamemode`

### Exemples
```
feat(entity): add player health component with damage logic
fix(audio): prevent AudioManager from playing after dispose
test(entity): add unit tests for CollisionSystem tile detection
chore(ci): add JaCoCo coverage report to pipeline
```

### Interdictions
- Commits sans scope
- Commits multi-scope → faire 2 commits
- Messages en français
- WIP commits sur develop/main

---

## 4. Architecture — Dépendances Inter-packages

```
asset/           ← couche zéro, ne dépend de rien
core/            ← dépend de asset/
entity/          ← dépend de core/, asset/
system/          ← dépend de entity/, core/
world/           ← dépend de core/, asset/
audio/           ← dépend de core/, asset/
input/           ← dépend de core/
gamemode/        ← dépend de core/, entity/, system/ (via interfaces)
ui/              ← dépend de core/, asset/   !! JAMAIS entity/ !!
screen/          ← orchestre tout
```

### Interdictions
- `ui/` → `entity/` (communication via EventBus uniquement)
- `entity/` → `screen/` ou `ui/`
- `gamemode/` → `screen/`
- `system/` → `gamemode/`
- `asset/` → rien du projet

---

## 5. Testing

### Coverage : 90% instruction (JaCoCo)

**Obligatoire 90%+ sur :**
- `entity/` | `system/` | `gamemode/` | `core/event/` | `input/`

**Exempté (requiert OpenGL) :**
- `screen/` | `ui/` | `audio/` | `asset/` | `desktop/`

### Frameworks
- JUnit 5 + Mockito + AssertJ

### Convention
- Format : `methodName_givenCondition_expectedResult`
- Structure : Given / When / Then
- Mocker : EventBus, AssetRegistry. **Ne jamais mocker** entities/components.

### TDD Workflow
1. RED : écrire le test qui décrit le comportement
2. GREEN : minimum de code pour passer
3. REFACTOR : conventions, extraction
4. Commit quand GREEN + coverage ≥ 90%

---

## 6. CI/CD Pipeline

### Quality Gates (bloquants)
1. **Spotless** — formatage Google Java Style
2. **Checkstyle** — conventions naming/structure
3. **Compile** — compilation sans erreur
4. **Tests** — JUnit 5 (continue-on-error: FALSE)
5. **JaCoCo** — coverage ≥ 90% sur packages ciblés

### Branch Protection
- `2026` : status checks required, no force push
- `main` : idem + 1 review minimum

### Merge Strategy
- Squash merge sur develop/2026
- Merge commit pour releases

---

## 7. Équipe d'Agents

| Agent | Modèle | Rôle | RACI |
|-------|--------|------|------|
| **PO FFDC** | Sonnet | Stories, backlog, acceptance | Accountable quoi |
| **Architect** | Sonnet | Interfaces, ADR, review core/ | Accountable comment |
| **Dev Core** | Sonnet | entity/, system/, gamemode/, world/, audio/, input/ | Responsible logique |
| **Dev UI** | Sonnet | screen/, ui/, Scene2D, HUD | Responsible rendu |
| **QA + Pipeline** | Haiku | Tests JUnit, JaCoCo, Checkstyle, Spotless, Gradle, assets, docs | Responsible qualité |

**5 agents, pas de doublon.** La qualité est assurée par les quality gates CI.

### Workflow inter-agents (par feature)
```
1. PO → user story + critères d'acceptance
2. Architect → interfaces Java + contrats
3. QA → tests (RED phase TDD)
4. Dev Core/UI → implémente (GREEN phase)
5. QA → vérifie CI (coverage, lint)
6. Architect → valide PR si touche core/
7. PO → valide acceptance
```

### Communication
- Fichiers markdown dans `docs/agile/inbox/`
- Escalade : agent → PO → Architect → Gaspard
