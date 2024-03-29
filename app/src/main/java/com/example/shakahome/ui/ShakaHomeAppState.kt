package com.example.shakahome.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Grid3x3
import androidx.compose.material.icons.filled.Stream
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Grid3x3
import androidx.compose.material.icons.outlined.Stream
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.feature_drawer.navigation.SettingsNavigation
import com.example.feature_info.navigation.InfoNavigation
import com.example.feature_streaming.navigation.ReportNavigation
import com.example.feature_streaming.navigation.StreamingDestination
import com.example.resource.R
import com.example.shakahome.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberShakaHomeAppState(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): ShakaHomeAppState {
    return remember(navController) {
        ShakaHomeAppState(
            navController = navController,
            drawerState = drawerState,
            coroutineScope = coroutineScope
        )
    }
}

@Stable
@OptIn(ExperimentalMaterial3Api::class)
class ShakaHomeAppState(
    val navController: NavHostController,
    val drawerState: DrawerState,
    val coroutineScope: CoroutineScope,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    private var _selectedDrawerItem: MutableState<DrawerItem?> = mutableStateOf(null)
    val selectedItem get() = _selectedDrawerItem.value

    private var _isShowBottomBar: MutableState<Boolean> = mutableStateOf(true)
    val isShowBottomBar get() = _isShowBottomBar.value

    init {
        coroutineScope.launch {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                _selectedDrawerItem.value = destination.route?.let {
                    DrawerItem.values().firstOrNull { it.navRoute == destination.route }
                }

                _isShowBottomBar.value = destination.route?.let { route ->
                    TOP_LEVEL_DESTINATIONS.any {
                        it.route == route
                    }
                } == true
            }
        }
    }

    fun onClickBottomBarItem(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun onClickDrawerItem(drawerItem: DrawerItem) {
        navController.navigate(drawerItem.navRoute)
        coroutineScope.launch {
            drawerState.close()
        }
    }

    fun onClickSettingIcon() {
        navController.navigate(SettingsNavigation.route)
    }

    val TOP_LEVEL_DESTINATIONS = listOf(
        TopLevelDestination(
            route = ReportNavigation.route,
            selectedIcon = Icons.Filled.Grid3x3,
            unselectedIcon = Icons.Outlined.Grid3x3,
            iconTextId = R.string.report
        ),
        TopLevelDestination(
            route = StreamingDestination.route,
            selectedIcon = Icons.Filled.Stream,
            unselectedIcon = Icons.Outlined.Stream,
            iconTextId = R.string.streaming
        ),
        TopLevelDestination(
            route = InfoNavigation.route,
            selectedIcon = Icons.Filled.Apps,
            unselectedIcon = Icons.Outlined.Apps,
            iconTextId = R.string.info
        ),
    )

}