# see https://github.com/casey/just

_targets:
  @just --list --unsorted --list-heading $'Available targets:\n' --list-prefix "  "

_gradle_cache_usage gradle_user_home=env_var_or_default("GRADLE_USER_HOME", "~/.gradle"):
  #!/usr/bin/env sh
  echo ">>> Gradle cache usage"
  if sort -h {{justfile()}} &> /dev/null; then
    du -h -d 1 {{gradle_user_home}}/caches/ | sort -hr
  else
    du -d 1 {{gradle_user_home}}/caches/ \
      | sort -k 1 -nr \
      | awk '{ if ($1 > 1024^4) { printf "%.1fP\t%s\n", $1/1024^4+0.05,$2 }
          else if ($1 > 1024^3) { printf "%.1fT\t%s\n", $1/1024^3+0.05,$2 }
          else if ($1 > 1024^2) { printf "%.1fG\t%s\n", $1/1024^2+0.05,$2 }
          else if ($1 > 1024^1) { printf "%.1fM\t%s\n", $1/1024^1+0.05,$2 }
          else                  { printf "%.1fK\t%s\n", $1/1024^0+0.05,$2 }
        }'
  fi

_gradle +args="--version":
  #!/usr/bin/env sh
  cd {{justfile_directory()}} && ./gradlew {{args}}

_gradle_task task: (_gradle task)

# clean everything
clean: (_gradle "clean")

# build project
check: (_gradle_task "check")

# rebuild project
rerun: (_gradle_task "check --rerun-tasks")

# check for new versions
versions: (_gradle "refreshVersions")

RED    := "\\u001b[31m"
GREEN  := "\\u001b[32m"
YELLOW := "\\u001b[33m"
BOLD   := "\\u001b[1m"
RESET  := "\\u001b[0m"

# release the CLI
release:
     #!/usr/bin/env sh
     curl -v -H "Authorization: token $GITHUB_TOKEN" \
      "https://api.github.com/repos/daviddenton/rebackupable/dispatches" \
      -d '{"event_type": "release", "client_payload": {"VERSION": "0.0.0"}}'
