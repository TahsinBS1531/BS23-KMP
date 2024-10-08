package com.jetbrains.bs23_kmp.core.data

import com.jetbrains.bs23_kmp.data.MuseumObject

interface BaseApi {
    suspend fun get(): List<MuseumObject>
}