package com.example.hello_tv_frontend

/** PUBLIC_INTERFACE
 * Model representing a single video for carousel rails.
 */
data class VideoItem(
    val thumbnailResId: Int, // Resource ID for the thumbnail image
    val title: String
)
