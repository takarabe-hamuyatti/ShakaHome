package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usecase.FetchStreamerInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StreamerInfoViewModel @Inject constructor(
    private val useCase: FetchStreamerInfoUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<StreamerInfoUiState> =
        MutableStateFlow(StreamerInfoUiState.Empty)
    val uiState = _uiState.asStateFlow()

    init {
        fetchStreamerInfo()
    }

    fun fetchStreamerInfo() {
        viewModelScope.launch {
            runCatching {
                StreamerInfoUiState.Success(useCase())
            }.onSuccess {
                _uiState.update { it }
            }.onFailure { e ->
                _uiState.update { StreamerInfoUiState.Error(e) }
            }
        }
    }

}