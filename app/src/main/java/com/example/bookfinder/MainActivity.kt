package com.example.bookfinder

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.bookfinder.ui.BookApp
import com.example.bookfinder.ui.theme.BookFinderTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookFinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    val context = LocalContext.current
//                    val display = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                        context.display
//                    } else {
//                        @Suppress("DEPRECATION")
//                        windowManager.defaultDisplay
//                    }
//                    val rotation = display!!.rotation // 0,2 - portrait, 1,3 - landscape

                    val orientation = this.resources.configuration.orientation // 1 - port, 2 - land
                    val windowSize = calculateWindowSizeClass(this)
                    BookApp(windowSize = windowSize.widthSizeClass, orientation = orientation)
                }
            }
        }
    }
}