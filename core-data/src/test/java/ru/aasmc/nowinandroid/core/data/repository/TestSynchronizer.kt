package ru.aasmc.nowinandroid.core.data.repository

import ru.aasmc.nowinandroid.core.data.Synchronizer
import ru.aasmc.nowinandroid.core.datastore.ChangeListVersions
import ru.aasmc.nowinandroid.core.datastore.NiaPreferencesDataSource

/**
 * Test synchronizer that delegates to [NiaPreferencesDataSource]
 */
class TestSynchronizer(
    private val niaPreferences: NiaPreferencesDataSource
) : Synchronizer {
    override suspend fun getChangeListVersions(): ChangeListVersions =
        niaPreferences.getChangeListVersions()

    override suspend fun updateChangeListVersions(
        update: ChangeListVersions.() -> ChangeListVersions
    ) = niaPreferences.updateChangeListVersion(update)
}