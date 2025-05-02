package com.deepakjetpackcompose.ainotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deepakjetpackcompose.ainotes.view.AddOrEditScreen
import com.deepakjetpackcompose.ainotes.view.MainScreen
import com.deepakjetpackcompose.ainotes.viewmodel.NotesViewmodel


@Composable
fun NavigationHelper(notesViewmodel: NotesViewmodel,modifier: Modifier = Modifier) {
    val navController= rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.MainScreen.routes
    ) {
        composable (route = NavigationDestination.MainScreen.routes){ MainScreen(notesViewmodel=notesViewmodel,navController=navController,modifier=modifier) }
        composable (route = NavigationDestination.AddOrUpdate.routes){ AddOrEditScreen(notesViewmodel=notesViewmodel,navController=navController,modifier=modifier) }
    }

}