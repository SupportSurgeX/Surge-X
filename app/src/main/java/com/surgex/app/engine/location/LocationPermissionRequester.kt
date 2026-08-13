package com.surgex.app.engine.location

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun rememberLocationPermissionRequester(
    context: Context,
    onGranted: () -> Unit,
    onDenied: () -> Unit = {}
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestMultiplePermissions()
) { permissions ->

    val fineGranted =
        permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

    val coarseGranted =
        permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

    if (fineGranted || coarseGranted) {
        onGranted()
    } else {
        onDenied()
    }
}
