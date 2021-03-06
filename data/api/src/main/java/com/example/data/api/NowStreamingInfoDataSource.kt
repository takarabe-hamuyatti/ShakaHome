package com.example.data.api

import com.example.model.response.NowStreamingInfoResponse

class NowStreamingInfoDataSource(
    private val api: Api
) {
    suspend fun fetchNowStreamingInfo(): NowStreamingInfoResponse {
        return api.fetchNowStreamingInfo().body()!!
    }
}
