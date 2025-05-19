package com.mandroidx.qiblax.calculator

fun interface QiblaCalculator {
    fun calculateQiblaBearing(lat: Double, lng: Double): Float
}