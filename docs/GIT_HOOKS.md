# :book: Git Hooks Guide

This project uses **Git Hooks** to enforce code quality before commits and pushes.

---

<h1>Table of contents</h1>

- [:gear: 1. What Are Git Hooks](#gear-1-what-are-git-hooks)
- [:shield: 2. Hooks Used](#shield-2-hooks-used)
- [:wrench: 3. Installation](#wrench-3-installation)
- [:white_check_mark: 4. Expected Behavior](#white_check_mark-4-expected-behavior)

---

## :gear: 1. What Are Git Hooks

Git hooks are scripts that run automatically during Git actions like commit and push.

They help:
- Enforce code quality
- Prevent broken commits
- Maintain team consistency

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

## :shield: 2. Hooks Used

### Pre-commit
- Runs code formatting and validation
- Command:
```
./gradlew spotlessCheck
```

### Pre-push
- Runs full quality checks
- Command:
```
./gradlew preCommitCheck
```

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

## :wrench: 3. Installation

To install the hooks, just run:

```
githooks/scripts/setup-hooks.sh
```

This script will:
- Configure Git to use the project hooks
- Enable pre-commit and pre-push hooks

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

## :white_check_mark: 4. Expected Behavior

- Code is formatted before commit
- Push is blocked if:
    - Formatting fails
    - Static analysis fails
    - Javadoc fails

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

<div align="left">
  <b><a href="../README.md#table-of-contents">↥ Back to main page</a></b>
</div>
