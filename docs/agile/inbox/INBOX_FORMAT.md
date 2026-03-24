# Format des messages inter-agents

Chaque agent possède un fichier inbox : `[agent-name]-inbox.md`
Les agents lisent leur inbox au début de chaque session de travail.
Le PO FFDC lit tous les outbox quotidiennement.

## Format d'un message

```markdown
## MSG-[N] — [Sujet court]
**De :** [agent émetteur]
**Pour :** [agent destinataire]
**Sprint :** N
**Priorité :** normale / haute / bloquante
**Date :** YYYY-MM-DD

[Corps du message : contexte, question, ou information]

**Action requise :** [description claire de ce qui est attendu]
**Deadline :** fin sprint / immédiat / YYYY-MM-DD
---
```

## Agents connus

- `po-ffdc` — Product Owner
- `architect` — Architect
- `dev-core` — Dev Core
- `dev-ui` — Dev UI
- `asset-pipeline` — Asset Pipeline
- `qa` — QA
- `doc-writer` — Doc Writer
- `gaspard` — Gaspard (décisions humaines uniquement)
