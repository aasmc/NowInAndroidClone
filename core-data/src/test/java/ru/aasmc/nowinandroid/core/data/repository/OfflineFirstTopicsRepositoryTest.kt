package ru.aasmc.nowinandroid.core.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import ru.aasmc.nowinadnroid.core.model.data.Topic
import ru.aasmc.nowinandroid.core.data.Synchronizer
import ru.aasmc.nowinandroid.core.data.model.asEntity
import ru.aasmc.nowinandroid.core.data.testdoubles.CollectionType
import ru.aasmc.nowinandroid.core.data.testdoubles.TestNiaNetworkDataSource
import ru.aasmc.nowinandroid.core.data.testdoubles.TestTopicDao
import ru.aasmc.nowinandroid.core.database.dao.TopicDao
import ru.aasmc.nowinandroid.core.database.model.TopicEntity
import ru.aasmc.nowinandroid.core.database.model.asExternalModel
import ru.aasmc.nowinandroid.core.datastore.NiaPreferencesDataSource
import ru.aasmc.nowinandroid.core.datastore.test.testUserPreferencesDataStore
import ru.aasmc.nowinandroid.core.network.model.NetworkTopic

class OfflineFirstTopicsRepositoryTest {
    private lateinit var subject: OfflineFirstTopicsRepository

    private lateinit var topicDao: TopicDao

    private lateinit var network: TestNiaNetworkDataSource

    private lateinit var niaPreferences: NiaPreferencesDataSource

    private lateinit var synchronizer: Synchronizer

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        topicDao = TestTopicDao()
        network = TestNiaNetworkDataSource()
        niaPreferences = NiaPreferencesDataSource(
            tmpFolder.testUserPreferencesDataStore()
        )
        synchronizer = TestSynchronizer(niaPreferences)

        subject = OfflineFirstTopicsRepository(
            topicDao = topicDao,
            network = network
        )
    }

    @Test
    fun offlineFirstTopicsRepository_topics_stream_is_backed_by_topics_dao() =
        runTest {
            Assert.assertEquals(
                topicDao.getTopicEntitiesStream()
                    .first()
                    .map(TopicEntity::asExternalModel),
                subject.getTopicsStream()
                    .first()
            )
        }

    @Test
    fun offlineFirstTopicsRepository_sync_pulls_from_network() =
        runTest {
            subject.syncWith(synchronizer)

            val networkTopics = network.getTopics()
                .map(NetworkTopic::asEntity)

            val dbTopics = topicDao.getTopicEntitiesStream()
                .first()

            Assert.assertEquals(
                networkTopics.map(TopicEntity::id),
                dbTopics.map(TopicEntity::id)
            )
            // After sync version should be updated
            Assert.assertEquals(
                network.latestChangeListVersion(CollectionType.Topics),
                synchronizer.getChangeListVersions().topicVersion
            )
        }

    @Test
    fun offlineFirstTopicsRepository_incremental_sync_pulls_from_network() =
        runTest {
            // set topics version to 10
            synchronizer.updateChangeListVersions {
                copy(topicVersion = 10)
            }
            subject.syncWith(synchronizer)

            val networkTopics = network.getTopics()
                .map(NetworkTopic::asEntity)
                // Drop 10 to simulate the first 10 items being changed
                .drop(10)

            val dbTopics = topicDao.getTopicEntitiesStream()
                .first()

            Assert.assertEquals(
                networkTopics.map(TopicEntity::id),
                dbTopics.map(TopicEntity::id)
            )

            // After sync version should be updated
            Assert.assertEquals(
                network.latestChangeListVersion(CollectionType.Topics),
                synchronizer.getChangeListVersions().topicVersion
            )
        }

    @Test
    fun offlineFirstTopicsRepository_sync_deletes_items_marked_deleted_on_network() =
        runTest {
            val networkTopics = network.getTopics()
                .map(NetworkTopic::asEntity)
                .map(TopicEntity::asExternalModel)

            // Delete half of the items on the network
            val deletedItems = networkTopics
                .map(Topic::id)
                .partition { it.chars().sum() % 2 == 0 }
                .first
                .toSet()

            deletedItems.forEach {
                network.editCollection(
                    collectionType = CollectionType.Topics,
                    id = it,
                    isDelete = true
                )
            }

            subject.syncWith(synchronizer)

            val dbTopics = topicDao.getTopicEntitiesStream()
                .first()
                .map(TopicEntity::asExternalModel)

            // Assert that items marked deleted on the network have been deleted locally
            Assert.assertEquals(
                networkTopics.map(Topic::id) - deletedItems,
                dbTopics.map(Topic::id)
            )

            // After sync version should be updated
            Assert.assertEquals(
                network.latestChangeListVersion(CollectionType.Topics),
                synchronizer.getChangeListVersions().topicVersion
            )
        }
}