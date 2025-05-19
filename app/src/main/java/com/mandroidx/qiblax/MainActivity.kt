package com.mandroidx.qiblax

import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mandroidx.qiblax.location.LocationProvider
import com.mandroidx.qiblax.manager.QiblaCompassManager
import com.mandroidx.qiblax.model.CompassState
import com.mandroidx.qiblax.ui.theme.QiblaXTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QiblaXTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    QiblaCompassScreen()
                }
            }
        }
    }
}

@Composable
fun QiblaCompassScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Simulated LocationProvider (replace with actual implementation in real app)
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

    Scaffold { padding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "qibla: ${state.qiblaDirection} (${state.isFacingQibla})",
                modifier = Modifier.padding(padding),
                style = MaterialTheme.typography.titleLarge
            )
        }

    }
}