package ru.aasmc.nowinandroid.core.data.model

import ru.aasmc.nowinandroid.core.database.model.*
import ru.aasmc.nowinandroid.core.network.model.NetworkNewsResource
import ru.aasmc.nowinandroid.core.network.model.NetworkNewsResourceExpanded

fun NetworkNewsResource.asEntity() = NewsResourceEntity(
    id = id,
    episodeId = episodeId,
    title = title,
    content = content,
    url = url,
    headerImageUrl = headerImageUrl,
    publishDate = publishDate,
    type = type,
)

fun NetworkNewsResourceExpanded.asEntity() = NewsResourceEntity(
    id = id,
    episodeId = episodeId,
    title = title,
    content = content,
    url = url,
    headerImageUrl = headerImageUrl,
    publishDate = publishDate,
    type = type,
)

/**
 * A shell [EpisodeEntity] to fulfill the foreign key constraint when inserting
 * a [NewsResourceEntity] into the DB
 */
fun NetworkNewsResource.episodeEntityShell() = EpisodeEntity(
    id = episodeId,
    name = "",
    publishDate = publishDate,
    alternateVideo = null,
    alternateAudio = null,
)

/**
 * A shell [AuthorEntity] to fulfill the foreign key constraint when inserting
 * a [NewsResourceEntity] into the DB
 */
fun NetworkNewsResource.authorEntityShells() =
    authors.map { authorId ->
        AuthorEntity(
            id = authorId,
            name = "",
            imageUrl = "",
            twitter = "",
            mediumPage = "",
            bio = "",
        )
    }


/**
 * A shell [TopicEntity] to fulfill the foreign key constraint when inserting
 * a [NewsResourceEntity] into the DB
 */
fun NetworkNewsResource.topicEntityShells() =
    topics.map { topicId ->
        TopicEntity(
            id = topicId,
            name = "",
            url = "",
            imageUrl = "",
            shortDescription = "",
            longDescription = "",
        )
    }

fun NetworkNewsResource.topicCrossReferences(): List<NewsResourceTopicCrossRef> =
    topics.map { topicId ->
        NewsResourceTopicCrossRef(
            newsResourceId = id,
            topicId = topicId
        )
    }

fun NetworkNewsResource.authorCrossReferences(): List<NewsResourceAuthorCrossRef> =
    authors.map { authorId ->
        NewsResourceAuthorCrossRef(
            newsResourceId = id,
            authorId = authorId
        )
    }