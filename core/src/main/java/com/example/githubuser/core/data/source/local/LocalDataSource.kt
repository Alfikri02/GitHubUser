package com.example.githubuser.core.data.source.local

import com.example.githubuser.core.data.source.local.entity.UserEntity
import com.example.githubuser.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val userDao: UserDao) {
    fun getFavorites(): Flow<List<UserEntity>> = userDao.getAllFavorites()

    fun check(id: Int): Flow<UserEntity>? = userDao.check(id)

    suspend fun delete(user: UserEntity) = userDao.delete(user)

    suspend fun insert(user: UserEntity) = userDao.insert(user)
}