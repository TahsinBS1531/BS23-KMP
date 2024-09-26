package com.jetbrains.bs23_kmp.screens.auth

import dev.gitlive.firebase.auth.UserMetaData

data class User(
    val id: String = "",
    val isAnonymous: Boolean = true,
    val displayName:String?="",
    val phoneNumber : String?="",
    val email:String? ="",
    val metaData:UserMetaData? = null
)
