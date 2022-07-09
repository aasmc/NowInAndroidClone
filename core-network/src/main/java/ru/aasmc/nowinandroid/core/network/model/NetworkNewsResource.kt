package ru.aasmc.nowinandroid.core.network.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import ru.aasmc.nowinadnroid.core.model.data.NewsResourceType
import ru.aasmc.nowinandroid.core.network.model.util.InstantSerializer
import ru.aasmc.nowinandroid.core.network.model.util.NewsResourceTypeSerializer

/**
 * Network representation of [NewsResource] when fetched from /newsresources
 */
@Serializable
data class NetworkNewsResource(
    val id: String,
    val episodeId: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String,
    @Serializable(InstantSerializer::class)
    val publishDate: Instant,
    @Serializable(NewsResourceTypeSerializer::class)
    val type: NewsResourceType,
    val authors: List<String> = listOf(),
    val topics: List<String> = listOf(),
)

/**
 * Network representation of [NewsResource] when fetched from /newsresources/{id}
 */
@Serializable
data class NetworkNewsResourceExpanded(
    val id: String,
    val episodeId: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String,
    @Serializable(InstantSerializer::class)
    val publishDate: Instant,
    @Serializable(NewsResourceTypeSerializer::class)
    val type: NewsResourceType,
    val authors: List<NetworkAuthor> = listOf(),
    val topics: List<NetworkTopic> = listOf(),
)
