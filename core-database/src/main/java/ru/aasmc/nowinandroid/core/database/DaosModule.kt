package ru.aasmc.nowinandroid.core.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.aasmc.nowinandroid.core.database.dao.AuthorDao
import ru.aasmc.nowinandroid.core.database.dao.EpisodeDao
import ru.aasmc.nowinandroid.core.database.dao.NewsResourceDao
import ru.aasmc.nowinandroid.core.database.dao.TopicDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesAuthorDao(
        database: NiaDatabase,
    ): AuthorDao = database.authorDao()

    @Provides
    fun providesTopicsDao(
        database: NiaDatabase,
    ): TopicDao = database.topicDao()

    @Provides
    fun providesEpisodeDao(
        database: NiaDatabase,
    ): EpisodeDao = database.episodeDao()

    @Provides
    fun providesNewsResourceDao(
        database: NiaDatabase,
    ): NewsResourceDao = database.newsResourceDao()
}