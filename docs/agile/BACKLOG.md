# FFDC 2026 — Product Backlog

> Document vivant. Mis à jour à chaque sprint planning.
> Dernière mise à jour : 2026-03-24

## Epics

| ID | Epic | Priorité | Sprint(s) |
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
| E14 | Leaderboard local (Speedrun) | Won't Have v1 | — |

---

## Backlog prioritisé

### Must Have — Sprint 1-2

| ID | Story | Epic | Points | Sprint | Statut |
|---|---|---|---|---|---|
| US-001 | Main menu avec 3 modes | E1 | 3 | 1 | A faire |
| US-002 | Déplacement Finn avec collision | E1 | 3 | 1 | A faire |
| US-003 | Musique de fond avec toggle M | E10 | 2 | 1 | A faire |
| US-004 | CI GitHub Actions build+test | E12 | 2 | 1 | A faire |
| US-005 | Pause menu Escape/P | E1 | 2 | 1 | A faire |
| US-006 | Chargement map .tmx Tiled | E2 | 5 | 2 | A faire |
| US-007 | Écran paramètres (audio, touches) | E9 | 3 | 2 | A faire |
| US-008 | Objets interactables (clé, porte, coffre) | E2 | 2 | 2 | A faire |
| US-009 | SFX sur interactions objets | E10 | 2 | 2 | A faire |

### Must Have — Sprint 3

| ID | Story | Epic | Points | Sprint | Statut |
|---|---|---|---|---|---|
| US-010 | Timer Speedrun (HUD) | E3 | 3 | 3 | A faire |
| US-011 | Best time sauvegardé localement | E3 | 2 | 3 | A faire |
| US-012 | HUD inventaire (clés) | E11 | 2 | 3 | A faire |
| US-013 | Écran de résultats Speedrun | E3 | 3 | 3 | A faire |

### Should Have — Sprint 4

| ID | Story | Epic | Points | Sprint | Statut |
|---|---|---|---|---|---|
| US-014 | NPC Jake avec dialogues | E4 | 5 | 4 | A faire |
| US-015 | Ghost replay best run | E8 | 5 | 4 | A faire |

### Should Have — Sprints 5-8 (à affiner)

| ID | Story | Epic | Points | Sprint | Statut |
|---|---|---|---|---|---|
| US-016 | Système de quêtes basique | E5 | 5 | 5 | A affiner |
| US-017 | Dialogues à choix multiples | E5 | 3 | 5 | A affiner |
| US-018 | Journal de quêtes (UI) | E5 | 3 | 5 | A affiner |
| US-019 | Arc Story complète (3 quêtes) | E5 | 5 | 6 | A affiner |
| US-020 | Écran de fin Story | E5 | 2 | 6 | A affiner |
| US-021 | Open world map étendue | E6 | 5 | 7 | A affiner |
| US-022 | Cycle jour/nuit (éclairage) | E7 | 5 | 7 | A affiner |
| US-023 | Système de sauvegarde Exploration | E6 | 3 | 7 | A affiner |
| US-024 | 5 zones distinctes + ambiances | E6 | 5 | 8 | A affiner |
| US-025 | Minimap | E11 | 3 | 8 | A affiner |

### Could Have — Sprints 9-10

| ID | Story | Epic | Points | Sprint | Statut |
|---|---|---|---|---|---|
| US-026 | Animations de transition entre écrans | E13 | 2 | 9 | A affiner |
| US-027 | Gestion d'erreurs (assets manquants) | E13 | 2 | 9 | A affiner |
| US-028 | Packaging JAR final + GitHub Release | E12 | 3 | 10 | A affiner |
| US-029 | README final + CHANGELOG v1.0 | E12 | 2 | 10 | A affiner |

### Won't Have v1

| ID | Story | Epic | Raison |
|---|---|---|---|
| US-030 | Leaderboard local Speedrun | E14 | Complexité disproportionnée pour v1 |
| US-031 | Mode multijoueur | — | Hors scope |
| US-032 | Génération procédurale de maps | — | Hors scope v1 |
