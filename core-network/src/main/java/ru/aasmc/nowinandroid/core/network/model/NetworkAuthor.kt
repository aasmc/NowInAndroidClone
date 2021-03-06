package ru.aasmc.nowinandroid.core.network.model

import kotlinx.serialization.Serializable

/**
 * Network representation of [Author]
 */
@Serializable
data class NetworkAuthor(
    val id: String,
    val name: String,
    val imageUrl: String,
    val twitter: String,
    val mediumPage: String,
    val bio: String,
)
