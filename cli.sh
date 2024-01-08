#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
CURRENT_DIR="$(pwd)"

if [ "$#" != "0" ]; then
    CLI_ARGS="--args=$*"
else
    CLI_ARGS=""
fi

"$SCRIPT_DIR"/gradlew -p "$SCRIPT_DIR" "-PcliWorkingDir=$CURRENT_DIR" runCli "$CLI_ARGS"
