package ru.aasmc.nowinadnroid.core.model.data

/**
 * Class summarizing user interest data
 */
data class UserData(
    val bookmarkedNewsResources: Set<String>,
    val followedTopics: Set<String>,
    val followedAuthors: Set<String>,
)
