package ru.aasmc.nowinandroid.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import ru.aasmc.nowinadnroid.core.model.data.NewsResource
import ru.aasmc.nowinadnroid.core.model.data.NewsResourceType

/**
 * Defines an NiA news resource.
 * It is the child in a 1 to many relationship with [EpisodeEntity]
 */
@Entity(
    tableName = "news_resources",
    foreignKeys = [
        ForeignKey(
            entity = EpisodeEntity::class,
            parentColumns = ["id"],
            childColumns = ["episode_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NewsResourceEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "episode_id")
    val episodeId: String,
    val title: String,
    val content: String,
    val url: String,
    @ColumnInfo(name = "header_image_url")
    val headerImageUrl: String?,
    @ColumnInfo(name = "publish_date")
    val publishDate: Instant,
    val type: NewsResourceType,
)

fun NewsResourceEntity.asExternalModel() = NewsResource(
    id = id,
    episodeId = episodeId,
    title = title,
    content = content,
    url = url,
    headerImageUrl = headerImageUrl,
    publishDate = publishDate,
    type = type,
    authors = listOf(),
    topics = listOf()
)
