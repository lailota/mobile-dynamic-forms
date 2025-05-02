# Mobile Dynamic Forms

A cross-platform mobile application demonstrating dynamic form rendering based on JSON definitions. Includes:

* **Android** implementation using Kotlin and Jetpack Compose.
* **iOS** implementation using SwiftUI.

## Features

* Load form definitions from JSON files (`all-fields.json`, `200-form.json`).
* Render fields: description (HTML), text, number, dropdown, date (Android), with fallbacks for other types.
* Organize fields into sections.
* Persist form entries locally (Room database on Android, UserDefaults on iOS).
* List and create new entries.

## Prerequisites

**Android**

* JDK 11+
* Android Studio Arctic Fox or later
* Gradle 7+

**iOS**

* Xcode 15+
* Swift 5.9+

## Installation

```bash
git clone https://github.com/lailota/mobile-dynamic-forms.git
cd mobile-dynamic-forms
```

### Android

1. Open the `android/` folder in Android Studio.
2. Sync Gradle and build the project.
3. Run on an emulator or physical device.

### iOS

1. Open `ios/DynamicForms.xcodeproj` in Xcode.
2. Ensure JSON files (`all-fields.json`, `200-form.json`) are included in the app bundle.
3. Build and run on a simulator or device.

## Project Structure

```
mobile-dynamic-forms/
├── android/           # Android app module
│   └── app/
│       ├── src/main/assets/  # JSON definitions
│       ├── java/              # Kotlin source
│       └── ...
└── ios/DynamicForms/  # iOS SwiftUI project
    ├── Models/        # Field, Section definitions
    ├── Views/         # Landing, Entries, Form screens
    └── ...
```

## Usage

1. **Landing**: Select a form to view or fill.
2. **Entries**: Browse past submissions or start a new one.
3. **Form**: Fill fields dynamically; submit to persist locally.
