package com.example.challengeandroid.ui.Login // ktlint-disable package-name

import androidx.lifecycle.ViewModel
import com.example.challengeandroid.data.Login.LoginRepository
import com.example.challengeandroid.data.Login.model.UserDataModel
import com.example.challengeandroid.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    suspend fun fetchUserData(username: String): Resource<UserDataModel> {
        return loginRepository.getUser(username)
    }
}
