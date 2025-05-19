package com.mandroidx.qiblax.calculator

class QiblaCalculatorImpl : QiblaCalculator {
    private val qiblaLat = 21.422487.toRadians()
    private val qiblaLng = 39.826206.toRadians()

    override fun calculateQiblaBearing(lat: Double, lng: Double): Float {
        val userLat = lat.toRadians()
        val userLng = lng.toRadians()
        val deltaLng = qiblaLng - userLng

        val y = sin(deltaLng)
        val x = cos(userLat) * tan(qiblaLat) - sin(userLat) * cos(deltaLng)
        val bearing = atan2(y, x).toDegrees().let { (it + 360) % 360 }
        return bearing.toFloat()
    }
}

private fun Double.toRadians() = Math.toRadians(this)
private fun Double.toDegrees() = Math.toDegrees(this)
private fun tan(value: Double) = kotlin.math.tan(value)
private fun sin(value: Double) = kotlin.math.sin(value)
private fun cos(value: Double) = kotlin.math.cos(value)
private fun atan2(y: Double, x: Double) = kotlin.math.atan2(y, x)