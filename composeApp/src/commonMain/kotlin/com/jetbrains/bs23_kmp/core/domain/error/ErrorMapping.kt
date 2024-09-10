package com.jetbrains.bs23_kmp.core.domain.error



import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException


//@Suppress("MagicNumber")
//fun <T> Response<T>.toException(): ExceptionModel.Http {
//    return when {
////        code() == 400 -> ExceptionModel.Http.BadRequest
////        code() == 401 -> ExceptionModel.Http.Unauthorized
//
//        code() == 204 -> ExceptionModel.Http.Custom(
//            code(),
//            message(),
//            "No Content"
//        )
//        code() == 400 -> ExceptionModel.Http.Custom(
//            code(),
//            message(),
//            convertToBadRequestErrorDto(errorBody())?.errors.toString()
//        )
////        code() == 403 -> ExceptionModel.Http.Forbidden
////        code() == 404 -> ExceptionModel.Http.NotFound
//        code() == 405 -> ExceptionModel.Http.MethodNotAllowed
////        code() in 500..600 -> ExceptionModel.Http.ServerException
//        else -> {
//            try {
//                val errorDto = convertToErrorDto(errorBody())?.messages?.firstOrNull()
//                ExceptionModel.Http.Custom(
//                    code(),
//                    message(),
//                    errorDto
//                )
//            } catch (e: Exception) {
//                ExceptionModel.Http.Custom(code(), "Server responded with unhandled exception","Server responded with unhandled exception")
//            }
//
//
//        }
//    }
//}



suspend fun HttpResponse.toException(): ExceptionModel.Http {
    return when (status.value) {
        400 -> ExceptionModel.Http.BadRequest
        401 -> ExceptionModel.Http.Unauthorized
        403 -> ExceptionModel.Http.Forbidden
        404 -> ExceptionModel.Http.NotFound
        405 -> ExceptionModel.Http.MethodNotAllowed
        in 500..599 -> ExceptionModel.Http.ServerException
        else -> {
            val errorBody = runCatching { body<String>() }.getOrNull() ?: "Unknown error"
            ExceptionModel.Http.Custom(
                code = status.value,
                message = status.description,
                errorBody = errorBody
            )
        }
    }
}


suspend fun Throwable.toExceptionModel(): ExceptionModel {
    return when (this) {
        is ClientRequestException -> this.response.toException() // 4xx errors
        is ServerResponseException -> this.response.toException() // 5xx errors
        is SocketTimeoutException -> ExceptionModel.Connection.Timeout
//        is UnknownHostException -> ExceptionModel.Connection.UnknownHost
        is IOException -> ExceptionModel.Connection.IOError
        is SerializationException -> ExceptionModel.DataException.ParseException(this)
        else -> ExceptionModel.Unknown(this)
    }
}


//@Suppress("TooGenericExceptionThrown", "TooGenericExceptionCaught")
//fun convertToErrorDto(errorBody: ResponseBody?): ErrorDto? {
//    return try {
//        Gson().fromJson(
//            errorBody?.string(),
//            ErrorDto::class.java
//        )
//    } catch (e: Exception) {
////        throw Throwable("Response processing error: Unauthorized")
//        return null
//    }
//}

//@Suppress("TooGenericExceptionThrown", "TooGenericExceptionCaught")
//fun convertToBadRequestErrorDto(errorBody: ResponseBody?): BadRequestErrorDto? {
//    return try {
//        Gson().fromJson(
//            errorBody?.string(),
//            BadRequestErrorDto::class.java
//        )
//    } catch (e: Exception) {
//        throw Throwable("Response processing error: BadRequest")
//    }
//}


fun Throwable.toException(): ExceptionModel {
    return when (this) {
        is SocketTimeoutException -> ExceptionModel.Connection.Timeout
//        is UnknownHostException -> ExceptionModel.Connection.UnknownHost
        is IOException -> ExceptionModel.Connection.IOError
        is SerializationException -> ExceptionModel.DataException.ParseException(this)
        else -> ExceptionModel.Unknown(this)
    }
}
