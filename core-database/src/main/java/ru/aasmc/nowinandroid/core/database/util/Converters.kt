package ru.aasmc.nowinandroid.core.database.util

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import ru.aasmc.nowinadnroid.core.model.data.NewsResourceType
import ru.aasmc.nowinadnroid.core.model.data.asNewsResourceType

class InstantConverter {
    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? =
        instant?.toEpochMilliseconds()
}

class NewsResourceTypeConverter {
    @TypeConverter
    fun newsResourceTypeToString(value: NewsResourceType?): String? =
        value?.let(NewsResourceType::serializedName)

    @TypeConverter
    fun stringToNewsResourceType(serializedName: String?): NewsResourceType =
        serializedName.asNewsResourceType()
}