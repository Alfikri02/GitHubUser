package com.example.githubuser.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuser.core.domain.model.User
import com.example.githubuser.core.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val userUseCase: UserUseCase) : ViewModel() {


    fun getDetailUser(username: String) = userUseCase.getDetailUser(username).asLiveData()

    fun checkUser(id: Int) = userUseCase.check(id)?.asLiveData()

    fun addFavorite(username: String?, id: Int?, avatarUrl: String?, isFavorite: Boolean?) =
        viewModelScope.launch {
            userUseCase.insert(username, id, avatarUrl, isFavorite)
        }

    fun removeFavorite(user: User) = viewModelScope.launch {
        userUseCase.delete(user)
    }
}