#!/usr/bin/env bash
# Установка кастомных rules, skills и agents Cursor на macOS/Linux
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CURSOR_HOME="${CURSOR_HOME:-$HOME/.cursor}"
PROJECT_AGENTS_DIR="${1:-}"

echo "==> Cursor setup installer"
echo "    Source: $SCRIPT_DIR"
echo "    Target: $CURSOR_HOME"

mkdir -p "$CURSOR_HOME/rules"
mkdir -p "$CURSOR_HOME/skills"
mkdir -p "$CURSOR_HOME/agents"

# Global rules
echo "==> Installing global rules..."
cp -f "$SCRIPT_DIR/global/rules/"*.mdc "$CURSOR_HOME/rules/"

# Global skills
echo "==> Installing global skills..."
for skill_dir in "$SCRIPT_DIR/global/skills/"*/; do
  skill_name="$(basename "$skill_dir")"
  mkdir -p "$CURSOR_HOME/skills/$skill_name"
  cp -f "$skill_dir/SKILL.md" "$CURSOR_HOME/skills/$skill_name/"
done

# Global agents
echo "==> Installing global agents..."
cp -f "$SCRIPT_DIR/global/agents/"*.md "$CURSOR_HOME/agents/"

# Project agents (optional)
if [[ -n "$PROJECT_AGENTS_DIR" ]]; then
  echo "==> Installing project agents into: $PROJECT_AGENTS_DIR"
  mkdir -p "$PROJECT_AGENTS_DIR"
  cp -f "$SCRIPT_DIR/project/agents/"*.md "$PROJECT_AGENTS_DIR/"
else
  echo ""
  echo "Project agents NOT installed (path not passed)."
  echo "To install PapaKarlo agents, run:"
  echo "  ./install.sh /path/to/PapaKarlo/.cursor/agents"
fi

echo ""
echo "Done."
echo ""
echo "Next steps:"
echo "  1. Restart Cursor (or reload window)"
echo "  2. Copy user rules from global/user-rules.md → Cursor Settings → Rules"
echo "  3. Verify: ls ~/.cursor/rules ~/.cursor/skills ~/.cursor/agents"
