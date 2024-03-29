package com.example.feature_info.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.compose.navigation.ShakaHomeNavigationDestination
import com.example.feature_info.ForInfoRoute

object InfoNavigation : ShakaHomeNavigationDestination {
    override val route: String = "info_route"
}

fun NavGraphBuilder.infoGraph(onSettingIconClick : ()-> Unit) {
    composable(route = InfoNavigation.route) {
        ForInfoRoute(
            onSettingIconClick = onSettingIconClick
        )
    }
}