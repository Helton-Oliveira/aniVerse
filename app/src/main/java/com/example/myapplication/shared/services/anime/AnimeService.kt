package com.example.myapplication.shared.services.anime

import com.example.myapplication.config.NetworkResult
import com.example.myapplication.shared.domain.anime.Anime
import com.example.myapplication.shared.enumerations.Gender
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess

class AnimeService(private val http: HttpClient) {
    private val BASE_URL = "https://api.jikan.moe/v4";


    suspend fun getTopAnimes(): NetworkResult<List<Anime>> {
        val response = http.get("$BASE_URL/top/anime");

        return if (response.status.isSuccess()) {
            NetworkResult.Success(response.body<AnimeResponseWrapper>().data)
        } else {
            NetworkResult.Failure(RuntimeException())
        }
    }

    suspend fun getAllAnimeByGender(gender: Gender): NetworkResult<List<Anime>> {
        val response = http.get("$BASE_URL/anime?genres=${gender.value}");

        return if (response.status.isSuccess()) {
            NetworkResult.Success(response.body<AnimeResponseWrapper>().data)
        } else {
            NetworkResult.Failure(RuntimeException())
        };
    }
}