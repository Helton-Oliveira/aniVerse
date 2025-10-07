package com.example.myapplication.shared

import com.example.myapplication.shared.services.anime.AnimeService
import com.example.myapplication.ui.viewModel.anime.AnimeList
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val entityModules = module {
    single { AnimeService(get()) }
    viewModelOf(::AnimeList)
}