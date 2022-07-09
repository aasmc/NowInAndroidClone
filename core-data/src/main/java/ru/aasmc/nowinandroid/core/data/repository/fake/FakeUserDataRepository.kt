package ru.aasmc.nowinandroid.core.data.repository.fake

import kotlinx.coroutines.flow.Flow
import ru.aasmc.nowinadnroid.core.model.data.UserData
import ru.aasmc.nowinandroid.core.data.repository.UserDataRepository
import ru.aasmc.nowinandroid.core.datastore.NiaPreferencesDataSource
import javax.inject.Inject

/**
 * Fake implementation of the [AuthorsRepository] that returns hardcoded authors.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeUserDataRepository @Inject constructor(
    private val niaPreferencesDataSource: NiaPreferencesDataSource,
) : UserDataRepository {

    override val userDataStream: Flow<UserData> =
        niaPreferencesDataSource.userDataStream

    override suspend fun setFollowedTopicIds(followedTopicIds: Set<String>) =
        niaPreferencesDataSource.setFollowedTopicIds(followedTopicIds)

    override suspend fun toggleFollowedTopicId(followedTopicId: String, followed: Boolean) =
        niaPreferencesDataSource.toggleFollowedTopicId(followedTopicId, followed)

    override suspend fun setFollowedAuthorIds(followedAuthorIds: Set<String>) {
        niaPreferencesDataSource.setFollowedAuthorIds(followedAuthorIds)
    }

    override suspend fun toggleFollowedAuthorId(followedAuthorId: String, followed: Boolean) {
        niaPreferencesDataSource.toggleFollowedAuthorId(followedAuthorId, followed)
    }

    override suspend fun updateNewsResourceBookmark(newsResourceId: String, bookmarked: Boolean) {
        niaPreferencesDataSource.toggleNewsResourceBookmark(newsResourceId, bookmarked)
    }
}