---
name: git-workflow
description: >-
  Перед написанием кода обновляй локальные git-ветки. Push, commit, PR, CI и
  релиз — только через release-agent. Сначала MCP-серверы, потом CLI. Применяй
  при любой задаче с правками кода, git-операциями или интеграциями с VCS.
---

# Git workflow

См. глобальное правило: `~/.cursor/rules/git-workflow.mdc`

## Перед написанием кода

1. `git fetch --all --prune`
2. `git pull --ff-only` для текущей ветки (если есть upstream)
3. Обнови основную ветку репозитория: `git fetch origin <base>:<base>`

## Push и VCS

Делегируй **release-agent** (subagent). Инструкции:
- `<workspace>/.cursor/agents/release-agent.md` — приоритет
- `~/.cursor/agents/release-agent.md` — fallback

## Приоритет MCP

MCP → CLI (`gh`, `glab`) → локальный git. Схему tool читай перед вызовом.
