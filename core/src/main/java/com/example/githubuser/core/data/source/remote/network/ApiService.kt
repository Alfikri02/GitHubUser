package com.example.githubuser.core.data.source.remote.network

import com.example.githubuser.core.data.source.remote.response.ResponseSearch
import com.example.githubuser.core.data.source.remote.response.ResponseUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") q: String?
    ): ResponseSearch

    @GET("users/{username}")
    suspend fun detailUser(
        @Path("username") username: String?
    ): ResponseUser

    @GET("users/{username}/followers")
    suspend fun userFollower(
        @Path("username") username: String?
    ): List<ResponseUser>

    @GET("users/{username}/following")
    suspend fun userFollowing(
        @Path("username") username: String?
    ): List<ResponseUser>
}