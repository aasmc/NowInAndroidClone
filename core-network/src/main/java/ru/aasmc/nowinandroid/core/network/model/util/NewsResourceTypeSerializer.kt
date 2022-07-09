package ru.aasmc.nowinandroid.core.network.model.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ru.aasmc.nowinadnroid.core.model.data.NewsResourceType
import ru.aasmc.nowinadnroid.core.model.data.asNewsResourceType

object NewsResourceTypeSerializer : KSerializer<NewsResourceType> {

    override fun deserialize(decoder: Decoder): NewsResourceType =
        decoder.decodeString().asNewsResourceType()

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "type",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: NewsResourceType) =
        encoder.encodeString(value.serializedName)
}