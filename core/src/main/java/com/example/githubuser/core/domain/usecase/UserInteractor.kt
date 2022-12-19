package com.example.githubuser.core.domain.usecase

import com.example.githubuser.core.data.Resource
import com.example.githubuser.core.domain.model.User
import com.example.githubuser.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {

    override fun getDetailUser(username: String): Flow<Resource<User>> {
        return userRepository.getDetailUser(username)
    }

    override fun getSearchUser(query: String?): Flow<Resource<List<User>>> {
        return userRepository.getSearchUsers(query)
    }

    override fun getFollower(username: String): Flow<Resource<List<User>>> {
        return userRepository.getListFollower(username)
    }

    override fun getFollowing(username: String): Flow<Resource<List<User>>> {
        return userRepository.getListFollowing(username)
    }

    override fun getFavorite(): Flow<List<User>> {
        return userRepository.getAllFavorites()
    }

    override suspend fun insert(
        username: String?, id: Int?, avatarUrl: String?, isFavorite: Boolean?
    ) {
        return userRepository.insert(username, id, avatarUrl, isFavorite)
    }

    override suspend fun delete(user: User): Int {
        return userRepository.delete(user)
    }

    override fun check(id: Int): Flow<User>? {
        return userRepository.check(id)
    }
}