package com.deepakjetpackcompose.ainotes.util

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.luminance
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetStatusBarColor(color:androidx.compose.ui.graphics.Color) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(
        color = color,
        darkIcons = color.luminance() > 0.5f // dark icons if background is light
    )
}