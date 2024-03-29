package com.example.feature_streaming.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.compose.navigation.ShakaHomeNavigationDestination
import com.example.feature_streaming.ForReportRoute

object ReportNavigation : ShakaHomeNavigationDestination {
    override val route: String = "report_route"
}

fun NavGraphBuilder.reportGraph(){
    composable(route = ReportNavigation.route){
        ForReportRoute()
    }
}