package ru.aasmc.nowinandroid.core.data.model

import ru.aasmc.nowinandroid.core.database.model.TopicEntity
import ru.aasmc.nowinandroid.core.network.model.NetworkTopic

fun NetworkTopic.asEntity() = TopicEntity(
    id = id,
    name = name,
    shortDescription = shortDescription,
    longDescription = longDescription,
    url = url,
    imageUrl = imageUrl
)