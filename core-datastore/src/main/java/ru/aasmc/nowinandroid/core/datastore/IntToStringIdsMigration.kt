package ru.aasmc.nowinandroid.core.datastore

import androidx.datastore.core.DataMigration

/**
 * Migrates saved ids from [Int] to [String] types
 */
object IntToStringIdsMigration : DataMigration<UserPreferences> {
    override suspend fun cleanUp() = Unit

    override suspend fun migrate(currentData: UserPreferences): UserPreferences =
        currentData.copy {
            // migrate topic ids
            followedTopicIds.clear()
            followedTopicIds.addAll(
                currentData.deprecatedIntFollowedTopicIdsList.map(Int::toString)
            )
            deprecatedIntFollowedTopicIds.clear()
            // migrate author ids
            followedAuthorIds.clear()
            followedAuthorIds.addAll(
                currentData.deprecatedIntFollowedAuthorIdsList.map(Int::toString)
            )
            deprecatedIntFollowedAuthorIds.clear()

            // Mark migration as complete
            hasDoneIntToStringIdMigration = true
        }

    override suspend fun shouldMigrate(currentData: UserPreferences): Boolean =
        !currentData.hasDoneIntToStringIdMigration
}