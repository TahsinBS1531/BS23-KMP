package com.jetbrains.bs23_kmp.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

actual class MapViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()

    fun updateLocation(newLocation: LatLng) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    currentLocation = newLocation,
                    trackedLocations = if (state.isTracking) state.trackedLocations + newLocation else state.trackedLocations
                )
            }
        }
    }

    fun toggleTracking(isTracking: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isTracking = isTracking) }
        }
    }

    fun updateStartTime(time:String) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    startTime = time
                )
            }
        }

    }

    fun updateEndTime(time: String) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    endTime = time
                )
            }
        }

    }

    fun saveMapData(email: String) = viewModelScope.launch {
//        val locations = _uiState.value.trackHistory.last().locations
        val locations = _uiState.value.trackedLocations
        val totalLocations = _uiState.value.trackedLocations.size
        val startTime = _uiState.value.startTime
        val endTime = _uiState.value.endTime
        _uiState.update { state ->
            state.copy(
                isTracking = false,
                trackedLocations = emptyList(),
                startTime = startTime,
                endTime = endTime,
            )
        }

        saveTrackedLocations(
            locations,
            "Last Tracked History",
            "Tracked Locations : $totalLocations",
            email,
            _uiState.value.startTime,
            _uiState.value.endTime
        )
//        repository.insertMapData(_uiState.value.trackHistory.last())
    }

    fun resetTracking() {
        viewModelScope.launch {
            // Reset tracking state and tracked locations and save in history
            _uiState.update { state ->
                state.copy(
                    isTracking = false,
                    trackedLocations = emptyList(),
//                    trackHistory = state.trackHistory + HistoryItem(
//                        title = "Tour History",
//                        description = "Tracked Locations: ${state.trackedLocations.size}",
//                        locations = state.trackedLocations
//                    )
                )
            }
        }
    }

    fun showLocationHistory(email: String) {
        viewModelScope.launch {
            fetchTrackedLocations(email) { result ->
                _uiState.update { currentState ->
                    currentState.copy(
                        trackHistory = result.map { item ->
                            HistoryItem(
                                id = item.first,
                                title = item.second.title,
                                description = item.second.description,
                                locations = locationDataToLatLng(item.second.locations),
                                startTime = item.second.startTime,
                                endTime = item.second.endTime
                            )
                        }
                    )
                }
            }
        }
    }

    fun deleteDocument(userEmail:String,documentId: String){
        viewModelScope.launch {
            deleteDocumentFromFirebase(userEmail, documentId)
        }
    }


    fun updateSelectedTab(index: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(selectedTabIndex = index) }
        }
    }

    fun toggleShowTrack(isShow: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(showTrack = isShow) }
        }
    }

    fun toogleBottomSheet(isShow: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isShowBottomSheet = isShow) }
        }
    }

}

data class MapUiState(
    val isTracking: Boolean = false, // Tracking state
    val currentLocation: LatLng? = LatLng(23.42, 90.20), // default location Dhaka
    val trackedLocations: List<LatLng> = emptyList(), // List to store tracked points
    val selectedTabIndex: Int = 0, // Index of the selected tab
    val trackHistory: List<HistoryItem> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val showTrack: Boolean = false,
    val isShowBottomSheet: Boolean = false,
)

data class HistoryItem(
    var id: String = "",
    val title: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val locations: List<LatLng>,
)

data class LocationData(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

data class HistoryItemFirestore(
    val title: String = "",
    val description: String = "",
    val locations: List<LocationData> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
)


fun saveTrackedLocations(
    trackedLocations: List<LatLng>,
    historyTitle: String,
    description: String,
    userEmail: String,
    startTime: String,
    endTime: String
) {
    val firestore = FirebaseFirestore.getInstance()

    val locationDataList = trackedLocations.map { latLng ->
        LocationData(latLng.latitude, latLng.longitude)
    }

    // Create a history item
    val historyItem = HistoryItemFirestore(
        title = historyTitle,
        description = description,
        locations = locationDataList,
        startTime = startTime,
        endTime = endTime
    )

    firestore.collection("users").document(userEmail).collection("trackedLocations")
        .add(historyItem)
        .addOnSuccessListener {
            println("Tracked locations saved successfully for $userEmail!")
        }
        .addOnFailureListener { e ->
            println("Failed to save tracked locations for $userEmail: ${e.message}")
        }
}

fun fetchTrackedLocations(
    userEmail: String,
    onResult: (List<Pair<String, HistoryItemFirestore>>) -> Unit
) {
    val firestore = FirebaseFirestore.getInstance()

    firestore.collection("users").document(userEmail).collection("trackedLocations")
        .get()
        .addOnSuccessListener { result ->
            val locationHistory = result.documents.map { document ->
                document.id to document.toObject(HistoryItemFirestore::class.java)!!
//                document.toObject(HistoryItemFirestore::class.java)!!
            }
            onResult(locationHistory)
        }
        .addOnFailureListener { e ->
            println("Failed to fetch tracked locations for $userEmail: ${e.message}")
        }
}

fun deleteDocumentFromFirebase(userEmail: String, documentId: String) {
    val db = FirebaseFirestore.getInstance()

    db.collection("users").document(userEmail).collection("trackedLocations").document(documentId)
        .delete()
        .addOnSuccessListener {
            println("DocumentSnapshot successfully deleted!")
        }
        .addOnFailureListener { e ->
            println("Error deleting document: $e")
        }
}

fun locationDataToLatLng(locations: List<LocationData>): List<LatLng> {
    return locations.map { LatLng(it.latitude, it.longitude) }
}

