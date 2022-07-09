package ru.aasmc.nowinandroid.core.datastore

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class IntToStringIdsMigrationTest {
    @Test
    fun IntToStringIdsMigration_should_migrate_topic_ids() = runTest {
        // Setup existing preferences with topic int ids
        val preMigrationUserPreferences = userPreferences {
            deprecatedIntFollowedTopicIds.addAll(listOf(1, 2, 3))
        }
        // Assert that there are no string topic ids yet
        assertEquals(
            emptyList<String>(),
            preMigrationUserPreferences.followedTopicIdsList
        )

        // Run the migration
        val postMigrationUserPreferences =
            IntToStringIdsMigration.migrate(preMigrationUserPreferences)

        // Assert the deprecated int topic ids have been migrated to the string topic ids
        assertEquals(
            userPreferences {
                followedTopicIds.addAll(listOf("1", "2", "3"))
                hasDoneIntToStringIdMigration = true
            },
            postMigrationUserPreferences
        )

        // Assert that the migration has been marked complete
        Assert.assertTrue(postMigrationUserPreferences.hasDoneIntToStringIdMigration)
    }

    @Test
    fun IntToStringIdsMigration_should_migrate_author_ids() = runTest {
        // Set up existing preferences with author int ids
        val preMigrationUserPreferences = userPreferences {
            deprecatedIntFollowedAuthorIds.addAll(listOf(4, 5, 6))
        }
        // Assert that there are no string author ids yet
        assertEquals(
            emptyList<String>(),
            preMigrationUserPreferences.followedAuthorIdsList
        )

        // Run the migration
        val postMigrationUserPreferences =
            IntToStringIdsMigration.migrate(preMigrationUserPreferences)

        // Assert the deprecated int author ids have been migrated to the string author ids
        assertEquals(
            userPreferences {
                followedAuthorIds.addAll(listOf("4", "5", "6"))
                hasDoneIntToStringIdMigration = true
            },
            postMigrationUserPreferences
        )

        // Assert that the migration has been marked complete
        Assert.assertTrue(postMigrationUserPreferences.hasDoneIntToStringIdMigration)
    }
}