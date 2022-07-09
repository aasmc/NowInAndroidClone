package ru.aasmc.nowinandroid.core.network

import ru.aasmc.nowinandroid.core.network.model.NetworkAuthor
import ru.aasmc.nowinandroid.core.network.model.NetworkChangeList
import ru.aasmc.nowinandroid.core.network.model.NetworkNewsResource
import ru.aasmc.nowinandroid.core.network.model.NetworkTopic

/**
 * Interface representing network calls to the NIA backend
 */
interface NiaNetworkDataSource {
    suspend fun getTopics(ids: List<String>? = null): List<NetworkTopic>

    suspend fun getAuthors(ids: List<String>? = null): List<NetworkAuthor>

    suspend fun getNewsResources(ids: List<String>? = null): List<NetworkNewsResource>

    suspend fun getTopicChangeList(after: Int? = null): List<NetworkChangeList>

    suspend fun getAuthorChangeList(after: Int? = null): List<NetworkChangeList>

    suspend fun getNewsResourceChangeList(after: Int? = null): List<NetworkChangeList>
}