package ru.aasmc.nowinandroid.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.aasmc.nowinandroid.core.data.repository.*

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsTopicRepository(
        topicsRepository: OfflineFirstTopicsRepository
    ): TopicsRepository

    @Binds
    fun bindsAuthorsRepository(
        authorsRepository: OfflineFirstAuthorsRepository
    ): AuthorsRepository

    @Binds
    fun bindsNewsResourceRepository(
        newsRepository: OfflineFirstNewsRepository
    ): NewsRepository

    @Binds
    fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository
    ): UserDataRepository
}