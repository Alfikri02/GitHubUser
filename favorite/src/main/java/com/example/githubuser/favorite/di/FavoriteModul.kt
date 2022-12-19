package com.example.githubuser.favorite.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { com.example.githubuser.favorite.favorite.FavoriteViewModel(get()) }
}