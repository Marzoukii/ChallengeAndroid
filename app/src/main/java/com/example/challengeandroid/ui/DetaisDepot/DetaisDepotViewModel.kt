package com.example.challengeandroid.ui.DetaisDepot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengeandroid.data.DetaisDepot.DetaisDepotRepository
import com.example.challengeandroid.data.DetaisDepot.model.DetaisDepotDataModel
import com.example.challengeandroid.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetaisDepotViewModel @Inject constructor(private val detaisDepotRepository: DetaisDepotRepository) :
    ViewModel() {

    private val _reposDetaisData = MutableLiveData<Resource<DetaisDepotDataModel>>()
    val reposDetaisData: LiveData<Resource<DetaisDepotDataModel>>
        get() = _reposDetaisData

    fun fetchRepositoryDetails(username: String, repoName: String) {
        viewModelScope.launch {
            val newData = detaisDepotRepository.getRepositoryDetails(username, repoName)
            _reposDetaisData.value = newData
        }
    }
}
