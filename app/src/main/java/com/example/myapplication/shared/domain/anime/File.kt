package com.example.myapplication.shared.domain.anime

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JPG(
    @SerialName("image_url")
    val imageUrl: String? = "",
    @SerialName("small_image_url")
    val smallImageUrl: String? = "",
    @SerialName("large_image_url")
    val largeImageUrl: String? = "",
)

@Serializable
data class File(
    @SerialName("jpg")
    val jpg: JPG? = JPG()
)