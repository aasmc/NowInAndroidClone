package ru.aasmc.nowinandroid.core.data.repository.fake

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.aasmc.nowinadnroid.core.model.data.NewsResource
import ru.aasmc.nowinandroid.core.data.Synchronizer
import ru.aasmc.nowinandroid.core.data.model.asEntity
import ru.aasmc.nowinandroid.core.data.repository.NewsRepository
import ru.aasmc.nowinandroid.core.database.model.NewsResourceEntity
import ru.aasmc.nowinandroid.core.database.model.asExternalModel
import ru.aasmc.nowinandroid.core.network.Dispatcher
import ru.aasmc.nowinandroid.core.network.NiaDispatchers
import ru.aasmc.nowinandroid.core.network.fake.FakeDataSource
import ru.aasmc.nowinandroid.core.network.model.NetworkNewsResource
import javax.inject.Inject

/**
 * Fake implementation of the [NewsRepository] that retrieves the news resources from a JSON String.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeNewsRepository @Inject constructor(
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json
) : NewsRepository {
    override fun getNewsResourcesStream(): Flow<List<NewsResource>> =
        flow {
            emit(
                networkJson.decodeFromString<List<NetworkNewsResource>>(FakeDataSource.data)
                    .map(NetworkNewsResource::asEntity)
                    .map(NewsResourceEntity::asExternalModel)
            )
        }
            .flowOn(ioDispatcher)

    override fun getNewsResourcesStream(
        filterAuthorIds: Set<String>,
        filterTopicIds: Set<String>,
    ): Flow<List<NewsResource>> =
        flow {
            emit(
                networkJson.decodeFromString<List<NetworkNewsResource>>(FakeDataSource.data)
                    .filter {
                        it.authors.intersect(filterAuthorIds).isNotEmpty() ||
                                it.topics.intersect(filterTopicIds).isNotEmpty()
                    }
                    .map(NetworkNewsResource::asEntity)
                    .map(NewsResourceEntity::asExternalModel)
            )
        }
            .flowOn(ioDispatcher)

    override suspend fun syncWith(synchronizer: Synchronizer) = true

}