package com.example.shakahome.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.ui.feature.drawer.settings.settingGraph
import com.example.ui.navigation.ReportDestination
import com.example.ui.navigation.infoGraph
import com.example.ui.navigation.reportGraph


@Composable
fun ShakaHomeNavHost(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ReportDestination.route,
    callbackOnItemClicked: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        reportGraph(
            windowSizeClass = windowSizeClass,
            callbackOnItemClicked = callbackOnItemClicked
        )
        infoGraph(
            windowSizeClass = windowSizeClass,
        )
        settingGraph()
    }
}