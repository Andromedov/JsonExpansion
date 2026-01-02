<div align="center">

# 🧩 Paradise JSON Expansion

**A custom PlaceholderAPI expansion for reading local JSON files.** *Designed exclusively for **ParadiseCraft** to dynamically fetch data from external scripts and configurations.*

[![Java](https://img.shields.io/badge/Java-21%2B-ed8b00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Gradle](https://img.shields.io/badge/Gradle-Build-02303a?style=for-the-badge&logo=gradle&logoColor=white)](https://gradle.org/)
[![PlaceholderAPI](https://img.shields.io/badge/Hook-PlaceholderAPI-2d9f4e?style=for-the-badge)](https://placeholderapi.com/)

</div>

---

## ✨ Features

- **📂 Local File Loading** Automatically scans and reads `.json` files from the `plugins/PlaceholderAPI/json/` directory.
- **⚡ Smart Caching & Hot-Reload** Caches JSON content in RAM for instant access. Automatically detects file changes on disk and reloads only when necessary. **Zero performance impact.**
- **🔍 Deep Nesting Support** Full support for dot notation to access nested keys (e.g., `messages.welcome.text`).
- **🧠 Intelligent Matching** Correctly distinguishes between similar filenames (e.g., `shop.json` vs `shop_items.json`).
- **✨ Clean Syntax** Simplified usage: no need to include `.json` in your placeholders.

---

## 🛠️ Prerequisites

To build this project, you need:

* **JDK 21** or higher.
* **Gradle** (optional, wrapper is included).

---

## 🚀 Installation & Build

### 1. Build the JAR
Open your terminal in the project directory and run:

**Windows (PowerShell/CMD):**
```powershell
.\gradlew build
```

**Linux/macOS:**
```shell
./gradlew build
```

> The compiled file will be located at: build/libs/LocalJsonExpansion-1.x.x.jar

### 2. Install on Server
1.  Stop your Minecraft server.
2.  Navigate to `plugins/PlaceholderAPI/expansions/`.
3.  Drop the `LocalJsonExpansion-1.x.x.jar` file there.
4.  Start the server.

---

## 📖 Usage Guide

### 1. File Placement
The expansion looks for files in a specific folder. You must create it if it doesn't exist:
```text
plugins/PlaceholderAPI/json/
```

### 2. Placeholder Syntax
The pattern is:
```text
%json_<filename>_<path_to_key>%
```

| Parameter       | Description                                               |
|:----------------|:----------------------------------------------------------|
| `<filename>`    | The name of the file **without** `.json`.                 |
| `<path_to_key>` | The key inside the JSON. Use dots `.` for nested objects. |

### 3. Examples

#### Scenario A: Simple Key
**File:** `plugins/PlaceholderAPI/json/holidays.json`
```json
{
  "first": "New Year",
  "second": "Christmas"
}
```

**Placeholders:**

- `%json_holidays_first%` → Returns: New Year
- `%json_holidays_second%` → Returns: Christmas

#### Scenario B: Nested Objects
**File:** `plugins/PlaceholderAPI/json/server.json`
```json
{
  "status": "online",
  "motd": {
    "line1": "Welcome to Paradise!",
    "maintenance": false
  }
}
```
**Placeholders:**
- `%json_server_status%` → Returns: **online**
- `%json_server_motd.line1%` → Returns: **Welcome to Paradise!**
## 🔧 Troubleshooting

<details>
<summary><strong>❌ Placeholder returns <code>File not found</code></strong></summary>

* Ensure the file exists in `plugins/PlaceholderAPI/json/`.
* Check if the filename in the placeholder matches the actual filename (case-insensitive, but exact spelling required).
* Do not include `.json` in the placeholder macro.
</details>

<details>
<summary><strong>❌ Placeholder returns <code>Invalid JSON</code></strong></summary>

* The file exists but contains syntax errors.
* Validate your JSON content using a site like [jsonlint.com](https://jsonlint.com).
</details>

<details>
<summary><strong>❌ Placeholder returns <code>...</code></strong></summary>

* The file was found, but the specific key does not exist.
* Check for typos in the path (e.g., `motd.line1` vs `motd.line_1`).
</details>