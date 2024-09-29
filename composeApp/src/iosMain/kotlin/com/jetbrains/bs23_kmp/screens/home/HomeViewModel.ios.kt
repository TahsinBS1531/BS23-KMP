package com.jetbrains.bs23_kmp.screens.home

actual fun DeleteDocumentFromFirebase(email: String, documentId: String) {

}

actual fun SaveTrackedLocations(
    trackedLocations: List<CoordinatesData>,
    historyTitle: String,
    description: String,
    userEmail: String,
    startTime: String,
    endTime: String
) {
}

actual fun FetchTrackedLocations(
    email: String,
    onResult: (List<Pair<String, HistoryItemFirestore1>>) -> Unit
) {
}

actual fun MapLogOut() {
}