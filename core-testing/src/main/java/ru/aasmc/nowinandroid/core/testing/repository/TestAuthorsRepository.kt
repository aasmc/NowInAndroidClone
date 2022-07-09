package ru.aasmc.nowinandroid.core.testing.repository

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import ru.aasmc.nowinadnroid.core.model.data.Author
import ru.aasmc.nowinandroid.core.data.Synchronizer
import ru.aasmc.nowinandroid.core.data.repository.AuthorsRepository

class TestAuthorsRepository : AuthorsRepository {
    /**
     * The backing hot flow for the list of author ids for testing.
     */
    private val authorsFlow: MutableSharedFlow<List<Author>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getAuthorsStream(): Flow<List<Author>> = authorsFlow

    override fun getAuthorStream(id: String): Flow<Author> {
        return authorsFlow.map { authors -> authors.find { it.id == id }!! }
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = true

    /**
     * A test-only API to allow controlling the list of authors from tests.
     */
    fun sendAuthors(authors: List<Author>) {
        authorsFlow.tryEmit(authors)
    }
}