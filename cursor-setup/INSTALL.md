# Развёртывание Cursor rules, skills и agents на Mac

Экспорт с Windows-машины (июнь 2026). Содержимое пакета `cursor-setup/`.

## Что внутри

| Путь | Назначение | Куда ставить на Mac |
|------|------------|---------------------|
| `global/rules/*.mdc` | Глобальные правила (always apply / globs) | `~/.cursor/rules/` |
| `global/skills/*/SKILL.md` | Пользовательские skills | `~/.cursor/skills/<name>/SKILL.md` |
| `global/agents/*.md` | Глобальный release-agent (fallback) | `~/.cursor/agents/` |
| `global/user-rules.md` | User Rules из настроек Cursor | Вручную в Settings → Rules |
| `project/agents/*.md` | Агенты репозитория PapaKarlo | `<repo>/.cursor/agents/` |

### Глобальные rules

- **git-workflow.mdc** — fetch/pull перед кодом; push/PR через release-agent; приоритет MCP
- **code-review.mdc** — reviewer после кода; линтеры не запускать самому
- **kotlin-lambda-names.mdc** — говорящие имена в Kotlin-лямбдах вместо `it` (только `*.kt`)

### Глобальные skills

- **git-workflow** — краткая выжимка git-workflow rule
- **code-review** — краткая выжимка code-review rule
- **create-mr** — ручной триггер MR через release-agent (`disable-model-invocation: true`)
- **review-answer** — ревью ответа по CLEAR перед использованием
- **answer-revise** — доработка ответа по замечаниям ревью

### Project agents (PapaKarlo)

| Агент | Описание |
|-------|----------|
| orchestrator | Координация задач и делегирование |
| kmp-architect | Архитектура KMP / модули |
| developer_domain | Use cases, domain |
| developer_data | Data layer, сеть, БД |
| developer_presentation | ViewModel, MVI, reducers |
| developer_ui | Compose UI |
| refactor-agent | Безопасный рефакторинг |
| reviewer | Code review + ktlint/lint |
| release-agent | Ветки, PR, релизы (специфика PapaKarlo) |
| tester | *(пустой шаблон)* |
| performance-agent | *(пустой шаблон)* |

---

## Быстрая установка (рекомендуется)

### 1. Скопировать пакет на Mac

Перенесите папку `cursor-setup` (или архив `cursor-setup.zip`) на Mac любым способом: AirDrop, git, USB, облако.

Распакуйте, если нужно:

```bash
unzip cursor-setup.zip -d ~/Downloads/
cd ~/Downloads/cursor-setup
```

### 2. Запустить скрипт

```bash
chmod +x install.sh
./install.sh ~/path/to/PapaKarlo/.cursor/agents
```

Без аргумента установятся только глобальные rules/skills/agents.

### 3. User Rules

Откройте **Cursor → Settings → Cursor Settings → Rules** и вставьте содержимое файла `global/user-rules.md` (или добавьте блоки по отдельности).

### 4. Перезапуск

Перезапустите Cursor или **Developer: Reload Window**.

---

## Ручная установка

```bash
# Глобальные rules
mkdir -p ~/.cursor/rules
cp global/rules/*.mdc ~/.cursor/rules/

# Глобальные skills
mkdir -p ~/.cursor/skills/git-workflow ~/.cursor/skills/code-review ~/.cursor/skills/create-mr
cp global/skills/git-workflow/SKILL.md ~/.cursor/skills/git-workflow/
cp global/skills/code-review/SKILL.md ~/.cursor/skills/code-review/
cp global/skills/create-mr/SKILL.md ~/.cursor/skills/create-mr/

# Глобальный agent
mkdir -p ~/.cursor/agents
cp global/agents/release-agent.md ~/.cursor/agents/

# Агенты проекта (в клоне PapaKarlo)
mkdir -p ~/Projects/PapaKarlo/.cursor/agents
cp project/agents/*.md ~/Projects/PapaKarlo/.cursor/agents/
```

---

## Проверка

```bash
ls ~/.cursor/rules/
ls ~/.cursor/skills/*/SKILL.md
ls ~/.cursor/agents/
ls ~/Projects/PapaKarlo/.cursor/agents/
```

В Cursor Agent chat должны быть доступны subagents: `reviewer`, `release-agent`, `orchestrator` и др. (зависит от версии Cursor).

Skills `create-mr`, `git-workflow`, `code-review` появятся в списке skills после перезагрузки.

---

## Приоритеты (как задумано)

1. **Rules** с `alwaysApply: true` подхватываются автоматически из `~/.cursor/rules/`
2. **Project agents** в `<repo>/.cursor/agents/` важнее глобальных (`release-agent`, `reviewer`)
3. **Skills** с `disable-model-invocation: true` (`create-mr`) вызываются только вручную

---

## MCP на Mac

Правила ссылаются на MCP (`user-github`, `user-GitLab` и т.д.). На Mac нужно отдельно настроить те же MCP-серверы в **Cursor Settings → MCP** — они не входят в этот пакет.

---

## Обновление

Повторите копирование поверх существующих файлов или снова запустите `install.sh`. Скрипт перезаписывает файлы с теми же именами.

---

## Структура архива

```
cursor-setup/
├── INSTALL.md          ← этот файл
├── install.sh            ← скрипт установки
├── global/
│   ├── rules/
│   ├── skills/
│   ├── agents/
│   └── user-rules.md
└── project/
    └── agents/
```
