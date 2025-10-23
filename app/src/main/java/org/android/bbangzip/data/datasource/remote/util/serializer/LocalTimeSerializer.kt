package org.android.bbangzip.data.datasource.remote.util.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object LocalTimeSerializer : KSerializer<LocalTime> {
    private val formatter = DateTimeFormatter.ofPattern("HH:mm")

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalTime", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: LocalTime,
    ) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalTime {
        return LocalTime.parse(decoder.decodeString(), formatter)
    }
}
