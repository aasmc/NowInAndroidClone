package ru.aasmc.nowinandroid.core.network.di

import ru.aasmc.nowinandroid.core.network.NiaNetworkDataSource
import ru.aasmc.nowinandroid.core.network.retrofit.RetrofitNiaNetwork
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    fun bindsNiaNetwork(
        niANetwork: RetrofitNiaNetwork
    ): NiaNetworkDataSource

    companion object {
        @Provides
        @Singleton
        fun providesNetworkJson(): Json = Json {
            ignoreUnknownKeys = true
        }
    }
}
