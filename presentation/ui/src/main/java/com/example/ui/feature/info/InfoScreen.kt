package com.example.ui.feature.info

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.model.CarouselModel
import com.example.ui.R
import com.example.ui.ShakaHomeTopAppBar
import com.example.ui.utils.Center
import com.example.ui.utils.ImageCarousel
import com.example.ui.utils.SimpleProgressBar
import com.example.viewmodel.StreamerInfoUiState
import com.example.viewmodel.StreamerInfoViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ForInfoRoute(
    modifier: Modifier = Modifier,
    viewModel: StreamerInfoViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    InfoScreen(
        onRefresh = { viewModel.fetchStreamerInfo() },
        uiState = state,
        modifier = modifier,
        isRefreshing = isRefreshing
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    uiState: StreamerInfoUiState,
    isRefreshing: Boolean,
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { onRefresh.invoke() },
        indicatorAlignment = Alignment.TopCenter,
        indicatorPadding = PaddingValues(100.dp)
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
            val context = LocalContext.current
            Feed(uiState, context, innerPadding)
        }
    }
}

@Composable
private fun Feed(
    uiState: StreamerInfoUiState,
    context: Context,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is StreamerInfoUiState.Loading -> {}

        is StreamerInfoUiState.Error -> {
            Toast.makeText(context, "???????????????", Toast.LENGTH_SHORT).show()
        }

        is StreamerInfoUiState.Empty -> {}

        is StreamerInfoUiState.Success -> {
            Column(modifier = modifier.padding(innerPadding)) {
                Column {
                    Text(
                        text = "??????",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = uiState.streamerInfo.baseInfo.displayName,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    uiState.streamerInfo.baseInfo.let {
                        ImageCarousel(
                            info = listOf(
                                CarouselModel(it.profileImageUrl, "????????????????????????"),
                                CarouselModel(it.offlineImageUrl, "?????????????????????"),
                            )
                        )
                    }
                    Text(
                        text = "??????????????????",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = uiState.streamerInfo.followInfo.total,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "?????????????????????",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                FollowList(
                    followInfo = uiState.streamerInfo.followInfo.FollowsInfo,
                )
            }
        }
    }
}
