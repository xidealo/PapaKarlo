---
name: review-answer
description: Review generated output using the CLEAR framework and produce focused feedback on completeness, logic, evidence, audience fit, and relevance. Use when the user asks to review, critique, validate, or quality-check an answer before sharing it.
---

# Review Answer (CLEAR)

## Purpose

Evaluate an output with the CLEAR framework:

- **Complete**: Did it answer everything requested?
- **Logical**: Does it make sense from start to finish?
- **Evidence**: Is it accurate and grounded? What needs source verification?
- **Audience**: Is it appropriate for the intended reader or user?
- **Relevant**: Does it stay focused on the requested task?

## Workflow

1. Identify the intended audience and the original request.
2. Read the output once for overall intent, then once for details.
3. Evaluate each CLEAR dimension and capture concrete findings.
4. Separate confirmed issues from items that need verification.
5. Produce concise revision guidance before the output is used.

## Output Format

Use this structure:

```markdown
## CLEAR Review

### Complete
- ✅ What is complete
- ⚠️ What is missing

### Logical
- ✅ What is coherent
- ⚠️ Where flow or reasoning breaks

### Evidence
- ✅ What is likely well-grounded
- 🔍 What requires verification (facts, numbers, claims, dates, sources)

### Audience
- ✅ What fits the audience
- ⚠️ What should be adjusted (tone, complexity, terminology, assumptions)

### Relevant
- ✅ What stays on-task
- ⚠️ What is off-topic or unnecessary

### Revise Before Use
- Priority 1:
- Priority 2:
- Priority 3:
```

Issue formatting rules:

- For each issue, include severity: `High`, `Medium`, or `Low`.
- Example: `- [Medium] Missing explicit source for claim about X.`
- If no material issues are found, state: `No material issues found`, then list only optional improvements.

## Quality Rules

- Prefer specific, text-anchored feedback over generic comments.
- Flag uncertainty explicitly; do not present assumptions as facts.
- When verification is not possible, mark the claim as `unverified`.
- Keep recommendations actionable and ordered by impact.
- Preserve the author's intent when suggesting rewrites.
