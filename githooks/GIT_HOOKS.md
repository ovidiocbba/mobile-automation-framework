# :book: Git Hooks Guide

This project uses **Git Hooks** to enforce code quality automatically
before commits and pushes.

---

<h1>Table of contents</h1>

- [:gear: 1. What Are Git Hooks](#gear-1-what-are-git-hooks)
- [:shield: 2. Hooks Used](#shield-2-hooks-used)
- [:wrench: 3. Installation](#wrench-3-installation)
- [:white_check_mark: 4. Expected Behavior](#white_check_mark-4-expected-behavior)

---

## :gear: 1. What Are Git Hooks

Git hooks are scripts that run automatically during Git actions like commit and push.

They are used in this project to:

- Enforce code formatting
- Detect code issues early
- Block broken or non-compiling code
- Enforce quality rules
- Keep team consistency

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

## :shield: 2. Hooks Used

### ✅ Pre-commit

Runs fast validations focused on **code quality before committing**.

Commands executed:

    ./gradlew spotlessCheck
    ./gradlew staticAnalysis

Behavior:

-   If formatting fails, the hook offers to run `spotlessApply`
-   If Checkstyle or PMD fails, the commit is blocked
-   Developer must fix issues before committing

------------------------------------------------------------------------

### 🚀 Pre-push

Runs deeper validations focused on **project integrity before pushing**.

Commands executed:

    ./gradlew assemble
    ./gradlew staticAnalysis
    ./gradlew javadoc

Behavior:

Push is blocked if:

-   The project does not compile
-   Spotless, Checkstyle, or PMD fail
-   Javadoc contains errors

This guarantees that **only clean, compilable, documented code reaches
the remote repository**.

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

## :wrench: 3. Installation

To install the hooks, run:

    githooks/scripts/setup-hooks.sh

This script will:

-   Configure Git to use the project hooks path
-   Enable `pre-commit` and `pre-push` hooks automatically
-   Work on macOS, Linux, and Git Bash on Windows

You only need to run this **once per clone**.

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

## :white_check_mark: 4. Expected Behavior

| Action            | What happens                                                   |
| ----------------- | -------------------------------------------------------------- |
| `git commit`      | Validates code formatting and static analysis (Checkstyle/PMD) |
| `git push`        | Validates compilation, static analysis, and Javadoc            |
| Formatting issues | You are prompted to auto-fix using Spotless                    |
| Quality issues    | Commit/Push is blocked until the issues are fixed              |

These hooks ensure the repository is always:

-   Formatted
-   Clean
-   Compilable
-   Documented

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

<div align="left">
  <b><a href="../README.md#table-of-contents">↥ Back to main page</a></b>
</div>
