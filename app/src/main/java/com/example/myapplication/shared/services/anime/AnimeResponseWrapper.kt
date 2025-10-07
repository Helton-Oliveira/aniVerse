package com.example.myapplication.shared.services.anime

import com.example.myapplication.shared.domain.anime.Anime
import kotlinx.serialization.Serializable

@Serializable
data class AnimeResponseWrapper(
    val data: List<Anime>,
) {}