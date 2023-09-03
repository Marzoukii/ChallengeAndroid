package com.example.challengeandroid.data

import com.example.challengeandroid.data.ListeDepots.ListeDepotsRepository
import com.example.challengeandroid.data.remote.GitService
import com.example.challengeandroid.data.remote.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGitService(): GitService =
        NetworkClient.initialize().create(GitService::class.java)

    @Provides
    @Singleton
    fun provideListeDepotsRepository(
        localService: GitService,
    ): ListeDepotsRepository = ListeDepotsRepository(localService)
}
