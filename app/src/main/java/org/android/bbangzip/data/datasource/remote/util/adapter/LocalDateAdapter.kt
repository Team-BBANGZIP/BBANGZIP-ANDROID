package org.android.bbangzip.data.datasource.remote.util.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.time.LocalDate

class LocalDateAdapter : TypeAdapter<LocalDate>() {
    override fun write(
        out: JsonWriter,
        value: LocalDate?,
    ) {
        out.value(value?.toString())
    }

    override fun read(input: JsonReader): LocalDate? {
        if (input.peek() == JsonToken.NULL) {
            input.nextNull()
            return null
        }
        return LocalDate.parse(input.nextString())
    }
}
