package com.jetbrains.bs23_kmp.data

import com.bs23.msfa.core.extensions.mapSuccess
import io.ktor.client.HttpClient
import io.ktor.client.request.get

interface MuseumApi {
    suspend fun getData(): List<MuseumObject>
}

class KtorMuseumApi(private val client: HttpClient) : MuseumApi {
    companion object {
        private const val API_URL =
            "https://raw.githubusercontent.com/Kotlin/KMP-App-Template/main/list.json"
    }

    override suspend fun getData(): List<MuseumObject> {
        return client.get(API_URL).mapSuccess()
    }
}
