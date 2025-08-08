# 📚 ArticleHub

**ArticleHub** is a modern Android application built using **Kotlin** and **Jetpack Compose** that allows users to browse, read, search, and save articles/blogs with a clean and intuitive UI.  
The app supports bookmarking (saved blogs), persistent state handling, and smooth navigation between screens.

---

## ✨ Features

- 📰 **Blog Listing** — Displays a list of blogs with title, image, and summary.
- ❤️ **Save Blogs** — Bookmark blogs for offline viewing in the Saved Blogs section.
- 🔍 **Search Functionality** — Filter blogs in real-time based on search queries.
- 📄 **Detailed Blog View** — View the complete content of a selected blog.
- 🗂 **Shared ViewModel** — State is shared between Blog List and Saved Blogs for a seamless experience.
- 🎨 **Modern UI** — Built entirely with Jetpack Compose.
- 🧩 **Dependency Injection** — Managed with [Koin](https://insert-koin.io/).

---

## 🛠 Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM (Model-View-ViewModel)
- **Navigation:** Android Navigation Compose
- **State Management:** `StateFlow` + `collectAsStateWithLifecycle`
- **Dependency Injection:** Koin
- **Other Tools:**
  - Kotlin Coroutines & Flows
  - Lifecycle Compose
  - Material 3 Components

---

## 📱 Screens

1. **Blog List Screen**
   - Displays list of all blogs.
   - Allows searching and saving blogs.

2. **Blog Content Screen**
   - Shows full content of the selected blog.

3. **Saved Blogs Screen**
   - Shows all bookmarked blogs stored in ViewModel state.

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Lguana | 
- JDK 11
- Kotlin 1.9+

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/Ayush92-byte/ArticleHub.git
2. Open in Android Studio.

3. Sync Gradle.

4. Run the app on an emulator or physical device.
