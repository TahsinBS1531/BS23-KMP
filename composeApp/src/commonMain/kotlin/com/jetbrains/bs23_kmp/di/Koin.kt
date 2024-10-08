package com.jetbrains.bs23_kmp.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            viewModelModule,
            networkModules()
        )
    }
}
