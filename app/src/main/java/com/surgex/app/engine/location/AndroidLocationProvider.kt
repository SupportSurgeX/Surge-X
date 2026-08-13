package com.surgex.app.engine.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*

class AndroidLocationProvider(
    context: Context
) {

    private val client =
        LocationServices.getFusedLocationProviderClient(context)

    private var callback: LocationCallback? = null

    @SuppressLint("MissingPermission")
    fun start(
        onLocation: (latitude: Double, longitude: Double) -> Unit
    ) {

        val request =
            LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                3000L
            )
                .setMinUpdateIntervalMillis(1500L)
                .setWaitForAccurateLocation(false)
                .build()

        callback =
            object : LocationCallback() {

                override fun onLocationResult(
                    result: LocationResult
                ) {

                    result.lastLocation?.let { location ->

                        onLocation(
                            location.latitude,
                            location.longitude
                        )
                    }
                }
            }

        client.requestLocationUpdates(
            request,
            callback!!,
            Looper.getMainLooper()
        )
    }

    fun stop() {

        callback?.let {
            client.removeLocationUpdates(it)
        }

        callback = null
    }
}
