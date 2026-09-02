---
name: create-mr
description: >-
  Создаёт feature-ветку и MR/PR через release-agent. Вызывать вручную, когда
  задача с кодом готова и нужно оформить ветку + merge request. Триггеры:
  create-mr, создай мр, создай mr, создай pr, оформи мр, release-agent.
disable-model-invocation: true
---

# Create MR

Пользователь явно вызвал этот skill — это разрешение на ветку, commit, push и MR.

## Инструкции release-agent

Прочитай и следуй (в порядке приоритета):
1. `<workspace>/.cursor/agents/release-agent.md`
2. `~/.cursor/agents/release-agent.md`

## Алгоритм

### 1. Подготовка

- `git status`, `git diff`, `git log` — понять, что сделано в текущей задаче
- `git fetch --all --prune`
- Обнови базовую ветку (`develop`, если не указано иное)

### 2. Запуск release-agent

Запусти subagent **release-agent** (`Task`, `subagent_type: release-agent`) с промптом:

```
Выполни полный цикл оформления MR для текущих изменений:

1. Создай ветку feature/<краткое-описание> от develop (или от текущей, если уже на feature-ветке)
2. Закоммить только релевантные изменения (без generated/секретов)
3. Push ветки
4. Создай MR/PR в develop

Соблюдай .cursor/agents/release-agent.md проекта.

MR:
- title: кратко, что сделано
- body: описание сделанного (из diff/коммитов) + как тестировать

Сначала MCP (user-github), потом gh CLI.
Верни: имя ветки, commit hash, URL MR, краткое summary.
```

Передай release-agent контекст: какие файлы менялись, суть задачи из сессии.

### 3. Самому не делать

Не выполняй push, `gh pr create` и создание ветки самостоятельно — только через **release-agent**.

### 4. Отчёт пользователю

После завершения release-agent сообщи:
- имя ветки
- ссылку на MR
- title и краткое summary body

## Если неясно

Одним вопросом уточни: имя ветки, base branch или scope коммита — если из контекста не выводится.
