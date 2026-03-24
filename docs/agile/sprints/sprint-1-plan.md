# Sprint 1 Plan — "Make it run, make it feel alive"

**Date de début :** 2026-03-24
**Durée :** 5 jours de travail réel
**Objectif :** Un joueur peut lancer le jeu, voir le menu principal, se déplacer sur une map basique, entendre de la musique, et mettre le jeu en pause.

---

## Stories sélectionnées

| ID | Story | Points | Agent principal |
|---|---|---|---|
| US-001 | Main menu avec 3 modes de jeu | 3 | Dev UI |
| US-002 | Déplacement Finn avec collision | 3 | Dev Core |
| US-003 | Musique de fond avec toggle M | 2 | Dev Core |
| US-004 | CI GitHub Actions build+test | 2 | Asset Pipeline / Doc Writer |
| US-005 | Pause menu Escape/P | 2 | Dev UI |

**Total : 12 points**

---

## Critères d'acceptation

### US-001 — Main Menu
- [ ] Le jeu affiche un écran titre au démarrage
- [ ] 3 boutons : Speedrun, Story, Exploration (même si les modes ne sont pas encore implémentés)
- [ ] Bouton Quitter fonctionnel
- [ ] Navigation au clavier (touches fléchées + Entrée)

### US-002 — Déplacement Finn
- [ ] Finn se déplace avec WASD, ZQSD et touches fléchées (les 3 simultanément)
- [ ] Mouvement fluide à 60fps
- [ ] Collision avec les murs (Finn ne traverse pas)
- [ ] Animation de marche dans les 4 directions

### US-003 — Musique de fond
- [ ] Une musique joue dès le démarrage d'une partie
- [ ] Touche M coupe la musique
- [ ] Touche M la relance (toggle)
- [ ] La musique reprend au même point (pas de restart)

### US-004 — CI GitHub Actions
- [ ] Workflow déclenché sur push vers main et develop
- [ ] Step : build (`./gradlew compileJava`)
- [ ] Step : tests (`./gradlew test`)
- [ ] Badge de statut visible dans le README

### US-005 — Pause Menu
- [ ] Escape ou P ouvre le menu pause depuis n'importe quel écran de jeu
- [ ] Gameplay gelé pendant la pause (pas de mouvement, pas de timer)
- [ ] Musique en pause pendant le menu pause
- [ ] Bouton "Reprendre" relance le jeu
- [ ] Bouton "Menu Principal" retourne au main menu

---

## Briefings agents

### Dev UI (US-001, US-005)
Implémenter MainMenuScreen et PauseMenuScreen en LibGDX Scene2D. Le MainMenuScreen est le premier Screen affiché. Le PauseMenu est une overlay (pas un Screen séparé) injectée dans les GameScreens via EventBus (événement PAUSE_REQUESTED). Respecter les conventions CLAUDE.md : une Screen par état, pas de magic numbers.

### Dev Core (US-002, US-003)
Implémenter le GameInputProcessor avec les 3 schémas de touches (enums InputAction). Le mouvement de Finn passe par le système existant. L'AudioManager gère le toggle musique via l'action INPUT_MUSIC_TOGGLE. Le CollisionSystem doit bloquer le mouvement contre les tiles solides.

### Asset Pipeline (US-004)
Créer `.github/workflows/build.yml` avec les steps build + test. Ajouter le badge de statut dans README.md. Vérifier que le workflow passe avec les assets actuels.

### QA (US-001 à US-005)
Écrire des tests JUnit pour : InputAction mapping, AudioManager toggle, CollisionSystem (test avec mur). Vérifier la DoD sur chaque story en fin de sprint.

### Doc Writer (fin de sprint)
Mettre à jour CHANGELOG.md et CLAUDE.md si nouvelles conventions. Produire `sprint-1-review.md` et `sprint-1-retro.md`.

---

## Risques du sprint

| Risque | Probabilité | Mitigation |
|---|---|---|
| Collision .tmx pas encore disponible | Haute | US-002 utilise une map hardcodée temporaire (tableau 2D) |
| CI échoue sur les assets binaires | Faible | Le workflow ignore les assets pour le premier run |

---

## Dépendances

- Aucune dépendance externe (sprint fondateur)
- La map pour US-002 sera une placeholder simple — la vraie map .tmx arrive Sprint 2

---

## Branches à créer

```
feature/US-001-main-menu
feature/US-002-player-movement
feature/US-003-background-music
feature/US-004-ci-github-actions
feature/US-005-pause-menu
```
