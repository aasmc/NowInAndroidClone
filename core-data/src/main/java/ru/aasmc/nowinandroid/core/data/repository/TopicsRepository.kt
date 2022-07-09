package ru.aasmc.nowinandroid.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.aasmc.nowinadnroid.core.model.data.Topic
import ru.aasmc.nowinandroid.core.data.Syncable

interface TopicsRepository : Syncable {
    /**
     * Gets the available topics as a stream
     */
    fun getTopicsStream(): Flow<List<Topic>>

    /**
     * Gets data for a specific topic
     */
    fun getTopic(id: String): Flow<Topic>
}