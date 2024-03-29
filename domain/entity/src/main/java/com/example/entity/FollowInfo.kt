package com.example.entity

import com.example.core.util.DateUtil.utcToJtc
import com.example.response.FollowInfoResponse
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class FollowInfo(
    val followsList: List<EachFollowInfo>,
    val total: String,
    val cursor: String? = null
) {
    val hasCursor = cursor.isNullOrEmpty().not()

    companion object{
        fun from(followInfoResponse : FollowInfoResponse): FollowInfo {
            return FollowInfo(
                followsList = followInfoResponse.data.map {
                    EachFollowInfo(
                        followedAt = utcToJtc(it.followedAt),
                        dateForSort = DateForSort.utcToDate(it.followedAt),
                        fromId = it.fromId,
                        fromLogin = it.fromLogin,
                        fromName = it.fromName,
                        toId = it.toId,
                        toLogin = it.toLogin,
                        toName = it.toName
                    )
                },
                total = followInfoResponse.total.toString(),
                cursor = followInfoResponse.pagination?.cursor
            )
        }
    }
}
data class EachFollowInfo(
    val followedAt: String,
    val dateForSort: DateForSort,
    val fromId: String,
    val fromLogin: String,
    val fromName: String,
    val toId: String,
    val toLogin: String,
    val toName: String
)

data class DateForSort(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minutes: Int
) {
    companion object {
        fun utcToDate(utcTime: String): DateForSort {
            val dateTime = OffsetDateTime.parse(utcTime)
            val zoneId = ZoneId.of("Asia/Tokyo")
            val jtc = dateTime.atZoneSameInstant(zoneId).toOffsetDateTime()

            return DateForSort(
                jtc.format(DateTimeFormatter.ofPattern("yyyy")).toInt(),
                jtc.format(DateTimeFormatter.ofPattern("MM")).toInt(),
                jtc.format(DateTimeFormatter.ofPattern("dd")).toInt(),
                jtc.format(DateTimeFormatter.ofPattern("HH")).toInt(),
                jtc.format(DateTimeFormatter.ofPattern("mm")).toInt(),
            )
        }

    }
}
