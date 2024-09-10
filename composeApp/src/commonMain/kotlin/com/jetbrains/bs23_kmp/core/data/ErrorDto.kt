package com.jetbrains.bs23_kmp.core.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

//
//@Serializable
//data class ErrorDto(val message: String?)

@Serializable
data class ErrorDto(
    val messages: List<String> = emptyList(),
    val source: String? = null,
    val exception: String? = null,
    val errorId: String? = null,
    val supportMessage: String? = null,
    val statusCode: Int? = null
)

data class BadRequestErrorDto(
    val type: String,
    val title: String,
    val status: Int,
    val detail: String,
    val instance: String,
    val errors: JsonObject
)
