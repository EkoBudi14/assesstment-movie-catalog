# Movie Catalog

An Android app for browsing movies from [TMDB](https://www.themoviedb.org/), built with Jetpack Compose.

## Features

- List of official movie genres
- Discover movies by genre with endless scrolling
- Movie detail: poster, rating, genres, and overview
- YouTube trailer player
- User reviews with endless scrolling
- Loading, error with retry, and empty states

## Tech Stack

| Category     | Library                                   |
| ------------ | ----------------------------------------- |
| Language     | Kotlin                                    |
| UI           | Jetpack Compose, Material 3               |
| Architecture | Clean Architecture + MVVM                 |
| DI           | Hilt                                      |
| Networking   | Retrofit, OkHttp, Gson                    |
| Async        | Kotlin Coroutines, StateFlow              |
| Navigation   | Navigation Compose (type-safe routes)     |
| Image        | Coil                                      |
| Video        | android-youtube-player                    |

## Architecture

The code is split into three layers:

```
com.example.movie_catalog
├── data      # API service, response models, mappers, repository implementation
├── domain    # Domain models, repository interface, use cases
├── di        # Hilt modules (network, repository)
└── ui        # Screens, ViewModels, navigation, shared components
    ├── genre
    ├── movie
    ├── detail
    ├── review
    ├── navigation
    ├── common
    └── components
```

- **data** calls the TMDB API and maps responses into domain models.
- **domain** holds business logic in use cases and has no Android dependencies.
- **ui** observes `StateFlow` from each ViewModel and renders the screen state.

## Getting Started

### Requirements

- Android Studio (latest stable)
- JDK 17 or newer
- Android device or emulator running API 24+

### Setup

1. Get a TMDB API key (v3) at https://www.themoviedb.org/settings/api.
2. Open `local.properties` in the project root and add:
   ```properties
   API_KEY=your_tmdb_api_key
   ```
3. Sync Gradle.
4. Run the `app` configuration on an emulator or device.

> `local.properties` is git-ignored, so the API key is never committed.

## Screenshots

| Genres | Movies | Detail | Reviews |
| ------ | ------ | ------ | ------- |
| _add screenshot_ | _add screenshot_ | _add screenshot_ | _add screenshot_ |
