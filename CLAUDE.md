# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Development Commands

```bash
# Build
./gradlew build

# Lint check (required to pass CI)
./gradlew ktlintCheck

# Auto-format code
./gradlew ktlintFormat

# Clean build
./gradlew clean build
```

CI runs `ktlintCheck` then `build` on PRs to `develop` and `main`.

### Local Setup

Create `local.properties` in the project root (not committed) with:
```
kakao.native.app.key=<kakao_key>
kakao.native.app.key.manifest=kakao<kakao_key>
dev.base.url=<dev_api_url>
prod.base.url=<prod_api_url>
```

## Architecture

Single-module app (`app`) following **Clean Architecture** with **MVI** pattern and **Jetpack Compose** UI.

### Layer Structure

```
org.android.bbangzip/
├── data/           # Retrofit services, repositories impl, DataStore, auth interceptor
├── domain/         # Repository interfaces, domain models, use cases
├── presentation/   # ViewModels, Compose screens, navigation
└── ui/             # Theme
```

### MVI Pattern

All screens use `BaseViewModel<Event, State, Reduce, Effect>` (`presentation/common/base/BaseViewModel.kt`):
- **Event** — user interactions dispatched from UI
- **State** — immutable UI state (Parcelable, persisted via SavedStateHandle)
- **Reduce** — state mutations applied by the reducer
- **SideEffect** — one-time effects (navigation, toasts) sent via Channel

Each screen module contains: `*ViewModel.kt`, `*Screen.kt`, `*Navigation.kt`, and a `*Contract.kt` for the sealed interfaces.

### Dependency Injection

Hilt with `SingletonComponent` for app-level singletons. Two OkHttp/Retrofit qualifiers:
- `@Auth` — authenticated client (injects bearer token, handles 401 token refresh)
- `@BbangZip` — unauthenticated client

`AuthInterceptor` handles automatic token refresh on 401 and broadcasts force-logout events via `AuthEventManager` if refresh fails.

### Navigation

Type-safe Compose Navigation using `@Serializable` route objects. Each feature defines its route and `NavGraphBuilder` extension in `*Navigation.kt`. Bottom nav tabs defined in `BottomNavigationType`.

### Data Persistence

User credentials and preferences stored via **Proto DataStore** (`UserPreferences` protobuf message defined in `app/src/main/proto/user_prefs.proto`). Access via `UserLocalDataSource`.

### Key Libraries

| Purpose | Library |
|---|---|
| UI | Jetpack Compose (BOM 2026.03.00) |
| DI | Hilt 2.59.2 |
| Networking | Retrofit 2.11.0 + OkHttp 4.12.0 |
| Serialization | Kotlinx Serialization JSON + Protobuf Lite |
| Image loading | Coil 3.0.3 |
| Auth | Kakao SDK v2 2.21.1 |
| Local storage | Protobuf DataStore 1.1.5 |
| Logging | Timber (debug builds only) |

### Screen Modules

Located under `presentation/ui/`:
`splash`, `auth`, `main`, `onboarding`, `todo`, `timer`, `category` (+ `addcategory`, `managecategory`, `editcategory`), `commitment`, `my`, `profileedit`, `screensetting`, `shared` (SharedViewModel for cross-screen state), `dummy`

### Network Response Convention

All API responses are wrapped in `BaseResponse<T>`. Services live in `data/datasource/remote/service/`.
