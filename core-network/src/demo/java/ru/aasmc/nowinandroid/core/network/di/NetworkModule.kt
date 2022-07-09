package ru.aasmc.nowinandroid.core.network.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import ru.aasmc.nowinandroid.core.network.NiaNetworkDataSource
import ru.aasmc.nowinandroid.core.network.fake.FakeNiaNetworkDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    @Binds
    fun bindsNiaNetwork(
        niaNetwork: FakeNiaNetworkDataSource
    ): NiaNetworkDataSource

    companion object {
        @Provides
        @Singleton
        fun providesNetworkJson(): Json = Json {
            ignoreUnknownKeys = true
        }
    }
}