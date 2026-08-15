package com.surgex.app.engine.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*

class AndroidLocationTracker(
    context: Context,
    private val locationTracker: LocationTracker
) {

    private val client =
        LocationServices.getFusedLocationProviderClient(context)

    private val request =
        LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            3000L
        )
            .setMinUpdateIntervalMillis(1500L)
            .setMinUpdateDistanceMeters(5f)
            .build()

    private var callback: LocationCallback? = null

    @SuppressLint("MissingPermission")
    fun start(
        onLocationChanged: (GeoPoint, Double) -> Unit
    ) {
        if (callback != null) return

        callback = object : LocationCallback() {

            override fun onLocationResult(
                result: LocationResult
            ) {
                result.lastLocation?.let { location ->

                    val distance =
                        locationTracker.updateLocation(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )

                    onLocationChanged(
                        GeoPoint(
                            location.latitude,
                            location.longitude
                        ),
                        distance
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
