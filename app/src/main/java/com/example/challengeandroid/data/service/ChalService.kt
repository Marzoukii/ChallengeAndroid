package com.example.challengeandroid.data.service

import android.app.Service
import com.example.challengeandroid.data.ListeDepots.ListeDepotsRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class ChalService : Service() {
    @Inject
    lateinit var repository: ListeDepotsRepository
}
