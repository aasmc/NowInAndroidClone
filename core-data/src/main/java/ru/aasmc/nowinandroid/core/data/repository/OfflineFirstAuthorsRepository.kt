package ru.aasmc.nowinandroid.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.aasmc.nowinadnroid.core.model.data.Author
import ru.aasmc.nowinandroid.core.data.Synchronizer
import ru.aasmc.nowinandroid.core.data.changeListSync
import ru.aasmc.nowinandroid.core.data.model.asEntity
import ru.aasmc.nowinandroid.core.database.dao.AuthorDao
import ru.aasmc.nowinandroid.core.database.model.AuthorEntity
import ru.aasmc.nowinandroid.core.database.model.asExternalModel
import ru.aasmc.nowinandroid.core.datastore.ChangeListVersions
import ru.aasmc.nowinandroid.core.network.NiaNetworkDataSource
import ru.aasmc.nowinandroid.core.network.model.NetworkAuthor
import javax.inject.Inject

/**
 * Disk storage backed implementation of the [AuthorsRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstAuthorsRepository @Inject constructor(
    private val authorDao: AuthorDao,
    private val network: NiaNetworkDataSource
) : AuthorsRepository {

    override fun getAuthorsStream(): Flow<List<Author>> =
        authorDao.getAuthorEntitiesStream()
            .map { it.map(AuthorEntity::asExternalModel) }

    override fun getAuthorStream(id: String): Flow<Author> =
        authorDao.getAuthorEntityStream(id).map {
            it.asExternalModel()
        }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::authorVersion,
            changeListFetcher = { currentVersion ->
                network.getAuthorChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(authorVersion = latestVersion)
            },
            modelDeleter = authorDao::deleteAuthors,
            modelUpdater = { changedIds ->
                val networkAuthors = network.getAuthors(ids = changedIds)
                authorDao.upsertAuthors(
                    entities = networkAuthors.map(NetworkAuthor::asEntity)
                )
            }
        )

}
















