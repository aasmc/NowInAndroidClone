package ru.aasmc.nowinandroid.core.network.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import ru.aasmc.nowinandroid.core.network.model.util.InstantSerializer

/**
 * Network representation of [Episode] when fetched from /episodes
 */
@Serializable
data class NetworkEpisode(
    val id: String,
    val name: String,
    @Serializable(InstantSerializer::class)
    val publishDate: Instant,
    val alternateVideo: String?,
    val alternateAudio: String?,
    val newsResources: List<String> = listOf(),
    val authors: List<String> = listOf(),
)


/**
 * Network representation of [Episode] when fetched from /episodes/{id}
 */
@Serializable
data class NetworkEpisodeExpanded(
    val id: String,
    val name: String,
    @Serializable(InstantSerializer::class)
    val publishDate: Instant,
    val alternateVideo: String,
    val alternateAudio: String,
    val newsResources: List<NetworkNewsResource> = listOf(),
    val authors: List<NetworkAuthor> = listOf(),
)