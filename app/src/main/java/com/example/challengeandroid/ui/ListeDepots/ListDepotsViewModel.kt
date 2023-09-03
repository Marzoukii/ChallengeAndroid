package com.example.challengeandroid.ui.ListeDepots

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengeandroid.data.ListeDepots.ListeDepotsRepository
import com.example.challengeandroid.data.ListeDepots.model.ListReposDataModelItem
import com.example.challengeandroid.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDepotsViewModel @Inject constructor(private val listeDepotsRepository: ListeDepotsRepository) :
    ViewModel() {

    private val _reposLiveData = MutableLiveData<Resource<List<ListReposDataModelItem>>>()
    val reposLiveData: LiveData<Resource<List<ListReposDataModelItem>>> = _reposLiveData

    private val allRepos = mutableListOf<ListReposDataModelItem>()
    private val filteredReposLiveData = MutableLiveData<List<ListReposDataModelItem>>()

    val filteredRepos: LiveData<List<ListReposDataModelItem>> = filteredReposLiveData

    fun searchRepositories(query: String) {
        val filteredRepos = if (query.isBlank()) {
            allRepos
        } else {
            allRepos.filter { repo ->
                repo.name.contains(query, ignoreCase = true)
            }
        }
        filteredReposLiveData.value = filteredRepos
    }

    fun fetchUserRepositories(username: String) {
        viewModelScope.launch {
            val newData = listeDepotsRepository.getUserRepositories(username)
            _reposLiveData.value = newData
            if (newData is Resource.Success) {
                val repos = newData.data ?: emptyList()
                allRepos.clear()
                allRepos.addAll(repos)
            }
        }
    }
}
