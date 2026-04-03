# ⚠️ CI Notes

## ⚠️ Gradle Wrapper Permissions (Required for CI)

When running this project in CI environments such as GitHub Actions or Jenkins, the Gradle wrapper file `gradlew` may not have execution permissions.

This usually happens when the repository was created or committed from Windows, because executable permissions are not preserved.

If the permission is missing, the CI pipeline may fail with:

```
./gradlew: Permission denied
ERROR: script returned exit code 126
```

To prevent this problem, CI pipelines must grant execution permission to the Gradle wrapper before running Gradle commands.

---

## ✅ Fix (Recommended)

The best long‑term solution is to commit the executable permission directly in Git so CI systems do not need to fix it each run.

Run this once:

```bash
git update-index --chmod=+x gradlew
git commit -m "fix(ci): make gradlew executable for CI environments"
git push
```

After this change, Jenkins, Docker agents, and GitHub Actions will recognize `gradlew` as executable automatically.

---
