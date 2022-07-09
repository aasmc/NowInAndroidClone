package ru.aasmc.nowinandroid.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.aasmc.nowinadnroid.core.model.data.UserData

interface UserDataRepository {

    /**
     * Stream of [UserData]
     */
    val userDataStream: Flow<UserData>

    /**
     * Sets the user's currently followed topics
     */
    suspend fun setFollowedTopicIds(followedTopicIds: Set<String>)

    /**
     * Toggles the user's newly followed/unfollowed topic
     */
    suspend fun toggleFollowedTopicId(followedTopicId: String, followed: Boolean)

    /**
     * Sets the user's currently followed authors
     */
    suspend fun setFollowedAuthorIds(followedAuthorIds: Set<String>)

    /**
     * Toggles the user's newly followed/unfollowed author
     */
    suspend fun toggleFollowedAuthorId(followedAuthorId: String, followed: Boolean)

    /**
     * Updates the bookmarked status for a news resource
     */
    suspend fun updateNewsResourceBookmark(newsResourceId: String, bookmarked: Boolean)
}
