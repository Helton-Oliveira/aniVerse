package com.example.myapplication.shared.domain.anime

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Anime(
    @SerialName("images")
    val img: File? = null,
    @SerialName("title_english")
    val name: String? = null,
    @SerialName("episodes")
    val episodeNumber: Int? = null,
    val synopsis: String? = null,
    var isFavorite: Boolean? = false
) {

    fun favorite() {
        this.isFavorite = true;
    }

}