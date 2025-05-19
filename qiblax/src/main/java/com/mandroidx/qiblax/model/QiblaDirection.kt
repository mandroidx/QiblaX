package com.mandroidx.qiblax.model

// --- Model ---
data class CompassState(
    val compassDirection: Float = 0.0f,
    val qiblaDirection: Float = 0.0f,
    val relativeQibla: Float = 0.0f,
    val isFacingQibla: Boolean = false
)
