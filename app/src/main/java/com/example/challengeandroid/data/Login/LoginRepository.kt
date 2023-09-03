package com.example.challengeandroid.data.Login

import com.example.challengeandroid.data.Login.model.UserDataModel
import com.example.challengeandroid.data.Resource
import com.example.challengeandroid.data.remote.GitService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val remoteService: GitService) {
    suspend fun getUser(
        username: String,
    ): Resource<UserDataModel> {
        try {
            val response = remoteService.getUser(username)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Resource.Success(body)
                } else {
                    return Resource.Error("Response body is null")
                }
            } else {
                val errorMessage = when (response.code()) {
                    404 -> "User not found"
                    401 -> "Unauthorized"
                    else -> response.message() ?: "Unknown error"
                }
                return Resource.Error(errorMessage)
            }
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error")
        }
    }
}
