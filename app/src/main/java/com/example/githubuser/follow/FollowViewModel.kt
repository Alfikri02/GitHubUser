package com.example.githubuser.follow

import androidx.lifecycle.*
import com.example.githubuser.core.data.Resource
import com.example.githubuser.core.domain.model.User
import com.example.githubuser.core.domain.usecase.UserUseCase

class FollowViewModel(userUseCase: UserUseCase) : ViewModel() {
    private var username: MutableLiveData<String> = MutableLiveData()

    fun setFollow(user: String) {
        if (username.value == user) return
        username.value = user
    }

    val userFollowers: LiveData<Resource<List<User>>> = Transformations.switchMap(username) {
        userUseCase.getFollower(it).asLiveData()
    }

    val userFollowing: LiveData<Resource<List<User>>> = Transformations.switchMap(username) {
        userUseCase.getFollowing(it).asLiveData()
    }
}