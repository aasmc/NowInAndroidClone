package ru.aasmc.nowinandroid.core.data.testdoubles

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Instant
import ru.aasmc.nowinadnroid.core.model.data.NewsResourceType
import ru.aasmc.nowinandroid.core.database.dao.NewsResourceDao
import ru.aasmc.nowinandroid.core.database.model.*

val filteredInterestsIds = setOf("1")
val nonPresentInterestsIds = setOf("2")
/**
 * Test double for [NewsResourceDao]
 */
class TestNewsResourceDao : NewsResourceDao {

    private var entitiesStateFlow = MutableStateFlow(
        listOf(
            NewsResourceEntity(
                id = "1",
                episodeId = "0",
                title = "news",
                content = "Hilt",
                url = "url",
                headerImageUrl = "headerImageUrl",
                type = NewsResourceType.Video,
                publishDate = Instant.fromEpochMilliseconds(1),
            )
        )
    )

    internal var topicCrossReferences: List<NewsResourceTopicCrossRef> = listOf()

    internal var authorCrossReferences: List<NewsResourceAuthorCrossRef> = listOf()

    override fun getNewsResourcesStream(): Flow<List<PopulatedNewsResource>> =
        entitiesStateFlow.map {
            it.map(NewsResourceEntity::asPopulatedNewsResource)
        }

    override fun getNewsResourcesStream(
        filterAuthorIds: Set<String>,
        filterTopicIds: Set<String>
    ): Flow<List<PopulatedNewsResource>> =
        getNewsResourcesStream()
            .map { resources ->
                resources.filter { resource ->
                    resource.topics.any { it.id in filterTopicIds } ||
                            resource.authors.any { it.id in filterAuthorIds }
                }
            }

    override suspend fun insertOrIgnoreNewsResources(
        entities: List<NewsResourceEntity>
    ): List<Long> {
        entitiesStateFlow.value = entities
        // Assume no conflicts on insert
        return entities.map { it.id.toLong() }
    }

    override suspend fun updateNewsResources(entities: List<NewsResourceEntity>) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun insertOrIgnoreTopicCrossRefEntities(
        newsResourceTopicCrossReferences: List<NewsResourceTopicCrossRef>
    ) {
        topicCrossReferences = newsResourceTopicCrossReferences
    }

    override suspend fun insertOrIgnoreAuthorCrossRefEntities(
        newsResourceAuthorCrossReferences: List<NewsResourceAuthorCrossRef>
    ) {
        authorCrossReferences = newsResourceAuthorCrossReferences
    }

    override suspend fun deleteNewsResources(ids: List<String>) {
        val idSet = ids.toSet()
        entitiesStateFlow.update { entities ->
            entities.filterNot { idSet.contains(it.id) }
        }
    }

}

private fun NewsResourceEntity.asPopulatedNewsResource() = PopulatedNewsResource(
    entity = this,
    episode = EpisodeEntity(
        id = this.episodeId,
        name = "episode 4",
        publishDate = Instant.fromEpochMilliseconds(2),
        alternateAudio = "audio",
        alternateVideo = "video",
    ),
    authors = listOf(
        AuthorEntity(
            id = this.episodeId,
            name = "name",
            imageUrl = "imageUrl",
            twitter = "twitter",
            mediumPage = "mediumPage",
            bio = "bio",
        )
    ),
    topics = listOf(
        TopicEntity(
            id = filteredInterestsIds.random(),
            name = "name",
            shortDescription = "short description",
            longDescription = "long description",
            url = "URL",
            imageUrl = "image URL",
        )
    ),
)
