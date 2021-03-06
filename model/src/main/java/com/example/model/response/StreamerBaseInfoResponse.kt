package com.example.model.response

import com.example.model.domain.StreamerBaseInfo
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

fun StreamerBaseInfoResponse.asDomainModel(): StreamerBaseInfo {
    val info = this.data[0]
    return StreamerBaseInfo(
        id = info.id,
        login = info.login,
        displayName = info.displayName,
        type = info.type,
        broadcasterType = info.broadcasterType,
        description = info.description,
        profileImageUrl = info.profileImageUrl,
        offlineImageUrl = info.offlineImageUrl,
        viewCount = info.viewCount,
        createdAt = info.createdAt
    )
}