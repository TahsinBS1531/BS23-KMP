package com.jetbrains.bs23_kmp.screens.detail

import androidx.lifecycle.ViewModel
import com.jetbrains.bs23_kmp.data.MuseumObject
import com.jetbrains.bs23_kmp.data.MuseumRepository
import kotlinx.coroutines.flow.Flow

class DetailViewModel(private val museumRepository: MuseumRepository) : ViewModel() {
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)
}
