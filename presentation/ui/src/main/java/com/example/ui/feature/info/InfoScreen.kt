package com.example.ui.feature.info

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.R
import com.example.ui.ShakaHomeTopAppBar
import com.example.ui.utils.Center
import com.example.ui.utils.SimpleProgressBar
import com.example.viewmodel.StreamerInfoUiState
import com.example.viewmodel.StreamerInfoViewModel

@Composable
fun ForInfoRoute(
    modifier: Modifier = Modifier,
    viewModel: StreamerInfoViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    InfoScreen(onUpdate = { viewModel.fetchStreamerInfo() }, uiState = state, modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    modifier: Modifier = Modifier,
    onUpdate: () -> Unit,
    uiState: StreamerInfoUiState,
) {
    Scaffold(
        topBar = {
            ShakaHomeTopAppBar(
                titleRes = R.string.app_name,
                actionIcon = Icons.Outlined.Settings,
                actionIconContentDescription = stringResource(
                    id = R.string.top_app_bar_action_button_content_desc
                ),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                ),
            )
        }, containerColor = Color.Transparent
    ) { innerPadding ->
        LazyColumn(
            modifier
                .fillMaxHeight()
                .padding(innerPadding)
        ) {
            feed(uiState)
        }
    }
}


private fun LazyListScope.feed(
    uiState: StreamerInfoUiState
) {
    when (uiState) {
        is StreamerInfoUiState.Loading -> {
            item {
                Center {
                    SimpleProgressBar()
                }
            }
        }

        is StreamerInfoUiState.Error -> {}
        is StreamerInfoUiState.Success -> {
            item{
                Center {
                    Text(text = "成功")
                }
            }
        }
        is StreamerInfoUiState.Empty -> {}
    }

}