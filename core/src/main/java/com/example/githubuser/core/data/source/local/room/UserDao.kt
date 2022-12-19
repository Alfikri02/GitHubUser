package com.example.githubuser.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.githubuser.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity): Int

    @Query("SELECT * FROM users WHERE id = :id")
    fun check(id: Int): Flow<UserEntity>?

    @Query("SELECT * from users")
    fun getAllFavorites(): Flow<List<UserEntity>>

}