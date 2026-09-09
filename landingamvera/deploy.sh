#!/usr/bin/env bash
# Rebuilds the landing bundle and refreshes landingamvera/site with the fresh output.
# After running this: git add landingamvera/ && git commit -m "update" && git push
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
LANDING_DIR="$ROOT_DIR/landing"

cd "$LANDING_DIR"
npm ci
npm run build

rm -rf "$SCRIPT_DIR/site"
mkdir -p "$SCRIPT_DIR/site"
cp -R "$LANDING_DIR/dist/." "$SCRIPT_DIR/site/"

# Source maps are only for debugging; drop them to keep the deploy small.
find "$SCRIPT_DIR/site" -name "*.map" -delete

echo "landingamvera/site/ updated. Next:"
echo "  git add landingamvera/ && git commit -m \"update\" && git push"
