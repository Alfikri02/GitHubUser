package com.example.githubuser.core.utils

import com.example.githubuser.core.data.source.local.entity.UserEntity
import com.example.githubuser.core.data.source.remote.response.ResponseUser
import com.example.githubuser.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapResponsesToDomain(input: List<ResponseUser>): Flow<List<User>> {
        val dataArray = ArrayList<User>()
        input.map {
            val user = User(
                it.id,
                it.username,
                it.company,
                it.publicRepos,
                it.followers,
                it.avatarUrl,
                it.following,
                it.name,
                it.location,
                false
            )
            dataArray.add(user)
        }
        return flowOf(dataArray)
    }

    fun mapResponseToDomain(input: ResponseUser): Flow<User> {
        return flowOf(
            User(
                input.id,
                input.username,
                input.company,
                input.publicRepos,
                input.followers,
                input.avatarUrl,
                input.following,
                input.name,
                input.location,
                false
            )
        )
    }

    fun mapEntitiesToDomain(input: List<UserEntity>): List<User> =
        input.map { userEntity ->
            User(
                id = userEntity.id,
                username = userEntity.username,
                avatarUrl = userEntity.avatarUrl,
                isFavorite = userEntity.isFavorite
            )
        }

    fun mapEntityToDomain(input: UserEntity?): User {
        return User(
            id = input?.id,
            username = input?.username,
            avatarUrl = input?.avatarUrl,
            isFavorite = input?.isFavorite
        )
    }


    fun mapDomainToEntity(username: String?, id: Int?, avatarUrl: String?, isFavorite: Boolean?) =
        UserEntity(
            id = id,
            username = username,
            avatarUrl = avatarUrl,
            isFavorite = isFavorite
        )
}