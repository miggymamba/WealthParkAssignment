package com.example.wealthparkassignment.di.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class LongTypeAdapter : JsonDeserializer<Long> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Long? {
        val primitive = json?.asJsonPrimitive
        return if (primitive?.isString == true) {
            if (primitive.asString == "") {
                null
            } else {
                primitive.asLong
            }
        } else {
            null
        }
    }
}