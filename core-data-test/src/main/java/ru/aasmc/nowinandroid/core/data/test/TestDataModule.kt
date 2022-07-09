package ru.aasmc.nowinandroid.core.data.test

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import ru.aasmc.nowinandroid.core.data.di.DataModule
import ru.aasmc.nowinandroid.core.data.repository.AuthorsRepository
import ru.aasmc.nowinandroid.core.data.repository.NewsRepository
import ru.aasmc.nowinandroid.core.data.repository.TopicsRepository
import ru.aasmc.nowinandroid.core.data.repository.UserDataRepository
import ru.aasmc.nowinandroid.core.data.repository.fake.FakeAuthorsRepository
import ru.aasmc.nowinandroid.core.data.repository.fake.FakeNewsRepository
import ru.aasmc.nowinandroid.core.data.repository.fake.FakeTopicsRepository
import ru.aasmc.nowinandroid.core.data.repository.fake.FakeUserDataRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface TestDataModule {
    @Binds
    fun bindsTopicRepository(
        fakeTopicsRepository: FakeTopicsRepository
    ): TopicsRepository

    @Binds
    fun bindsAuthorRepository(
        fakeAuthorsRepository: FakeAuthorsRepository
    ): AuthorsRepository

    @Binds
    fun bindsNewsResourceRepository(
        fakeNewsRepository: FakeNewsRepository
    ): NewsRepository

    @Binds
    fun bindsUserDataRepository(
        userDataRepository: FakeUserDataRepository
    ): UserDataRepository
}
