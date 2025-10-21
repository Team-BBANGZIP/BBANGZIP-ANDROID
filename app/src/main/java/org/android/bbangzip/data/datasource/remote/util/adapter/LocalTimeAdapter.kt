package org.android.bbangzip.data.datasource.remote.util.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.time.LocalTime

class LocalTimeAdapter : TypeAdapter<LocalTime>() {
    override fun write(
        out: JsonWriter,
        value: LocalTime?,
    ) {
        out.value(value?.toString())
    }

    override fun read(input: JsonReader): LocalTime? {
        if (input.peek() == JsonToken.NULL) {
            input.nextNull()
            return null
        }
        return LocalTime.parse(input.nextString())
    }
}
