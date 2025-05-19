package com.mandroidx.qiblax.location

import android.location.Location

fun interface LocationProvider {
    suspend fun getCurrentLocation(): Location?
}
