package com.kouseina.githubuser.data.retrofit

import com.kouseina.githubuser.data.response.DetailUserResponse
import com.kouseina.githubuser.data.response.ItemsItem
import com.kouseina.githubuser.data.response.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Query("q") q: String,
    ) : Call<SearchUserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String,
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
    ): Call<List<ItemsItem>>
}