# User Rules (Cursor Settings → Rules)

Эти правила хранятся в настройках Cursor (User Rules), а не в файловой системе.
На Mac: **Cursor → Settings → Cursor Settings → Rules** — вставьте блоки ниже.

---

## Committing changes with git

Only create commits when requested by the user. If unclear, ask first. When the user asks you to create a new git commit, follow these steps carefully:

Git Safety Protocol:

- NEVER update the git config
- NEVER run destructive/irreversible git commands (like push --force, hard reset, etc) unless the user explicitly requests them
- NEVER skip hooks (--no-verify, --no-gpg-sign, etc) unless the user explicitly requests it
- NEVER run force push to main/master, warn the user if they request it
- Avoid git commit --amend. ONLY use --amend when ALL conditions are met
- CRITICAL: If commit FAILED or was REJECTED by hook, NEVER amend - fix the issue and create a NEW commit
- CRITICAL: If you already pushed to remote, NEVER amend unless the user explicitly requests it
- NEVER commit changes unless the user explicitly asks you to

When committing: run git status, git diff, git log in parallel; stage only relevant files; use HEREDOC for commit message.

Do NOT push unless explicitly asked.

---

## Creating pull requests

Use the gh command for GitHub PRs. Before creating PR: git status, git diff, check upstream, git log and diff from base branch.

Create branch if needed, push with -u, then `gh pr create` with HEREDOC body.

Return the PR URL when done.

---

## Code writing principles

1. Minimize scope — simplest correct diff
2. Avoid over-engineering
3. Use existing conventions
4. Comments only for non-obvious logic
5. Useful tests only when requested or meaningful

---

## Позитивная ветка if/else

При развилке: в `if` — позитивное условие («есть значение», «всё ок»), альтернатива в `else`.
Исключение: guard с ранним return.

---

## Строки UI — только в ресурсах (PapaKarlo)

Не добавлять пользовательские строки литералами в commonMain/androidMain/iosMain.

Новые строки → `designsystem/src/commonMain/composeResources/values/strings.xml`
с префиксами: title_, hint_, msg_, error_, action_, description_, common_.

В Compose: `stringResource(Res.string.<name>)` или принятый паттерн проекта.
