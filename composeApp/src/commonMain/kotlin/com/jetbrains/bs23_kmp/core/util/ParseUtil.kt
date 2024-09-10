package com.jetbrains.bs23_kmp.core.util


class ParseUtil {
}

object JWTUtils {
    private const val ROLE_KEY = "http://schemas.microsoft.com/ws/2008/06/identity/claims/role"
    private const val BUSINESS_UNIT_KEY = "BusinessUnit"
    private const val IS_HIGHER_LEVEL_MANAGER_KEY = "isHigherLevelManager"
    private const val IS_REQUIRED_APP_SYNC_KEY = "isRequiredAppSync"

//    private fun getPayloadAsJson(JWTEncoded: String): JSONObject {
//        val split = JWTEncoded.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//        val jsonPayload = getJson(split[1])
//        return JSONObject(jsonPayload)
//    }
//
//
//
//    @Throws(UnsupportedEncodingException::class)
//    private fun getJson(strEncoded: String): String {
//        val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
//        return String(decodedBytes, charset("UTF-8"))
//    }
//
//
//    @Throws(Exception::class)
//    fun getRole(JWTEncoded: String): String {
//        return try {
//            getPayloadAsJson(JWTEncoded).getString(ROLE_KEY)
//        } catch (e: Exception) {
//            ""
//        }
//    }
//    fun getBusinessUnit(JWTEncoded: String): List<String> {
//        return try {
//            val businessUnit = getPayloadAsJson(JWTEncoded).getString(BUSINESS_UNIT_KEY)
//            if (businessUnit.contains(",")) {
//                businessUnit.split(",")
//            } else {
//                listOf(businessUnit)
//            }
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }
//
//    fun getIsHigherLevelManager(JWTEncoded: String): String {
//        return try {
//            getPayloadAsJson(JWTEncoded).getString(IS_HIGHER_LEVEL_MANAGER_KEY)
//        } catch (e: Exception) {
//            ""
//        }
//    }
//
//    fun getIsSyncRequired(JWTEncoded: String): String {
//        return try {
//            getPayloadAsJson(JWTEncoded).getString(IS_REQUIRED_APP_SYNC_KEY)
//        } catch (e: Exception) {
//            ""
//        }
//    }
}

//
//object JWTUtils {
//
//
//    @Throws(Exception::class)
//    fun getRole(JWTEncoded: String): String {
//        try {
//            val ROLE_KEY = "http://schemas.microsoft.com/ws/2008/06/identity/claims/role"
//            val split = JWTEncoded.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//            val jsonPayload = getJson(split[1])
//            val jsonObject = JSONObject(jsonPayload)
//            return jsonObject.getString(ROLE_KEY)
//        } catch (e: Exception) {
//            //Error
//            return ""
//        }
//    }
//
//    @Throws(UnsupportedEncodingException::class)
//    private fun getJson(strEncoded: String): String {
//        val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
//        return String(decodedBytes, charset("UTF-8"))
//    }
//
//    // get business unit
//    fun getBusinessUnit(JWTEncoded: String): List<String> {
//        try {
//            val BUSINESS_UNIT_KEY = "BusinessUnit"
//            val split = JWTEncoded.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//            val jsonPayload = getJson(split[1])
//            val jsonObject = JSONObject(jsonPayload)
//            val businessUnit = jsonObject.getString(BUSINESS_UNIT_KEY)
//
//            return if(businessUnit.contains(",")){
//                businessUnit.split(",")
//            } else{
//                listOf(businessUnit)
//            }
//        } catch (e: Exception) {
//            //Error
//            return emptyList()
//        }
//    }
//
//    fun getIsHigherLevelManager(JWTEncoded: String): String {
//        try {
//            val IS_HIGHER_LEVEL_MANAGER_KEY = "isHigherLevelManager"
//            val split = JWTEncoded.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//            val jsonPayload = getJson(split[1])
//            val jsonObject = JSONObject(jsonPayload)
//            return jsonObject.getString(IS_HIGHER_LEVEL_MANAGER_KEY)
//        } catch (e: Exception) {
//            //Error
//            return ""
//        }
//    }
//
//}



//object JWTUtils {
//    @Throws(Exception::class)
//    fun getRole(JWTEncoded: String): String {
//        try {
//            val ROLE_KEY = "http://schemas.microsoft.com/ws/2008/06/identity/claims/role"
//
//            val split = JWTEncoded.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
//                .toTypedArray()
//            Log.d("JWT_DECODED", "Body: " + getJson(split[1]))
//            val role = JSONObject(getJson(split[1])).getString("role")
//            return role
////            Log.d("JWT_DECODED", "Header: " + getJson(split[0]))
////            Log.d("JWT_DECODED", "Body: " + getJson(split[1]))
//        } catch (e: UnsupportedEncodingException) {
//            //Error
//            return ""
//        }
//    }
//
//    @Throws(UnsupportedEncodingException::class)
//    private fun getJson(strEncoded: String): String {
//        val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
//        return String(decodedBytes, charset("UTF-8"))
//    }
//}