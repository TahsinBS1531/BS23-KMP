package com.jetbrains.bs23_kmp.di

import com.jetbrains.bs23_kmp.data.InMemoryMuseumStorage
import com.jetbrains.bs23_kmp.data.KtorMuseumApi
import com.jetbrains.bs23_kmp.data.MuseumApi
import com.jetbrains.bs23_kmp.data.MuseumRepository
import com.jetbrains.bs23_kmp.data.MuseumStorage
import com.jetbrains.bs23_kmp.screens.detail.DetailViewModel
import com.jetbrains.bs23_kmp.screens.list.ListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                // TODO Fix API so it serves application/json
                json(json, contentType = ContentType.Any)
            }
        }
    }

    single<MuseumApi> { KtorMuseumApi(get()) }
    single<MuseumStorage> { InMemoryMuseumStorage() }
    single {
        MuseumRepository(get(), get()).apply {
            initialize()
        }
    }
}

val viewModelModule = module {
    factoryOf(::ListViewModel)
    factoryOf(::DetailViewModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            viewModelModule,
        )
    }
}
