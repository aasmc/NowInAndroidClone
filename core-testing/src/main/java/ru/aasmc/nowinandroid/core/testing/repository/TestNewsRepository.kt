package ru.aasmc.nowinandroid.core.testing.repository

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import ru.aasmc.nowinadnroid.core.model.data.Author
import ru.aasmc.nowinadnroid.core.model.data.NewsResource
import ru.aasmc.nowinadnroid.core.model.data.Topic
import ru.aasmc.nowinandroid.core.data.Synchronizer
import ru.aasmc.nowinandroid.core.data.repository.NewsRepository

class TestNewsRepository : NewsRepository {
    /**
     * The backing hot flow for the list of topics ids for testing.
     */
    private val newsResourcesFlow: MutableSharedFlow<List<NewsResource>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getNewsResourcesStream(): Flow<List<NewsResource>> = newsResourcesFlow

    override fun getNewsResourcesStream(
        filterAuthorIds: Set<String>,
        filterTopicIds: Set<String>
    ): Flow<List<NewsResource>> = getNewsResourcesStream()
        .map { newsResources ->
            newsResources
                .filter {
                    it.authors.map(Author::id).intersect(filterAuthorIds).isNotEmpty() ||
                            it.topics.map(Topic::id).intersect(filterTopicIds).isNotEmpty()
                }
        }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = true

    /**
     * A test-only API to allow controlling the list of news resources from tests.
     */
    fun sendNewsResources(newsResources: List<NewsResource>) {
        newsResourcesFlow.tryEmit(newsResources)
    }
}