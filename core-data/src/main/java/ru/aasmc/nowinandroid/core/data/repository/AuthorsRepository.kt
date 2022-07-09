package ru.aasmc.nowinandroid.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.aasmc.nowinadnroid.core.model.data.Author
import ru.aasmc.nowinandroid.core.data.Syncable

interface AuthorsRepository : Syncable {
    /**
     * Gets the available Authors as a stream
     */
    fun getAuthorsStream(): Flow<List<Author>>

    /**
     * Gets data for a specific author
     */
    fun getAuthorStream(id: String): Flow<Author>
}