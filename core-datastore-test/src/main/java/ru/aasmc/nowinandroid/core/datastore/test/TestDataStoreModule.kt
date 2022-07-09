package ru.aasmc.nowinandroid.core.datastore.test

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.junit.rules.TemporaryFolder
import ru.aasmc.nowinandroid.core.datastore.UserPreferences
import ru.aasmc.nowinandroid.core.datastore.UserPreferencesSerializer
import ru.aasmc.nowinandroid.core.datastore.di.DataStoreModule
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataStoreModule::class]
)
class TestDataStoreModule {
    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        userPreferencesSerializer: UserPreferencesSerializer,
        tmpFolder: TemporaryFolder
    ): DataStore<UserPreferences> =
        tmpFolder.testUserPreferencesDataStore(userPreferencesSerializer)

}

fun TemporaryFolder.testUserPreferencesDataStore(
    userPreferencesSerializer: UserPreferencesSerializer = UserPreferencesSerializer()
) = DataStoreFactory.create(
    serializer = userPreferencesSerializer
) {
    newFile("user_preferences_test.pb")
}