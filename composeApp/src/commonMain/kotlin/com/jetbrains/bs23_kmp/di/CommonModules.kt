package com.jetbrains.bs23_kmp.di

import com.jetbrains.bs23_kmp.dashboard.DashboardViewModel
import com.jetbrains.bs23_kmp.dashboard.data.DashboardApi
import com.jetbrains.bs23_kmp.dashboard.data.DashboardRepository
import com.jetbrains.bs23_kmp.data.InMemoryMuseumStorage
import com.jetbrains.bs23_kmp.data.KtorMuseumApi
import com.jetbrains.bs23_kmp.data.MuseumApi
import com.jetbrains.bs23_kmp.data.MuseumRepository
import com.jetbrains.bs23_kmp.data.MuseumStorage
import com.jetbrains.bs23_kmp.screens.detail.DetailViewModel
import com.jetbrains.bs23_kmp.screens.list.ListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
//    single {
//        val json = Json { ignoreUnknownKeys = true }
//        HttpClient {
//            install(ContentNegotiation) {
//                // TODO Fix API so it serves application/json
//                json(json, contentType = ContentType.Any)
//            }
//        }
//    }

    single<MuseumApi> { KtorMuseumApi(get()) }
    single<MuseumStorage> { InMemoryMuseumStorage() }
    single {
        MuseumRepository(get(), get()).apply {
            initialize()
        }
    }
    singleOf(::DashboardRepository).bind<DashboardApi>()
}
val viewModelModule = module {
    factoryOf(::ListViewModel)
    factoryOf(::DetailViewModel)
    viewModelOf(::DashboardViewModel)
}