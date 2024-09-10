package com.jetbrains.bs23_kmp.core.data

interface DataSource<T> {
    suspend fun getAll(): List<T>
    suspend fun add(item: T)
    suspend fun addAll(items: List<T>)
    suspend fun clear()
}