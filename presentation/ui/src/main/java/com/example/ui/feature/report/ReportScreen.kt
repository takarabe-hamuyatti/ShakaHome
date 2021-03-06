package com.example.ui.feature.report

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.ui.R
import com.example.ui.ShakaHomeTopAppBar
import com.example.viewmodel.NowStreamingInfoState
import com.example.viewmodel.PastVideosInfoState
import com.example.viewmodel.ReportViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ForReportRoute(
    modifier: Modifier = Modifier,
    viewModel: ReportViewModel = hiltViewModel()
) {
    val nowStreamInfoState by viewModel.nowStreamingInfoUiState.collectAsState()
    val pastVideosInfoState by viewModel.pastVideosInfoState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    ReportScreen(
        isRefreshing = isRefreshing,
        onRefreshing = { viewModel.refresh() },
        nowStreamingInfoUiState = nowStreamInfoState,
        pastVideosInfoState = pastVideosInfoState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefreshing: () -> Unit,
    nowStreamingInfoUiState: NowStreamingInfoState,
    pastVideosInfoState: PastVideosInfoState
) {
    SwipeRefresh(
        modifier = modifier.fillMaxHeight(),
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { onRefreshing.invoke() },
        indicatorAlignment = Alignment.TopCenter,
        indicatorPadding = PaddingValues(
            100.dp
        )
    ) {
        Scaffold(
            topBar = {
                ShakaHomeTopAppBar(
                    titleRes = R.string.app_name,
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
            LazyColumn(
                modifier = modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                NowStreamingInfo(
                    uiState = nowStreamingInfoUiState,
                    context = context
                )
                PastVideosInfo(
                    uiState = pastVideosInfoState,
                    context = context
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun LazyListScope.NowStreamingInfo(
    uiState: NowStreamingInfoState,
    modifier: Modifier = Modifier,
    context: Context
) {
    when (uiState) {
        is NowStreamingInfoState.Loading -> {}

        is NowStreamingInfoState.Error -> {
            Toast.makeText(context, "???????????????", Toast.LENGTH_SHORT).show()
        }

        is NowStreamingInfoState.Empty -> {
            item {
                Text(
                    text = "????????????????????????????????????",
                    modifier = modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        }

        is NowStreamingInfoState.Success -> {
            item {
                Card(
                    Modifier
                        .padding(16.dp)
                        .wrapContentSize()
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    ) {
                        Text(
                            text = "Now Streaming",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        AsyncImage(
                            model = uiState.nowStreamingInfo.thumbnailUrl,
                            contentDescription = "aa",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                        Text(
                            text = uiState.nowStreamingInfo.title,
                            textAlign = TextAlign.Start,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row {
                            Text(
                                text = "???????????? ",
                            )
                            Text(
                                text = uiState.nowStreamingInfo.viewerCount.toString(),
                                textAlign = TextAlign.Center
                            )
                        }
                        Row {
                            Text(
                                text = uiState.nowStreamingInfo.startedAt,
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun LazyListScope.PastVideosInfo(
    uiState: PastVideosInfoState,
    modifier: Modifier = Modifier,
    context: Context
) {
    when (uiState) {
        is PastVideosInfoState.Empty -> {

        }

        is PastVideosInfoState.Error -> {

        }

        is PastVideosInfoState.Loading -> {

        }

        is PastVideosInfoState.Success -> {
            items(uiState.pastVideosState.pastVideos) { item ->
                Card(
                    Modifier
                        .padding(16.dp)
                        .wrapContentSize()
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    ) {
                        if (item.thumbnailUrl.isEmpty()) {
                            Text(
                                text = "???????????????????????????????????????",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                        } else {
                            AsyncImage(
                                model = item.thumbnailUrl,
                                contentDescription = "aa",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                        }
                        Text(
                            text = item.title,
                            textAlign = TextAlign.Start,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row {
                            Text(
                                text = "???????????? ",
                            )
                            Text(
                                text = item.viewCount.toString(),
                                textAlign = TextAlign.Center
                            )
                        }
                        Row {
                            Text(
                                text = "${item.createdAt}~  ",
                            )
                            Text(
                                text = item.duration
                            )
                        }
                    }
                }
            }
        }
    }
}
