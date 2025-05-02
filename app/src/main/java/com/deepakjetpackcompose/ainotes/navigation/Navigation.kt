package com.deepakjetpackcompose.ainotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.deepakjetpackcompose.ainotes.view.AddOrEditScreen
import com.deepakjetpackcompose.ainotes.view.MainScreen
import com.deepakjetpackcompose.ainotes.viewmodel.NotesViewmodel


@Composable
fun NavigationHelper(notesViewmodel: NotesViewmodel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.MainScreen.routes
    ) {
        composable(route = NavigationDestination.MainScreen.routes) {
            MainScreen(
                notesViewmodel = notesViewmodel,
                navController = navController,
                modifier = modifier
            )
        }
        composable(
            route = "${NavigationDestination.AddOrUpdate.routes}?title={title}&description={description}&type={type}&id={id}",
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("description") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("type") {
                    type = NavType.IntType
                    defaultValue = 1
                },
                navArgument ("id"){
                    type= NavType.IntType
                }
            )

        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val type = backStackEntry.arguments?.getInt("type") ?: 1
            val id=backStackEntry.arguments?.getInt("id")?:0
            AddOrEditScreen(
                id = id,
                title1 = title,
                desc1 = description,
                type = type,
                notesViewmodel = notesViewmodel,
                navController = navController,
                modifier = modifier
            )
        }
    }

}