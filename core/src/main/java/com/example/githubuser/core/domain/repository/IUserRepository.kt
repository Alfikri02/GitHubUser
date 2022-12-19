package com.example.githubuser.core.domain.repository

import com.example.githubuser.core.data.Resource
import com.example.githubuser.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getSearchUsers(query: String?): Flow<Resource<List<User>>>

    fun getListFollower(username: String): Flow<Resource<List<User>>>

    fun getListFollowing(username: String): Flow<Resource<List<User>>>

    fun getDetailUser(username: String): Flow<Resource<User>>

    fun getAllFavorites(): Flow<List<User>>

    fun check(id: Int): Flow<User>?

    suspend fun insert(username: String?, id: Int?, avatarUrl: String?, isFavorite: Boolean?)

    suspend fun delete(user: User): Int
}