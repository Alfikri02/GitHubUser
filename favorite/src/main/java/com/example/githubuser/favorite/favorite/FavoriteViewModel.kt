package com.example.githubuser.favorite.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubuser.core.domain.usecase.UserUseCase

class FavoriteViewModel(userUseCase: UserUseCase) : ViewModel() {
    val favoritesUsers = userUseCase.getFavorite().asLiveData()
}