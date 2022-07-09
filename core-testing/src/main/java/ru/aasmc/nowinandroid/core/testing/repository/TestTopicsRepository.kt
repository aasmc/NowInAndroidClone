package ru.aasmc.nowinandroid.core.testing.repository

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import ru.aasmc.nowinadnroid.core.model.data.Topic
import ru.aasmc.nowinandroid.core.data.Synchronizer
import ru.aasmc.nowinandroid.core.data.repository.TopicsRepository

class TestTopicsRepository : TopicsRepository {
    /**
     * The backing hot flow for the list of topics ids for testing.
     */
    private val topicsFlow: MutableSharedFlow<List<Topic>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getTopicsStream(): Flow<List<Topic>> = topicsFlow

    override fun getTopic(id: String): Flow<Topic> {
        return topicsFlow.map { topics -> topics.find { it.id == id }!! }
    }

    /**
     * A test-only API to allow controlling the list of topics from tests.
     */
    fun sendTopics(topics: List<Topic>) {
        topicsFlow.tryEmit(topics)
    }

    override suspend fun syncWith(synchronizer: Synchronizer) = true
}