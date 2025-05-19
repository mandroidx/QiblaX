package com.mandroidx.qiblax.location

import android.location.Location

fun interface LocationProvider {
    fun getCurrentLocation(): Location?
}
