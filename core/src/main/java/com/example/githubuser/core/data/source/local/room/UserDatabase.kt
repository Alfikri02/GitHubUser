package com.example.githubuser.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubuser.core.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}