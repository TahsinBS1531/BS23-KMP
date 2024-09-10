package com.jetbrains.bs23_kmp.core.di


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


object CoroutinesModule {

//    @DefaultDispatcher
//    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

//    @IoDispatcher
//    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

//    @MainDispatcher
//    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

//    @MainImmediateDispatcher
//    @Provides
    fun providesMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}