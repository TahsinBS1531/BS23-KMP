package com.jetbrains.bs23_kmp.screens.home

import com.google.firebase.firestore.FirebaseFirestore

actual fun DeleteDocumentFromFirebase(email: String, documentId: String) {

    val db = FirebaseFirestore.getInstance()

    db.collection("users").document(email).collection("trackedLocations").document(documentId)
        .delete()
        .addOnSuccessListener {
            println("DocumentSnapshot successfully deleted!")
        }
        .addOnFailureListener { e ->
            println("Error deleting document: $e")
        }
}

actual fun SaveTrackedLocations(
    trackedLocations: List<CoordinatesData>,
    historyTitle: String,
    description: String,
    userEmail: String,
    startTime: String,
    endTime: String
) {

    val firestore = FirebaseFirestore.getInstance()

    val locationDataList = trackedLocations.map { latLng ->
        CoordinatesData(latLng.latitude, latLng.longitude)
    }

    val historyItem = HistoryItemFirestore1(
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

actual fun FetchTrackedLocations(
    email: String,
    onResult: (List<Pair<String, HistoryItemFirestore1>>) -> Unit
) {

    val firestore = FirebaseFirestore.getInstance()

    firestore.collection("users").document(email).collection("trackedLocations")
        .get()
        .addOnSuccessListener { result ->
            val locationHistory = result.documents.map { document ->
                document.id to document.toObject(HistoryItemFirestore1::class.java)!!
            }
            onResult(locationHistory)
        }
        .addOnFailureListener { e ->
            println("Failed to fetch tracked locations for $email: ${e.message}")
        }
}
