package com.jetbrains.bs23_kmp.core.util
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Any.toJson(): String {
    return try {
        Json.encodeToString(this)
    } catch (e: Exception) {
        ""
    }
}

inline fun <reified T> String.fromJson(): T? {
    return try {
        Json.decodeFromString<T>(this)
    } catch (e: Exception) {
        null
    }
}

/**
Usage:

@Serializable
data class Person(val name: String, val age: Int)

val person = Person("John Doe", 30)

// Serialize the object to a JSON string
val json = person.toJson()

// Deserialize the JSON string to an object
val deserializedPerson = json.fromJson<Person>()
*/