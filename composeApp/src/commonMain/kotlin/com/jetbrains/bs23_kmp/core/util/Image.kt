package com.jetbrains.bs23_kmp.core.util


data class Image(
    val name: String,
    val extension: String,
    val data: String
){
//    companion object{
//        fun toImage(context: Context, imageName: String, imageUri: Uri) = Image(
//            name = imageName,
//            extension = ".jpeg",
//            data = "$BASE64_IMAGE_SOURCE_PREFIX${imageUri.toBase64(context)}"
//        )
//    }
}
