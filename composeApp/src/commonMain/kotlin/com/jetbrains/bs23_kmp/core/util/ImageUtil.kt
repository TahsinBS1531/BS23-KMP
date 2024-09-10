package com.jetbrains.bs23_kmp.core.util

const val BASE64_IMAGE_SOURCE_PREFIX = "data:image/jpeg;base64,"

//fun Uri?.toBase64(context: Context): String {
//    if(this == null) return ""
//    val bitmap = if(Build.VERSION.SDK_INT < 28) {
//        MediaStore.Images.Media.getBitmap(
//            context.contentResolver,
//            this
//        )
//
//    } else {
//        val source = ImageDecoder.createSource(context.contentResolver, this)
//        ImageDecoder.decodeBitmap(source)
//
//    }
//    return bitmapToBase64(bitmap)
//}

//fun bitmapToBase64(bitmap: Bitmap): String {
//    val byteStream = ByteArrayOutputStream()
//    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteStream)
//    val b: ByteArray = byteStream.toByteArray()
//
//    return Base64.encodeToString(b, Base64.NO_WRAP)
//}
