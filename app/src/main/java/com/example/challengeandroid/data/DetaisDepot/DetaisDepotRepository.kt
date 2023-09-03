package com.example.challengeandroid.data.DetaisDepot

import com.example.challengeandroid.data.DetaisDepot.model.DetaisDepotDataModel
import com.example.challengeandroid.data.Resource
import com.example.challengeandroid.data.remote.GitService
import javax.inject.Inject

class DetaisDepotRepository @Inject constructor(private val remoteService: GitService) {
    suspend fun getRepositoryDetails(
        username: String,
        repoName: String,
    ): Resource<DetaisDepotDataModel> {
        try {
            val response = remoteService.getRepositoryDetails(username, repoName)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Resource.Success(body)
                } else {
                    return Resource.Error("Response body is null")
                }
            } else {
                val errorMessage = when (response.code()) {
                    404 -> "Not Found"
                    else -> response.message() ?: "Unknown error"
                }
                return Resource.Error(errorMessage)
            }
        } catch (e: Exception) {
            // Handle exceptions here
            return Resource.Error(e.message ?: "Unknown error")
        }
    }
}
