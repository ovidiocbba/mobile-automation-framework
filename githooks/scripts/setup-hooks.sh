#!/bin/sh

echo "==> Configuring git hooks..."

# Configure hooks path
git config core.hooksPath githooks

# Make hooks executable
chmod +x githooks/pre-commit
chmod +x githooks/pre-push

echo "[OK] Git hooks configured successfully."

