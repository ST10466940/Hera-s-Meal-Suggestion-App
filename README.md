# Hera’s Meal Picker

A tiny native Android app that helps Hera decide what to eat based on the time of day. Enter a time like **Morning**, **Afternoon snack**, or **After dinner snack**, and the app suggests fun, rotating options.

## ✨ Features
- Simple, engaging UI with quick-fill buttons
- Clear suggestions for: Morning / Mid-morning snack / Afternoon / Afternoon snack / Dinner / After dinner snack
- Helpful error messages for unknown inputs
- **Logs** (Logcat) to demonstrate understanding of control flow

## 📸 Screenshots
> Replace these with your own
- ![Home](docs/screenshot-home.png)
- ![Suggestion](docs/screenshot-suggest.png)
- ![Actions Artifact](docs/screenshot-actions-artifact.png)

## 🏗️ Tech
- Kotlin, Jetpack Compose (Material 3)
- Gradle, Android Studio
- GitHub Actions (CI): build, lint, test, upload APK

## 🧠 Design considerations
- **Clarity first:** one input, one output, plus quick-fill to reduce friction.
- **Motivational errors:** actionable prompts (“Try Morning, Dinner…”).
- **Deterministic categories via `if` statements:** meets rubric and keeps logic transparent.

## 🚀 Getting started
1. Open in Android Studio (latest).
2. Run on emulator or device.
3. Open **Logcat**, filter by `MealSuggest`, try different inputs.

## 🔍 Manual testing (no unit tests required)
- Morning → Breakfast suggestion.
- Mid-morning snack → Light snack.
- Afternoon → Lunch.
- Afternoon snack → Quick bite.
- Dinner → Main course.
- After dinner snack → Dessert.
- Unknown / blank → Friendly guidance + Toast + Logcat entry.

## 🔗 Version control (Git/GitHub)
```bash
git init
git add .
git commit -m "chore: initial commit"
git branch -M main
git remote add origin https://github.com/<you>/<repo>.git
git push -u origin main
