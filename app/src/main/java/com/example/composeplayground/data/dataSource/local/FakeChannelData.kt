package com.example.composeplayground.data.dataSource.local

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

fun getFakeChannel(): ChannelItem =
    ChannelItem(
        id = "",
        title = "Philipp Lackner",
        subscriberCount = "1.5M subscribers",
        customUrl = "@philipplackner",
        highThumbnailUrl = "https://yt3.ggpht.com/mhup7lzHh_c9b55z0edX65ReN9iJmTF2JU7vMGER9LTOora-NnXtvZdtn_vJmTvW6-y97z0Y=s800-c-k-c0x00ffffff-no-rj",
        subscribed = true
    )

fun getFakeVideos(): List<VideoItem> {
    val videos: MutableList<VideoItem> = mutableListOf()

    for (i in 1..50) {
        videos.add(
            VideoItem(
                id = "",
                title = "Full Guide to Dependency Injection With Koin for Compose Multiplatform - KMP for Beginners",
                highThumbnailUrl = "https://i.ytimg.com/vi/TAKZy3uQTdE/hqdefault.jpg",
                description = "In this video you'll find a full guide to Dependency Injection With Koin for Compose Multiplatform! Check out my course Building ...",
                publishedAt = 1L
            )
        )
    }
    return videos
}

data class TabItem(
    val title: String, val unSelectedColor: Color, val selectedColor: Color
)

enum class VideoType {
    VIDEO, SHORTS
}

data class VideoItem(
    val id: String,
    val title: String,
    val duration: String? = null,
    val viewCount: String? = null,
    val channelId: String? = null,
    val channelTitle: String? = null,
    val description: String,
    val publishedAt: Long,
    val highThumbnailUrl: String,
    val videoType: VideoType = VideoType.VIDEO
)

data class ChannelItem(
    val id: String,
    val title: String,
    val customUrl: String,
    val subscriberCount: String? = "- subscribers",
    val highThumbnailUrl: String,
    var subscribed: Boolean
)