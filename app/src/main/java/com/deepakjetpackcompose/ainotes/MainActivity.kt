package com.deepakjetpackcompose.ainotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.deepakjetpackcompose.ainotes.navigation.NavigationHelper
import com.deepakjetpackcompose.ainotes.ui.theme.AiNotes
import com.deepakjetpackcompose.ainotes.viewmodel.NotesViewmodel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            val notesViewmodel: NotesViewmodel by viewModels()
            val isDark = notesViewmodel.isDark.collectAsState()
            AiNotes(darkTheme = isDark.value) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationHelper(notesViewmodel=notesViewmodel,modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

