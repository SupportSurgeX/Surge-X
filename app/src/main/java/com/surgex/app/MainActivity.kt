package com.surgex.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.surgex.app.ui.navigation.SurgeXNavigation
import com.surgex.app.ui.theme.SurgeXTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SurgeXTheme {
                SurgeXNavigation()
            }
        }
    }
}
