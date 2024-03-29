package com.example.feature_info.uiState

data class InfoScreenUiState(
    val followInfoState: FollowInfoUiState = FollowInfoUiState.Loading,
    val streamerBaseInfoState: StreamerBaseInfoUiState = StreamerBaseInfoUiState.Loading,
    val isRefreshing: Boolean = true
)
