package com.example.data.remote

import com.example.response.FollowInfoResponse
import com.example.response.NowStreamingInfoResponse
import com.example.response.PastVideosResponse
import com.example.response.StreamerBaseInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

// local propertiesに移動せねば
const val authorization = "Authorization: Bearer fweq5p27fq9j5yfmg5okuz0n11pu2m"
const val clientId = "Client-Id:x24r8nw8hd6arlvf1hhjuic2n154fl"
const val shakaSanId = 49207184

interface Api {

    @Headers(
        authorization,
        clientId
    )
    @GET("users")
    suspend fun fetchStreamerBaseInfo(
        @Query("id") streamerId: Int = shakaSanId
    ): Response<StreamerBaseInfoResponse>

    @Headers(
        authorization,
        clientId
    )
    @GET("users/follows")
    suspend fun fetchStreamerFollowInfo(
        @Query("from_id") streamerId: Int = shakaSanId,
        @Query("first") first: Int = 100
    ): Response<FollowInfoResponse>

    @Headers(
        authorization,
        clientId
    )
    @GET("users/follows")
    suspend fun fetchStreamerFollowInfoWithCursor(
        @Query("from_id") streamerId: Int = shakaSanId,
        @Query("first") first: Int = 100,
        @Query("after") after: String
    ): Response<FollowInfoResponse>

    @Headers(
        authorization,
        clientId
    )
    @GET("streams")
    suspend fun fetchNowStreamingInfo(
        @Query("user_id") streamerId: Int = shakaSanId
    ): Response<NowStreamingInfoResponse>

    @Headers(
        authorization,
        clientId
    )
    @GET("videos")
    suspend fun fetchVideos(
        @Query("user_id") streamerId: Int = shakaSanId,
        @Query("first") first: Int = 100,
    ): Response<PastVideosResponse>

}
