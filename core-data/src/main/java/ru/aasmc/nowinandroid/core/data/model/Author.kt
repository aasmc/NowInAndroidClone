package ru.aasmc.nowinandroid.core.data.model

import ru.aasmc.nowinandroid.core.database.model.AuthorEntity
import ru.aasmc.nowinandroid.core.network.model.NetworkAuthor

fun NetworkAuthor.asEntity() = AuthorEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    twitter = twitter,
    mediumPage = mediumPage,
    bio = bio,
)
