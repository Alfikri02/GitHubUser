package com.example.githubuser.di

import com.example.githubuser.core.domain.usecase.UserInteractor
import com.example.githubuser.core.domain.usecase.UserUseCase
import com.example.githubuser.detail.DetailViewModel
import com.example.githubuser.follow.FollowViewModel
import com.example.githubuser.home.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { FollowViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}