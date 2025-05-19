
# QiblaX 📍🧭

**QiblaX** is an Android library that determines the Qibla direction using device sensors and location. It emits compass state as a `Flow` and is lifecycle-aware, making it easy to integrate into modern Android apps.

---

## 📦 Installation

In your **module-level** `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.mandroidx.QiblaX:qiblax:1.0.0")
}
```

In your **project-level** `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

---

## 🧭 Usage

### 1. Implement a LocationProvider

```kotlin
class MyLocationProvider(private val context: Context) : LocationProvider {
    override fun getCurrentLocation(): Location? {
        // Use FusedLocationProviderClient or other logic here
        return null
    }
}
```

### 2. Activity
```kotlin
val locationProvider = remember {
        object : LocationProvider {
            override fun getCurrentLocation(): Location = Location("").apply {
                latitude = 21.4241
                longitude = 39.8173
            }
        }
    }

    val qiblaCompassManager = remember {
        QiblaCompassManager(
            context = context,
            locationProvider = locationProvider
        )
    }

    // Start/Stop manager with LifecycleObserver
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(qiblaCompassManager)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(qiblaCompassManager)
        }
    }

    val state by qiblaCompassManager.compassStateFlow
        .collectAsStateWithLifecycle(initialValue = CompassState())

```

---

## 📄 License

Distributed under the MIT License.

---

## 🔗 Links

- GitHub: [https://github.com/mandroidx/QiblaX](https://github.com/mandroidx/QiblaX)
- JitPack: [https://jitpack.io/#mandroidx/QiblaX](https://jitpack.io/#mandroidx/QiblaX)
