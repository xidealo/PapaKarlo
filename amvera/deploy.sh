#!/usr/bin/env bash
# Rebuilds the web bundle and refreshes amvera/site with the fresh output.
# After running this: git add -A && git commit -m "update" && git push amvera master
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

cd "$ROOT_DIR"
./gradlew :webApp:jsBrowserDistribution

rm -rf "$SCRIPT_DIR/site"
mkdir -p "$SCRIPT_DIR/site"
cp -R "$ROOT_DIR/webApp/build/dist/js/productionExecutable/." "$SCRIPT_DIR/site/"

# netlify.toml is only for Netlify; on Amvera nginx handles the proxying.
rm -f "$SCRIPT_DIR/site/netlify.toml"

# Source maps are only for debugging; drop them to keep the deploy small.
find "$SCRIPT_DIR/site" -name "*.map" -delete

echo "site/ updated. Next:"
echo "  cd \"$SCRIPT_DIR\" && git add -A && git commit -m \"update\" && git push amvera master"
