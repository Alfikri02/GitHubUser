package com.example.githubuser.core.domain.usecase

import com.example.githubuser.core.data.Resource
import com.example.githubuser.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    fun getSearchUser(query: String?): Flow<Resource<List<User>>>

    fun getFollower(username: String): Flow<Resource<List<User>>>

    fun getFollowing(username: String): Flow<Resource<List<User>>>

    fun getDetailUser(username: String): Flow<Resource<User>>

    fun getFavorite(): Flow<List<User>>

    fun check(id: Int): Flow<User>?

    suspend fun insert(username: String?, id: Int?, avatarUrl: String?, isFavorite: Boolean?)

    suspend fun delete(user: User): Int


}