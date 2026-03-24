#!/bin/sh

echo "==> Configuring git hooks..."

git config core.hooksPath githooks

chmod +x githooks/pre-commit
chmod +x githooks/pre-push

echo "[OK] Git hooks configured."

