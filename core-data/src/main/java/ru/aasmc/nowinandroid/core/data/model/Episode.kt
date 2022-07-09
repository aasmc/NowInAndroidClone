package ru.aasmc.nowinandroid.core.data.model

import ru.aasmc.nowinandroid.core.database.model.EpisodeEntity
import ru.aasmc.nowinandroid.core.network.model.NetworkEpisode
import ru.aasmc.nowinandroid.core.network.model.NetworkEpisodeExpanded

fun NetworkEpisode.asEntity() = EpisodeEntity(
    id = id,
    name = name,
    publishDate = publishDate,
    alternateVideo = alternateVideo,
    alternateAudio = alternateAudio,
)

fun NetworkEpisodeExpanded.asEntity() = EpisodeEntity(
    id = id,
    name = name,
    publishDate = publishDate,
    alternateVideo = alternateVideo,
    alternateAudio = alternateAudio,
)