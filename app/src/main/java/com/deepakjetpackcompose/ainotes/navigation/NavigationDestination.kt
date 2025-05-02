package com.deepakjetpackcompose.ainotes.navigation

sealed class NavigationDestination(var routes:String) {
    object MainScreen: NavigationDestination("main")
    object AddOrUpdate: NavigationDestination("add")
}