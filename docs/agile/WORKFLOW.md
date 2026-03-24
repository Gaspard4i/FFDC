# FFDC 2026 — Workflow Agile Complet

> "Finn Found Da Chest: The Land of Ooo Chronicles"
> Dev solo (Gaspard) + agents Claude spécialisés
> Document de référence opérationnel — mis à jour à chaque sprint

---

## 1. METHODOLOGIE SCRUM ADAPTEE

### 1.1 Durée des sprints

**5 jours de travail réel** par sprint (pas calendaires).

Justification : dev solo avec contraintes académiques/personnelles. 5 jours permettent de livrer une feature complète et testable sans s'engager sur des cycles trop longs. Une semaine calendaire peut couvrir 10 jours réels si Gaspard travaille le week-end — dans ce cas, les sprints s'adaptent mais le planning reste en "jours de travail réels".

Régulation : si un sprint dépasse 7 jours réels, il est découpé en deux.

### 1.2 Cérémonies conservées

| Cérémonie | Fréquence | Durée | Format adapté (solo + agents) |
|---|---|---|---|
| Sprint Planning | Début de chaque sprint | 30 min | Gaspard + PO agent : validation du backlog, sélection des stories, briefing des agents |
| Sprint Review | Fin de chaque sprint | 20 min | Gaspard joue le jeu, valide les critères d'acceptation, le PO agent produit le rapport |
| Sprint Retrospective | Fin de chaque sprint | 15 min | Gaspard répond à 3 questions fixes (voir §1.5), les résultats alimentent le prochain planning |
| Daily Check-in | Chaque jour de travail actif | 5 min | Remplace le standup (voir §1.4) |

Les cérémonies "Backlog Refinement" sont intégrées dans le Sprint Planning — pas de session séparée.

### 1.3 Sprint Planning — format

Le Sprint Planning produit systématiquement un fichier `docs/agile/sprints/sprint-N-plan.md` avec :

1. Objectif du sprint (1 phrase)
2. Stories sélectionnées avec story points
3. Critères d'acceptation pour chaque story
4. Briefing par agent (qui fait quoi)
5. Risques identifiés pour ce sprint

Le PO agent rédige ce fichier. Gaspard valide avant que les agents commencent.

### 1.4 Daily Check-in (remplace le standup)

Chaque jour de travail, Gaspard crée ou met à jour un fichier `docs/agile/daily/YYYY-MM-DD.md` avec :

```
## Hier
- [ce qui a été fait]

## Aujourd'hui
- [ce qui est prévu]

## Blocage
- [rien / description du blocage]
```

Si un blocage est signalé, le PO agent le ré-achemine vers l'agent compétent ou escalade vers l'Architect.

### 1.5 Sprint Retrospective — format adapté

3 questions fixes, réponses en bullets courts :

1. Qu'est-ce qui a bien fonctionné ? (pratiques à conserver)
2. Qu'est-ce qui a mal fonctionné ? (pratiques à abandonner)
3. Qu'est-ce qu'on essaie au prochain sprint ? (une seule chose nouvelle)

Le résultat est stocké dans `docs/agile/sprints/sprint-N-retro.md`. Le PO agent intègre les résultats dans le Sprint Planning suivant.

---

## 2. BACKLOG COMPLET

### 2.1 Epics (priorités MoSCoW)

| ID | Epic | Priorité | Sprints cibles |
|---|---|---|---|
| E1 | Infrastructure de jeu (core loop) | Must Have | 1-2 |
| E2 | Gestion des maps et du monde | Must Have | 2-3 |
| E3 | Mode Speedrun | Must Have | 3-4 |
| E4 | Entités et IA NPC | Should Have | 4-5 |
| E5 | Mode Story (quêtes + dialogues) | Should Have | 5-6 |
| E6 | Mode Exploration (open world) | Should Have | 6-7 |
| E7 | Cycle jour/nuit | Should Have | 7 |
| E8 | Ghost replay (Speedrun) | Could Have | 4 |
| E9 | Menus complets (main, pause, options) | Must Have | 2 |
| E10 | Audio (musique + SFX) | Must Have | 1-2 |
| E11 | HUD (timer, inventaire, minimap) | Should Have | 3-5 |
| E12 | CI/CD et packaging | Must Have | 1, 10 |
| E13 | Polissage et équilibrage | Could Have | 9-10 |
| E14 | Leaderboard local (Speedrun) | Won't Have (v1) | — |

### 2.2 Story points — T-shirt sizing

| Taille | Points | Définition |
|---|---|---|
| XS | 1 | Modification triviale, < 1h |
| S | 2 | Feature simple, < 3h |
| M | 3 | Feature standard, < 6h (1 journée) |
| L | 5 | Feature complexe, 1-2 journées |
| XL | 8 | Feature majeure, > 2 journées — à découper |

Vélocité cible : **12-15 points par sprint** (calibrage sprint 1-2, ajustement sprint 3+).

### 2.3 Definition of Ready (DoR)

Une story est prête pour un sprint si :

- [ ] La story est rédigée au format "As a... / I want... / So that..."
- [ ] Les critères d'acceptation sont listés (au moins 2, testables)
- [ ] La story est estimée (T-shirt size)
- [ ] Les dépendances sont identifiées et satisfaites
- [ ] Les assets nécessaires sont disponibles ou leur livraison est planifiée dans le même sprint

### 2.4 Definition of Done (DoD)

Une story est terminée si :

- [ ] Le code compile sans erreur (`./gradlew compileJava`)
- [ ] Les tests passent (`./gradlew test`)
- [ ] La feature est jouable dans le jeu (pas seulement compilable)
- [ ] Tous les critères d'acceptation sont validés manuellement par Gaspard
- [ ] Aucune régression sur les features précédentes
- [ ] Le code respecte les conventions de CLAUDE.md (pas de magic numbers, enums, EventBus, etc.)
- [ ] La branche est mergée sur `develop` via PR

### 2.5 User Stories — Sprints 1 à 4

#### Sprint 1 — Core Loop & Infrastructure

**US-001** (E1, M, 3pts)
As a player, I want to see a main menu when I launch the game, so that I can choose a game mode before playing.
- Critères : menu s'affiche au lancement, 3 boutons (Speedrun / Story / Exploration), bouton Quitter fonctionnel

**US-002** (E1, M, 3pts)
As a player, I want to move Finn on a map with keyboard (WASD/ZQSD/arrows), so that I can explore the world.
- Critères : déplacement fluide 60fps, les 3 schémas de touches fonctionnent, collision avec les murs

**US-003** (E10, S, 2pts)
As a player, I want background music to play during the game, so that the atmosphere feels immersive.
- Critères : musique joue au démarrage d'une partie, touche M toggle on/off, reprise sans redémarrage

**US-004** (E12, S, 2pts)
As a developer, I want CI to build and test the game on each push, so that regressions are caught early.
- Critères : GitHub Actions build sur push main/develop, rapport de test visible dans l'UI GitHub

**US-005** (E1, S, 2pts)
As a player, I want a pause menu accessible with Escape/P, so that I can interrupt the game at any time.
- Critères : pause suspend le gameplay et la musique, menu affiche Reprendre/Menu Principal, timer gelé

#### Sprint 2 — Maps, Monde & Menus

**US-006** (E2, L, 5pts)
As a player, I want the game world to be loaded from a Tiled .tmx map, so that the level design is editable without recompiling.
- Critères : map .tmx chargée via AssetRegistry, tiles visibles, collisions fonctionnelles depuis le tmx

**US-007** (E9, M, 3pts)
As a player, I want a settings screen accessible from the main menu, so that I can configure controls and audio.
- Critères : volume musique/SFX réglable, affichage des touches courantes, retour au menu principal

**US-008** (E2, S, 2pts)
As a player, I want objects (keys, doors, chest) to be placed on the map and interactable, so that I can progress through the level.
- Critères : clé ramassable, porte déverrouillée avec clé, coffre ouvrable, événements via EventBus

**US-009** (E10, S, 2pts)
As a player, I want sound effects when I interact with objects (pick up key, open chest), so that actions feel satisfying.
- Critères : SFX joue à la collecte de clé, à l'ouverture de porte, à l'ouverture du coffre

#### Sprint 3 — Mode Speedrun

**US-010** (E3, M, 3pts)
As a speedrunner, I want a visible timer that starts when I move and stops when I open the chest, so that I can measure my run time.
- Critères : timer démarre au premier mouvement, s'arrête à l'ouverture du coffre, affiché en HUD format mm:ss:ms

**US-011** (E3, S, 2pts)
As a speedrunner, I want to see my best time saved locally, so that I know my personal record.
- Critères : best time sauvegardé sur disque, affiché sur l'écran de fin de run, persiste entre les sessions

**US-012** (E11, S, 2pts)
As a player, I want a HUD showing my current inventory (keys held), so that I know what I'm carrying.
- Critères : icônes de clés dans le HUD, mise à jour immédiate à la collecte/utilisation

**US-013** (E3, M, 3pts)
As a speedrunner, I want a results screen after completing a run showing my time vs best time, so that I feel rewarded or motivated to retry.
- Critères : écran de fin affiché, temps courant, best time, delta, boutons Rejouer/Menu

#### Sprint 4 — NPC & Ghost Replay

**US-014** (E4, L, 5pts)
As a player, I want to encounter NPCs (Jake) who can speak to me, so that the world feels alive.
- Critères : Jake affiché sur la map, dialogue déclenché à l'approche (touche interaction), DialogueBox s'affiche

**US-015** (E8, L, 5pts)
As a speedrunner, I want to see a ghost of my best run replaying while I play, so that I can compare my movements in real time.
- Critères : positions du best run enregistrées frame par frame, ghost semi-transparent affiché, synchronisé avec le timer

---

## 3. SPRINT PLANS DETAILLES (Sprint 1 à 10)

### Sprint 1 — "Make it run, make it feel alive"
**Objectif :** Un joueur peut lancer le jeu, voir le menu, se déplacer sur une map basique, entendre de la musique, mettre en pause.

Stories : US-001, US-002, US-003, US-004, US-005
Points : 12
Dépendances : aucune (sprint fondateur)

Critères d'acceptation globaux :
- Le jeu se lance via `./gradlew desktop:run` sans erreur
- Main menu fonctionnel avec 3 modes
- Déplacement Finn avec collision
- Musique joue, toggle M
- Pause suspend tout
- CI verte sur GitHub

---

### Sprint 2 — "Le monde existe"
**Objectif :** La map est chargée depuis un fichier Tiled, les objets sont interactables, les paramètres sont accessibles.

Stories : US-006, US-007, US-008, US-009
Points : 12
Dépendances : Sprint 1 (monde, AssetRegistry, EventBus)

Critères d'acceptation globaux :
- Map .tmx visible et jouable
- Clé/Porte/Coffre interactables
- SFX sur interactions
- Écran paramètres accessible

---

### Sprint 3 — "Speedrun Mode v1"
**Objectif :** Le mode Speedrun est jouable de bout en bout avec timer, best time et écran de résultats.

Stories : US-010, US-011, US-012, US-013
Points : 10
Dépendances : Sprint 1 (core loop), Sprint 2 (objets interactables)

Critères d'acceptation globaux :
- Timer démarre/s'arrête correctement
- Best time persisté sur disque
- HUD inventaire fonctionnel
- Écran de fin complet

---

### Sprint 4 — "Le monde respire"
**Objectif :** Jake parle et le ghost replay donne une dimension compétitive au Speedrun.

Stories : US-014, US-015
Points : 10
Dépendances : Sprint 2 (NPC system, carte), Sprint 3 (enregistrement des positions)

---

### Sprint 5 — "Story Mode — Fondations"
**Objectif :** Le mode Story possède un système de quêtes basique et des dialogues à choix.

Stories à affiner en Sprint 4 :
- Système de quêtes (objectif, état, complétion)
- Dialogues à choix multiples (DialogueBox étendue)
- Journal de quêtes (UI)
- NPCs avec états différents selon quête active

Points estimés : 13
Dépendances : Sprint 4 (NPC system, DialogueBox)

---

### Sprint 6 — "Story Mode — Contenu"
**Objectif :** Une arc narrative complète est jouable (intro → quête → fin).

Stories à affiner en Sprint 5 :
- 3 quêtes chainées avec dialogues écrits
- Écran de fin Story avec épilogue
- Conditions de victoire spécifiques Story
- Music / ambiance adaptée au mode

Points estimés : 12
Dépendances : Sprint 5

---

### Sprint 7 — "Exploration Mode — Fondations"
**Objectif :** Le mode Exploration ouvre le monde sans objectif imposé, avec cycle jour/nuit.

Stories à affiner en Sprint 6 :
- Open world : map plus grande, navigation libre
- Cycle jour/nuit (éclairage dynamique)
- Points d'intérêt découvrables
- Système de sauvegarde de progression Exploration

Points estimés : 13
Dépendances : Sprint 2 (maps), Sprint 5 (NPC states)

---

### Sprint 8 — "Exploration Mode — Contenu"
**Objectif :** L'Exploration est riche avec secrets, PNJs variés et ambiance complète.

Stories à affiner en Sprint 7 :
- 5+ zones distinctes avec ambiances
- Secrets et easter eggs Adventure Time
- Météo (pluie, nuages) — optionnel
- Minimap

Points estimés : 12
Dépendances : Sprint 7

---

### Sprint 9 — "Polissage & Cohérence"
**Objectif :** Les 3 modes sont cohérents visuellement et jouables sans friction.

Stories à affiner en Sprint 8 :
- Écran de sélection de mode amélioré
- Animations de transition entre écrans
- Gestion des erreurs (assets manquants, sauvegarde corrompue)
- Accessibilité : taille police, contraste
- Refactoring si dette technique identifiée

Points estimés : 10-12
Dépendances : Sprints 3, 6, 8

---

### Sprint 10 — "Release v1.0"
**Objectif :** La v1.0 est packagée, documentée et publiée sur GitHub Releases.

Stories à affiner en Sprint 9 :
- Packaging JAR exécutable (desktop:dist)
- README final avec instructions de jeu
- CHANGELOG v1.0
- Tag git v1.0.0
- Tests de régression complets sur les 3 modes
- GitHub Release avec artifact

Points estimés : 8
Dépendances : tous les sprints précédents

---

## 4. RAPPORTS ET DOCUMENTATION

### 4.1 Structure de stockage dans le repo

```
docs/
  agile/
    WORKFLOW.md              # Ce document
    BACKLOG.md               # Backlog vivant (mis à jour chaque sprint)
    sprints/
      sprint-N-plan.md       # Créé au début du sprint N
      sprint-N-review.md     # Créé à la fin du sprint N
      sprint-N-retro.md      # Créé à la fin du sprint N
    daily/
      YYYY-MM-DD.md          # Daily check-in
    VELOCITY.md              # Suivi vélocité par sprint
  adr/
    ADR-001-*.md             # Architecture Decision Records
  CHANGELOG.md               # Format Keep a Changelog
```

### 4.2 Sprint Report Template

Fichier : `docs/agile/sprints/sprint-N-review.md`

```markdown
# Sprint N Review — [Objectif]

**Date :** YYYY-MM-DD
**Points engagés :** X | **Points livrés :** Y | **Vélocité :** Y

## Stories
| Story | Titre | Statut | Notes |
|---|---|---|---|
| US-XXX | Titre | Terminé / Partiel / Bloqué | ... |

## Démo
[Description de ce que Gaspard a joué et validé]

## Bugs identifiés
- [bug] ...

## Points reportés au prochain sprint
- US-XXX : raison

## Décisions prises
- ...
```

### 4.3 Burndown

Utile : oui, mais simplifié. Le `VELOCITY.md` contient un tableau :

```markdown
| Sprint | Points engagés | Points livrés | Vélocité | Notes |
|---|---|---|---|---|
| 1 | 12 | 10 | 10 | US-004 reportée |
```

Pas de graphique automatisé — Gaspard met à jour manuellement à la fin de chaque sprint. La tendance à 3 sprints suffit pour recalibrer.

### 4.4 Release Notes format

`CHANGELOG.md` au format Keep a Changelog (https://keepachangelog.com) :

```markdown
## [1.0.0] - YYYY-MM-DD
### Added
- Mode Speedrun avec timer et ghost replay
- Mode Story avec 3 quêtes
- Mode Exploration avec cycle jour/nuit

### Fixed
- ...

### Changed
- ...
```

---

## 5. WORKFLOW GIT

### 5.1 Stratégie de branches

**GitHub Flow adapté** (pas GitFlow — trop lourd pour un dev solo).

```
main          ← production stable, protégée, tags de release
develop       ← intégration continue, toujours buildable
feature/*     ← une branche par story
fix/*         ← corrections de bugs
chore/*       ← maintenance (CI, deps, docs)
```

Règle : pas de commit direct sur `main` ni `develop`. Tout passe par PR.

### 5.2 Naming convention des branches

```
feature/US-001-main-menu
feature/US-006-tmx-map-loading
fix/US-010-timer-not-stopping
chore/update-libgdx-1.13.1
chore/ci-add-test-step
```

Format : `type/US-XXX-description-courte` (kebab-case, en anglais, < 50 chars)

### 5.3 PR Process

Template PR (`.github/pull_request_template.md`) :

```markdown
## Story liée
US-XXX — [Titre]

## Ce que cette PR fait
[1-3 bullets]

## Critères d'acceptation
- [ ] Critère 1
- [ ] Critère 2

## Tests effectués
- [ ] `./gradlew test` passe
- [ ] Testé en jeu manuellement

## Screenshots / GIF (si UI)
[optionnel]
```

**Reviewers :** en dev solo, pas de reviewer humain. L'agent QA fait une revue automatisée avant merge.

**Labels GitHub :**
- `feature` / `fix` / `chore`
- `sprint-N`
- `blocked` / `ready-to-merge`
- `agent: dev-core` / `agent: dev-ui` / `agent: qa`

### 5.4 Merge strategy

**Squash and merge** sur `develop` : une story = un commit propre dans l'historique.
**Merge commit** de `develop` vers `main` à chaque release : on garde le point de merge visible.

Message de commit squash : `feat(US-001): add main menu with 3 game mode buttons`

Format : `type(scope): description` (Conventional Commits)
Types : `feat`, `fix`, `chore`, `docs`, `test`, `refactor`

### 5.5 Release tagging

```
v1.0.0    ← release majeure (fin Sprint 10)
v0.3.0    ← fin Sprint 3 (Speedrun complet)
v0.5.0    ← fin Sprint 5 (Story v1)
v0.7.0    ← fin Sprint 7 (Exploration v1)
```

Format : `vMAJOR.MINOR.PATCH` (SemVer)
- MAJOR : changement de version de jeu (v0 = dev, v1 = release)
- MINOR : sprint livré avec features
- PATCH : hotfix

Tag créé manuellement par Gaspard après validation du Sprint Review :
```bash
git tag -a v0.3.0 -m "Sprint 3 — Speedrun Mode complet"
git push origin v0.3.0
```

---

## 6. EQUIPE D'AGENTS

### 6.1 Composition et rôles

| Agent | Modèle | Rôle | Charge par sprint |
|---|---|---|---|
| PO FFDC | Sonnet | Backlog, user stories, sprint planning, sprint review, coordination | Chaque sprint |
| Architect | Sonnet | ADRs, revue d'architecture, interfaces entre systèmes, décisions techniques structurantes | Sprint 1, puis à la demande |
| Dev Core | Sonnet | Systèmes de jeu (GameContext, entités, physics, collision, gamemodes, EventBus) | Chaque sprint |
| Dev UI | Sonnet | Screens LibGDX, HUD, menus, dialogues, Scene2D | Sprints 1-2, 5-6, 9 |
| Asset Pipeline | Haiku | Gradle tasks, TexturePacker, organisation assets, atlas generation | Sprints 1-2, puis à la demande |
| QA | Haiku | JUnit tests, rapport de bugs, vérification DoD, revue PR | Chaque sprint |
| Doc Writer | Haiku | CLAUDE.md, README, CHANGELOG, sprint reports | Fin de chaque sprint |

**Pas de doublons** — 7 agents distincts est le bon équilibre pour ce projet. Haiku pour les tâches répétitives/mécaniques, Sonnet pour les tâches de conception et logique complexe.

### 6.2 Qui fait quoi par sprint type

**Début de sprint (Day 1) :**
1. PO FFDC : produit `sprint-N-plan.md` avec briefings par agent
2. Architect : validé si story implique une nouvelle interface ou système majeur
3. Gaspard : valide le plan, crée les branches

**Pendant le sprint (Days 1-4) :**
- Dev Core : implémente les systèmes (crée les branches feature/*)
- Dev UI : implémente les screens et HUD
- Asset Pipeline : génère/organise les assets si nécessaire
- QA : écrit les tests JUnit en parallèle, teste au fil de l'eau

**Fin de sprint (Day 5) :**
1. QA : rapport de bugs, vérification DoD pour chaque story
2. Doc Writer : met à jour CHANGELOG, CLAUDE.md si nécessaire
3. PO FFDC : produit `sprint-N-review.md` et `sprint-N-retro.md`
4. Gaspard : joue et valide, merge les PRs validées

### 6.3 Communication entre agents

**Protocole de communication via fichiers markdown :**

```
docs/agile/inbox/
  [agent-name]-inbox.md    ← messages entrants pour cet agent
  [agent-name]-outbox.md   ← messages sortants (lecture par PO)
```

Format d'un message dans l'inbox :

```markdown
## MSG-[N] — [Sujet]
**De :** [agent émetteur]
**Pour :** [agent destinataire]
**Sprint :** N
**Priorité :** normale / haute / bloquante
**Date :** YYYY-MM-DD

[Corps du message]

**Action requise :** [description claire]
**Deadline :** fin sprint / immédiat
```

### 6.4 Escalation path

1. Agent bloqué → il documente le blocage dans son outbox avec priorité "bloquante"
2. PO FFDC lit les outbox quotidiennement → re-route vers l'agent compétent
3. Si le blocage est architectural → Architect est invoqué
4. Si le blocage nécessite une décision de Gaspard → PO FFDC flag dans le daily check-in
5. Si le blocage dure plus de 1 journée → la story est retirée du sprint et replanifiée

**Règle d'or :** un agent bloqué ne reste jamais bloqué silencieusement. Il documente, il signale, il travaille sur une autre tâche en attendant.

---

## 7. METRIQUES DE SUIVI

### 7.1 KPIs du projet

| Métrique | Cible | Fréquence de mesure |
|---|---|---|
| Vélocité (points/sprint) | 12-15 (calibrage sprints 1-2) | Fin de chaque sprint |
| Taux de complétion des stories | > 80% | Fin de chaque sprint |
| Nombre de bugs ouverts | < 5 | Fin de chaque sprint |
| Couverture de tests | > 60% sur le code métier | Fin de chaque sprint |
| Temps de build CI | < 3 min | Continu |
| FPS en jeu | > 55fps stable | Chaque sprint contenant du gameplay |

### 7.2 Velocity tracking

Fichier `docs/agile/VELOCITY.md` mis à jour à chaque fin de sprint.

Les 3 premières sprints servent de calibrage. A partir du sprint 4, si la vélocité réelle est < 10 ou > 18, le contenu du sprint suivant est ajusté.

### 7.3 Quality metrics

- Tests JUnit : l'agent QA produit un rapport dans le sprint review
- Pas de régression : chaque PR doit passer `./gradlew test`
- Performance : Gaspard vérifie visuellement les FPS à chaque sprint contenant du gameplay
- Convention : l'agent QA vérifie le respect de CLAUDE.md (magic numbers, enums, EventBus)

### 7.4 Mesure du progrès

Progrès par rapport au scope total :
- 3 modes de jeu = 3 milestones majeures
- v0.3.0 = Speedrun done (Sprint 3)
- v0.5.0 = Story done (Sprint 6)
- v0.7.0 = Exploration done (Sprint 8)
- v1.0.0 = Release (Sprint 10)

A chaque fin de sprint, le PO FFDC indique le pourcentage de chaque milestone atteint.

---

## 8. RISK MANAGEMENT

### 8.1 Risques identifiés

| ID | Risque | Probabilité | Impact | Score |
|---|---|---|---|---|
| R1 | Scope creep (3 modes = complexité explosive) | Haute | Haute | Critique |
| R2 | Dette technique LibGDX (Screen transitions, AssetManager) | Moyenne | Haute | Haute |
| R3 | Disponibilité réduite de Gaspard (contraintes personnelles) | Haute | Moyenne | Haute |
| R4 | Assets manquants ou non compatibles | Moyenne | Moyenne | Moyenne |
| R5 | Performance dégradée (trop d'entités/systèmes) | Faible | Haute | Moyenne |
| R6 | Agent bloqué sur une dépendance inter-systèmes | Moyenne | Moyenne | Moyenne |
| R7 | Incompatibilité LibGDX / Java 21 sur mise à jour | Faible | Haute | Moyenne |

### 8.2 Mitigation plan

**R1 — Scope creep :**
- Le mode Speedrun est livré complet en Sprint 3 avant de commencer Story
- Chaque mode est une epic indépendante avec feature flag si nécessaire
- "Won't Have v1" est une liste active que le PO FFDC protège

**R2 — Dette technique :**
- L'Architect produit un ADR avant chaque décision structurante
- Refactoring sprint dédié (Sprint 9) prévu
- Les interfaces sont définies avant l'implémentation

**R3 — Disponibilité Gaspard :**
- Les sprints sont en "jours réels" pas calendaires
- Un sprint peut être mis en pause et repris sans conséquence
- Les agents travaillent de façon asynchrone

**R4 — Assets manquants :**
- L'Asset Pipeline agent audite les assets au Sprint 1
- Les placeholders sont acceptés jusqu'au Sprint 9
- Les assets critiques (player, tiles basiques) sont prioritaires

**R5 — Performance :**
- L'agent QA vérifie les FPS à chaque sprint gameplay
- Le profiling LibGDX est activé si FPS < 45
- Limite : < 50 entités actives simultanément jusqu'à optimisation

**R6 — Agent bloqué :**
- Protocole d'escalade décrit en §6.4
- PO FFDC vérifie les outbox chaque journée active

**R7 — Incompatibilité LibGDX :**
- Pas de mise à jour de LibGDX pendant un sprint actif
- Les mises à jour passent par une branche `chore/update-*` avec CI obligatoire
- `gradle.properties` versionné et documenté

### 8.3 Contingency

Si le projet prend du retard de 2 sprints ou plus :
1. Le mode Exploration est réduit à une version minimale (carte ouverte sans cycle jour/nuit)
2. Le ghost replay est dépriorisé (Could Have → Won't Have v1)
3. Le Sprint 10 est avancé pour livrer une v1.0 avec Speedrun + Story uniquement

Le PO FFDC déclenche cette contingence si à la fin du Sprint 7, les 3 milestones Speedrun et Story ne sont pas à 100%.

---

## ANNEXE — Checklist de démarrage Sprint

```
[ ] Sprint N-1 review complète et archivée
[ ] Retro N-1 lue et action intégrée dans ce planning
[ ] Stories du sprint sélectionnées et DoR vérifiée
[ ] Briefings agents rédigés dans sprint-N-plan.md
[ ] Branches créées sur GitHub
[ ] PR template présent (.github/pull_request_template.md)
[ ] Gaspard a validé le plan
```
