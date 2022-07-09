package ru.aasmc.nowinandroid.core.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import ru.aasmc.nowinandroid.core.datastore.NiaPreferencesDataSource
import ru.aasmc.nowinandroid.core.datastore.test.testUserPreferencesDataStore

class OfflineFirstUserDataRepositoryTest {
    private lateinit var subject: OfflineFirstUserDataRepository

    private lateinit var niaPreferencesDataSource: NiaPreferencesDataSource

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        niaPreferencesDataSource = NiaPreferencesDataSource(
            tmpFolder.testUserPreferencesDataStore()
        )

        subject = OfflineFirstUserDataRepository(
            niaPreferencesDataSource = niaPreferencesDataSource
        )
    }

    @Test
    fun offlineFirstUserDataRepository_toggle_followed_topics_logic_delegates_to_nia_preferences() =
        runTest {
            subject.toggleFollowedTopicId(followedTopicId = "0", followed = true)

            Assert.assertEquals(
                setOf("0"),
                subject.userDataStream
                    .map { it.followedTopics }
                    .first()
            )

            subject.toggleFollowedTopicId(followedTopicId = "1", followed = true)

            Assert.assertEquals(
                setOf("0", "1"),
                subject.userDataStream
                    .map { it.followedTopics }
                    .first()
            )

            assertEquals(
                niaPreferencesDataSource.userDataStream
                    .map { it.followedTopics }
                    .first(),
                subject.userDataStream
                    .map { it.followedTopics }
                    .first()
            )
        }

    @Test
    fun offlineFirstUserDataRepository_set_followed_topics_logic_delegates_to_nia_preferences() =
        runTest {
            subject.setFollowedTopicIds(followedTopicIds = setOf("1", "2"))

            Assert.assertEquals(
                setOf("1", "2"),
                subject.userDataStream
                    .map { it.followedTopics }
                    .first()
            )

            assertEquals(
                niaPreferencesDataSource.userDataStream
                    .map { it.followedTopics }
                    .first(),
                subject.userDataStream
                    .map { it.followedTopics }
                    .first()
            )
        }

    @Test
    fun offlineFirstUserDataRepository_bookmark_news_resource_logic_delegates_to_nia_preferences() =
        runTest {
            subject.updateNewsResourceBookmark(newsResourceId = "0", bookmarked = true)

            Assert.assertEquals(
                setOf("0"),
                subject.userDataStream
                    .map { it.bookmarkedNewsResources }
                    .first()
            )

            subject.updateNewsResourceBookmark(newsResourceId = "1", bookmarked = true)

            Assert.assertEquals(
                setOf("0", "1"),
                subject.userDataStream
                    .map { it.bookmarkedNewsResources }
                    .first()
            )

            assertEquals(
                niaPreferencesDataSource.userDataStream
                    .map { it.bookmarkedNewsResources }
                    .first(),
                subject.userDataStream
                    .map { it.bookmarkedNewsResources }
                    .first()
            )
        }
}