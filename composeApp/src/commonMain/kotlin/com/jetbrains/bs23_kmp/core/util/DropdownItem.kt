package com.jetbrains.bs23_kmp.core.util

data class DropdownItem(
    val id: String,
    val name: String,
    val item: Any? = null,
    val parentId: String? = "",
    val secondaryText: String = "",
) {
    override fun toString(): String {
        return name
    }

    fun <T>getItemObj(): T? {
        try{
            return item as T
        }
        catch (e: Exception){
            return null
        }
    }
}
