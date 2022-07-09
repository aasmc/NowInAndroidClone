package ru.aasmc.nowinandroid.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.aasmc.nowinadnroid.core.model.data.NewsResource
import ru.aasmc.nowinandroid.core.data.Syncable

/**
 * Data layer implementation for [NewsResource]
 */
interface NewsRepository : Syncable {
    /**
     * Returns available news resources as a stream.
     */
    fun getNewsResourcesStream(): Flow<List<NewsResource>>

    /**
     * Returns available news resources as a stream filtered by authors or topics.
     */
    fun getNewsResourcesStream(
        filterAuthorIds: Set<String> = emptySet(),
        filterTopicIds: Set<String> = emptySet(),
    ): Flow<List<NewsResource>>
}