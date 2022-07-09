package ru.aasmc.nowinandroid.core.testing.repository

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import ru.aasmc.nowinadnroid.core.model.data.UserData
import ru.aasmc.nowinandroid.core.data.repository.UserDataRepository

private val emptyUserData = UserData(
    bookmarkedNewsResources = emptySet(),
    followedTopics = emptySet(),
    followedAuthors = emptySet()
)

class TestUserDataRepository : UserDataRepository {
    /**
     * The backing hot flow for the list of followed topic ids for testing.
     */
    private val _userData =
        MutableSharedFlow<UserData>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val currentUserData: UserData
        get() = _userData.replayCache
            .firstOrNull() ?: emptyUserData

    override val userDataStream: Flow<UserData> = _userData.filterNotNull()

    override suspend fun setFollowedTopicIds(followedTopicIds: Set<String>) {
        _userData.tryEmit(currentUserData.copy(followedTopics = followedTopicIds))
    }

    override suspend fun toggleFollowedTopicId(followedTopicId: String, followed: Boolean) {
        currentUserData.let { current ->
            val followedTopics = if (followed) current.followedTopics + followedTopicId
            else current.followedTopics - followedTopicId

            _userData.tryEmit(current.copy(followedTopics = followedTopics))
        }
    }

    override suspend fun setFollowedAuthorIds(followedAuthorIds: Set<String>) {
        _userData.tryEmit(currentUserData.copy(followedAuthors = followedAuthorIds))
    }

    override suspend fun toggleFollowedAuthorId(followedAuthorId: String, followed: Boolean) {
        currentUserData.let { current ->
            val followedAuthors = if (followed) current.followedAuthors + followedAuthorId
            else current.followedAuthors - followedAuthorId

            _userData.tryEmit(current.copy(followedAuthors = followedAuthors))
        }
    }

    override suspend fun updateNewsResourceBookmark(newsResourceId: String, bookmarked: Boolean) {
        currentUserData.let { current ->
            val bookmarkedNews = if (bookmarked) current.bookmarkedNewsResources + newsResourceId
            else current.bookmarkedNewsResources - newsResourceId

            _userData.tryEmit(current.copy(bookmarkedNewsResources = bookmarkedNews))
        }
    }

    /**
     * A test-only API to allow querying the current followed topics.
     */
    fun getCurrentFollowedTopics(): Set<String>? =
        _userData.replayCache.firstOrNull()?.followedTopics

    /**
     * A test-only API to allow querying the current followed authors.
     */
    fun getCurrentFollowedAuthors(): Set<String>? =
        _userData.replayCache.firstOrNull()?.followedAuthors
}