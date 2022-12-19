package com.example.githubuser.home

import androidx.lifecycle.*
import com.example.githubuser.core.data.Resource
import com.example.githubuser.core.domain.model.User
import com.example.githubuser.core.domain.usecase.UserUseCase

class SearchViewModel(userUseCase: UserUseCase) : ViewModel() {

    private var username: MutableLiveData<String> = MutableLiveData()

    fun setSearch(query: String) {
        if (username.value == query) {
            return
        }
        username.value = query
    }

    val users: LiveData<Resource<List<User>>> = Transformations
        .switchMap(username) {
            userUseCase.getSearchUser(it).asLiveData()
        }
}