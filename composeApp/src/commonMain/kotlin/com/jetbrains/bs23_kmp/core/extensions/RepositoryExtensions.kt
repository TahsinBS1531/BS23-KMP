package com.bs23.msfa.core.extensions



import com.jetbrains.bs23_kmp.core.domain.error.toException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

//inline fun <T> repoCall(
//    block: () -> Response<T>
//): T {
//    val response = block()
//    val body = response.body()
//    return when (response.isSuccessful && body != null) {
//        true -> body
//        false -> throw response.toException()
//    }
//}

//inline fun <reified T, R> Response<T>.mapSuccess(
//    crossinline block: (T) -> R
//): R {
//    val safeBody = body()
//    if (this.isSuccessful && safeBody != null) {
//        return block(safeBody)
//    }
//    else if(this.isSuccessful && this.code() == 204){
//        return block(T::class.java.newInstance())
//    }
//
//    else {
//        throw toException()
//    }
//}


suspend inline fun <reified T : Any> HttpResponse.mapSuccess(): T {
    val responseBody: T = this.body()
    return if (this.status.isSuccess()) {
        responseBody
    } else {
        throw toException()
    }
}



//inline fun <reified T> Response<T>.mapSuccessNoBody(): Boolean {
//    if(this.isSuccessful && this.body() == null) return true
//    throw toException()
//}