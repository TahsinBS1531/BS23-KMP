package com.jetbrains.bs23_kmp.screens.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

actual fun DeleteDocumentFromFirebase(email: String, documentId: String) {

    val db = FirebaseFirestore.getInstance()

    db.collection("users").document(email).collection("trackedLocations").document(documentId)
        .delete()
        .addOnSuccessListener {
            //println("DocumentSnapshot successfully deleted!")
        }
        .addOnFailureListener { e ->
            //println("Error deleting document: $e")
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


    val historyItem = HistoryItemFirestore1(
        title = historyTitle,
        description = description,
        locations = trackedLocations,
        startTime = startTime,
        endTime = endTime
    )

    firestore.collection("users").document(userEmail).collection("trackedLocations")
        .add(historyItem)
        .addOnSuccessListener {
            //println("Tracked locations saved successfully for $userEmail!")
        }
        .addOnFailureListener { e ->
            //println("Failed to save tracked locations for $userEmail: ${e.message}")
        }
}

actual fun FetchTrackedLocations(
    email: String,
    onResult: (List<Pair<String, HistoryItemFirestore1>>) -> Unit
) {

    val firestore = FirebaseFirestore.getInstance()

    firestore.collection("users").document(email).collection("trackedLocations")
        .orderBy("startTime", Query.Direction.ASCENDING)
        .addSnapshotListener { value, error ->
        error?.let {
            //println("Error fetching tracked locations: ${error.message}")
        }
        value?.let {
            val locationHistory = it.documents.map { document ->
                document.id to document.toObject(HistoryItemFirestore1::class.java)!!
            }
            onResult(locationHistory)
        }
    }

}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}
