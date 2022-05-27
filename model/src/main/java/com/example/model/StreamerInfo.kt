package com.example.model

data class StreamerInfo(
    val id: String,
    val login: String,
    val displayName: String,
    val type: String,
    val broadcasterType: String,
    val description: String,
    val profileImageUrl: String,
    val offlineImageUrl : String,
    val viewCount : Int,
    val createdAt : String
)