package com.jetbrains.BS23_KMP.screens.detail

import androidx.lifecycle.ViewModel
import com.jetbrains.BS23_KMP.data.MuseumObject
import com.jetbrains.BS23_KMP.data.MuseumRepository
import kotlinx.coroutines.flow.Flow

class DetailViewModel(private val museumRepository: MuseumRepository) : ViewModel() {
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)
}
