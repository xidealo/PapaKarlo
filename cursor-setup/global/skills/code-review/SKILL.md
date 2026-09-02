---
name: code-review
description: >-
  После завершения написания кода вызывай reviewer. Не запускай ktlint, detekt,
  lint и другие проверки чистоты кода самостоятельно — это делает reviewer.
  Применяй при завершении задач с правками кода.
---

# Code review

См. глобальное правило: `~/.cursor/rules/code-review.mdc`

## После завершения кода

Вызови subagent **reviewer**. Инструкции:
- `<workspace>/.cursor/agents/reviewer.md` — приоритет
- `~/.cursor/agents/reviewer.md` — fallback

## Не запускай сам

ktlint, detekt, `./gradlew check`, ESLint, ReadLints вместо review — нет. Проверки делает reviewer.
