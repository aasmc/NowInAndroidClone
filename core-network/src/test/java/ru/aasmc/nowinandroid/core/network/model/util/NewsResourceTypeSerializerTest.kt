package ru.aasmc.nowinandroid.core.network.model.util

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.aasmc.nowinadnroid.core.model.data.NewsResourceType

class NewsResourceTypeSerializerTest {
    @Test
    fun test_news_resource_serializer_video() {
        assertEquals(
            NewsResourceType.Video,
            Json.decodeFromString(NewsResourceTypeSerializer, """"Video 📺"""")
        )
    }

    @Test
    fun test_news_resource_serializer_article() {
        assertEquals(
            NewsResourceType.Article,
            Json.decodeFromString(NewsResourceTypeSerializer, """"Article 📚"""")
        )
    }

    @Test
    fun test_news_resource_serializer_api_change() {
        assertEquals(
            NewsResourceType.APIChange,
            Json.decodeFromString(NewsResourceTypeSerializer, """"API change"""")
        )
    }

    @Test
    fun test_news_resource_serializer_codelab() {
        assertEquals(
            NewsResourceType.Codelab,
            Json.decodeFromString(NewsResourceTypeSerializer, """"Codelab"""")
        )
    }

    @Test
    fun test_news_resource_serializer_podcast() {
        assertEquals(
            NewsResourceType.Podcast,
            Json.decodeFromString(NewsResourceTypeSerializer, """"Podcast 🎙"""")
        )
    }

    @Test
    fun test_news_resource_serializer_docs() {
        assertEquals(
            NewsResourceType.Docs,
            Json.decodeFromString(NewsResourceTypeSerializer, """"Docs 📑"""")
        )
    }

    @Test
    fun test_news_resource_serializer_event() {
        assertEquals(
            NewsResourceType.Event,
            Json.decodeFromString(NewsResourceTypeSerializer, """"Event 📆"""")
        )
    }

    @Test
    fun test_news_resource_serializer_dac() {
        assertEquals(
            NewsResourceType.DAC,
            Json.decodeFromString(NewsResourceTypeSerializer, """"DAC"""")
        )
    }

    @Test
    fun test_news_resource_serializer_unknown() {
        assertEquals(
            NewsResourceType.Unknown,
            Json.decodeFromString(NewsResourceTypeSerializer, """"umm"""")
        )
    }

    @Test
    fun test_serialize_and_deserialize() {
        val json = Json.encodeToString(NewsResourceTypeSerializer, NewsResourceType.Video)
        assertEquals(
            NewsResourceType.Video,
            Json.decodeFromString(NewsResourceTypeSerializer, json)
        )
    }
}