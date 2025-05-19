package com.mandroidx.qiblax.manager

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mandroidx.qiblax.calculator.QiblaCalculator
import com.mandroidx.qiblax.calculator.QiblaCalculatorImpl
import com.mandroidx.qiblax.location.LocationProvider
import com.mandroidx.qiblax.model.CompassState
import com.mandroidx.qiblax.sensor.CompassSensorManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull

class QiblaCompassManager(
    context: Context,
    private val locationProvider: LocationProvider,
    private val calculator: QiblaCalculator = QiblaCalculatorImpl(),
    private val tolerance: Float = 5f
) : DefaultLifecycleObserver {

    private val sensorManager = CompassSensorManager(context)

    val compassStateFlow: Flow<CompassState> = sensorManager.azimuthFlow
        .mapNotNull { azimuth -> getCompassState(azimuth) }
        .flowOn(Dispatchers.Default)

    private suspend fun getCompassState(azimuth: Float): CompassState? {
        val location = locationProvider.getCurrentLocation() ?: return null
        val qiblaBearing =
            calculator.calculateQiblaBearing(location.latitude, location.longitude)
        val relativeQibla = (qiblaBearing - azimuth + 360) % 360
        val isFacing = relativeQibla <= tolerance || relativeQibla >= (360 - tolerance)

        return CompassState(
            compassDirection = azimuth,
            qiblaDirection = qiblaBearing,
            relativeQibla = relativeQibla,
            isFacingQibla = isFacing
        )
    }

    override fun onStart(owner: LifecycleOwner) {
        sensorManager.onStart(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        sensorManager.onStop(owner)
    }
}