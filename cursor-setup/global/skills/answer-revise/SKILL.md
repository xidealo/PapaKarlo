---
name: answer-revise
description: Revise an existing answer using prior review feedback, preserving strong sections, fixing gaps, and listing what still requires manual verification. Use when the user asks to revise, improve, or finalize an answer after critique/review.
---

# Answer Revise

## Core Instruction

Revise the output based on that review. Keep the parts that work, fix the gaps, and tell me what I should still check myself.

## Inputs Required

- Original request or goal.
- Current draft answer to revise.
- Provided review feedback (for example from CLEAR or similar framework).

If any input is missing, ask for it briefly before revising.

## Revision Workflow

1. Extract accepted strengths from the review and mark them as keep-as-is.
2. Extract issues and convert each into a concrete edit action.
3. Apply edits in priority order: correctness first, then completeness, then clarity/tone.
4. Keep original intent and voice unless review requires changing it.
5. Add a final checklist of what remains unverified or requires human confirmation.

## Output Format

Use this structure:

```markdown
## Revised Output
[Improved answer text]

## What Was Kept
- [Kept item 1 and why]
- [Kept item 2 and why]

## What Was Fixed
- [Fix 1 tied to review gap]
- [Fix 2 tied to review gap]

## Still Verify Manually
- [Claim/fact/source/date to verify]
- [Assumption or environment-specific item to check]
```

## Rules

- Do not re-litigate the whole review; focus on implementing it.
- Prefer minimal necessary edits; avoid rewriting strong sections.
- If a claim cannot be validated from provided context, mark it `unverified`.
- Keep "Still Verify Manually" specific and actionable.
- If no material fixes are needed, say `No material fixes required` and still provide manual checks.
