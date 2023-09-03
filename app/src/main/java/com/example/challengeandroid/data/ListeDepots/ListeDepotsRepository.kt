package com.example.challengeandroid.data.ListeDepots

import com.example.challengeandroid.data.ListeDepots.model.ListReposDataModelItem
import com.example.challengeandroid.data.Resource
import com.example.challengeandroid.data.remote.GitService
import javax.inject.Inject

class ListeDepotsRepository @Inject constructor(private val remoteService: GitService) {

    suspend fun getUserRepositories(username: String): Resource<List<ListReposDataModelItem>> {
        try {
            val response = remoteService.getUserRepositories(username)

            if (response.isSuccessful && response.body() != null) {
                return Resource.Success(response.body()!!)
            } else {
                // Handle the error case here
                val errorMessage = response.message() ?: "Unknown error"
                return Resource.Error(errorMessage)
            }
        } catch (e: Exception) {
            // Handle exceptions here
            return Resource.Error(e.message)
        }
    }
}
