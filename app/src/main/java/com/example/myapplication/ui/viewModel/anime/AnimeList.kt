package com.example.myapplication.ui.viewModel.anime

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.config.onFailure
import com.example.myapplication.config.onSuccess
import com.example.myapplication.shared.domain.anime.Anime
import com.example.myapplication.shared.enumerations.Gender
import com.example.myapplication.shared.services.anime.AnimeService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeList
constructor(private val service: AnimeService) : ViewModel() {

    private val _animes = MutableStateFlow<List<Anime>>(emptyList());
    val animes = _animes.asStateFlow();

    private val _gender = MutableStateFlow<String>("");
    val gender = _gender.asStateFlow();

    init {
        this.loadTopAnimes();
    }

    private fun loadTopAnimes() {
        viewModelScope.launch {
            service.getTopAnimes()
                .onSuccess { topAnimes ->
                    _animes.value =
                        topAnimes.filter { anime -> anime.name?.isNotBlank() == true && anime.episodeNumber != null };
                }
                .onFailure { exception ->
                    Log.e(
                        "AnimeList",
                        "Erro ao carregar top animes: ${exception.message}",
                        exception
                    )
                }
        }
    }

    fun getAnimesByGenre(gender: Gender) {
        viewModelScope.launch {
            _animes.value = listOf()
            service.getAllAnimeByGender(gender)
                .onSuccess { animeByGenre ->
                    _animes.value =
                        animeByGenre.filter { anime -> anime.name?.isNotBlank() == true && anime.episodeNumber != null };
                    updateGenderState(gender);
                }
                .onFailure { exception ->
                    Log.e(
                        "AnimeList",
                        "Erro ao carregar animes por Genero: ${exception.message}",
                        exception
                    )
                }
        }
    }

    private fun updateGenderState(gender: Gender) {
        _gender.value = gender.name;
    }

    fun favorite(anime: Anime) {
        anime.favorite();
    }
}