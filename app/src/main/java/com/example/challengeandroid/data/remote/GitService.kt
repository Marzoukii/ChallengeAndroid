package com.example.challengeandroid.data.remote

import com.example.challengeandroid.data.DetaisDepot.model.DetaisDepotDataModel
import com.example.challengeandroid.data.ListeDepots.model.ListReposDataModelItem
import com.example.challengeandroid.data.Login.model.UserDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitService {

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<UserDataModel>

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(@Path("username") username: String): Response<List<ListReposDataModelItem>>

    @GET("repos/{username}/{repoName}")
    suspend fun getRepositoryDetails(
        @Path("username") username: String,
        @Path("repoName") repoName: String,
    ): Response<DetaisDepotDataModel>
}
