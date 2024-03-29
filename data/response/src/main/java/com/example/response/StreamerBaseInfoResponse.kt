package com.example.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamerBaseInfoResponse(
    val `data`: List<StreamerBaseInfoResponseContent>
)

@Serializable
data class StreamerBaseInfoResponseContent(
    val id: Int,
    val login: String,
    @SerialName("display_name") val displayName: String,
    val type: String,
    @SerialName("broadcaster_type") val broadcasterType: String,
    val description: String,
    @SerialName("profile_image_url") val profileImageUrl: String,
    @SerialName("offline_image_url") val offlineImageUrl: String,
    @SerialName("view_count") val viewCount: Int,
    @SerialName("created_at") val createdAt: String
)
