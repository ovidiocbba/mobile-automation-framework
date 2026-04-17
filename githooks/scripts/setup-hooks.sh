#!/bin/sh

printf '==> Configuring git hooks...\n'

# Configure hooks path
git config core.hooksPath githooks

# Make hooks executable
chmod +x githooks/pre-commit
chmod +x githooks/pre-push

printf '[OK] Git hooks configured successfully.\n'

