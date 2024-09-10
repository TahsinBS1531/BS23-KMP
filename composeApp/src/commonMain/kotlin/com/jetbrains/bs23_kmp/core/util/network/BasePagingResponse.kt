package com.jetbrains.bs23_kmp.core.util.network

open class BasePagingResponse {
//    @SerializedName("currentPage")
//    var currentPage: Int = 0
//
//    @SerializedName("totalPages")
//    var totalPages: Int = 0
//
//    @SerializedName("totalCount")
//    var totalCount: Int = 0
//
//    @SerializedName("pageSize")
//    var pageSize: Int = 0
//
//    @SerializedName("hasPreviousPage")
//    var hasPreviousPage: Boolean = true
//
//    @SerializedName("hasNextPage")
//    var hasNextPage: Boolean = true

    var currentPage: Int = 0
    var totalPages: Int = 0
    var totalCount: Int = 0
    var pageSize: Int = 0
    var hasPreviousPage: Boolean = true
    var hasNextPage: Boolean = true

}