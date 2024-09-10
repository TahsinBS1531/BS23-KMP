package com.jetbrains.bs23_kmp.core.util

//import android.os.Build
//import androidx.annotation.RequiresApi
//import java.text.SimpleDateFormat
//import java.time.Instant
//import java.util.Calendar
//import java.time.LocalDate
//import java.time.LocalDateTime
//import java.time.ZoneId
//import java.time.format.DateTimeFormatter
//import java.time.temporal.ChronoUnit
//
//import java.util.Date
//import java.util.Locale


////val dateFormat = "2023-06-08T07:29:23Z"
//const val dateFormat = "yyyy-MM-dd'T'hh:mm:ss'Z'"
//const val serverFormat = "yyyy-MM-dd'T'HH:mm:ss"
//
//fun getCurrentDateTime(): Date {
//    return Calendar.getInstance().time
//}
//
//fun String.toDate(): Date? {
//    try {
//        val format = SimpleDateFormat(dateFormat)
//        return format.parse(this)
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//    return null
//}
//
//fun String.fromServerDate(): Date? {import kotlinx.datetime.LocalDateTime
//import kotlinx.datetime.toLocalDateTime
//
//fun String.fromServerDate(): LocalDateTime? {    try {
//        val format = SimpleDateFormat(serverFormat)
//        val date = format.parse(this)
//        val calendar = Calendar.getInstance()
//        calendar.time = date
//        calendar.add(Calendar.HOUR_OF_DAY, 6)
//        return calendar.time
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//    return null
//}
//
//fun makeDateObject(day: Int, month: Int, year: Int): Date {
//    val calendar = Calendar.getInstance()
//    calendar.set(year, month-1, day)
//    return calendar.time
//}
//
//fun Date?.toString(): String {
//    val format = SimpleDateFormat(dateFormat, Locale.getDefault())
//    return format.format(this)
//}
//
//fun Date?.toServerDateTime(): String {
//    return this.toString()
//}
//
//
//fun Date?.toDisplayDate(): String {
//    return try {
//        val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
//        format.format(this)
//    } catch (e: Exception) {
//        e.printStackTrace()
//        ""
//    }
//}
//
//fun Date?.toDisplayTime(): String{
//    return try {
//        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
//        format.format(this)
//    } catch (e: Exception) {
//        e.printStackTrace()
//        ""
//    }
//}
//
//fun Date?.toDisplayDateTimeNewLine(): String {
//    return try {
//        val format = SimpleDateFormat("dd MMM yyyy\n hh:mm a", Locale.getDefault())
//        format.format(this)
//    } catch (e: Exception) {
//        e.printStackTrace()
//        ""
//    }
//}
//
//fun Date?.toServerDate(): String {
////    val format = SimpleDateFormat("yyyy-MM-dd'T'00:00:00'Z'", Locale.getDefault())
//    return try{
//        val format = SimpleDateFormat(serverFormat, Locale.getDefault())
//        format.format(this)
//    } catch (e: Exception) {
//        e.printStackTrace()
//        ""
//    }
//}
//
///*
//* This method is used to convert date to server date with time offset
//* UTC +6 (DHAKA TIMEZONE)
// */
//fun Date?.toServerDateWithTimeOffset(): String {
//    val format = SimpleDateFormat(serverFormat, Locale.getDefault())
//    val date = this
//    val calendar = Calendar.getInstance().apply {
//        time = date
//        add(Calendar.HOUR_OF_DAY, -6)
//    }
//    val adjustedDate = calendar.time
//    return format.format(adjustedDate)
//}
//
//fun Date?.toDisplayDateTime(): String {
//    return try {
//        val format = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
//        format.format(this)
//    } catch (e: Exception) {
//        e.printStackTrace()
//        ""
//    }
//}
//
//
//fun Date?.toDayInMonth(): Int {
//    val format = SimpleDateFormat("dd", Locale.getDefault())
//    return format.format(this).toInt()
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//fun LocalDate?.toServerDate(): String {
//    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00")
//    return format.format(this)
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//fun String?.fromServer(): LocalDate {
//    val format = DateTimeFormatter.ofPattern(serverFormat)
//    return LocalDate.parse(this, format)
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//fun LocalDate.toDisplayDate(): String {
//    val format = DateTimeFormatter.ofPattern("dd MMM yyyy")
//    return format.format(this)
//}
//
//
//fun getTimeStamp(): String{
//    return Calendar.getInstance().timeInMillis.toString()
//}
//object DateUtil {
//    fun getCurrentMonth(): Int {
//        val format = SimpleDateFormat("MM", Locale.getDefault())
//        return format.format(Date()).toInt()
//    }
//
//    fun getCurrentYear(): Int {
//        val format = SimpleDateFormat("yyyy", Locale.getDefault())
//        return format.format(Date()).toInt()
//    }
//}
//
//fun Long.toDate(): LocalDate{
//    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate();
//}
//
//fun getCurrentDate() : String {
//    return Calendar.getInstance().timeInMillis.toDate().toString()
//}
//
//fun getYesterdayDate() : String {
//    return (Calendar.getInstance().timeInMillis - 24 * 60 * 60 * 1000).toDate().toString()
//}
//
//fun getTomorrowDate(): String {
//    return (Calendar.getInstance().timeInMillis + 24 * 60 * 60 * 1000).toDate().toString()
//}
//
//fun getStartDateOfCurrentMonth(): String {
//    val currentDate = LocalDate.now()
//
//    val startOfMonth = currentDate.withDayOfMonth(1)
//
//    return startOfMonth.toServerDate()
//}
//
//fun getCurrentDateInServerFormat(): String {
//    val currentDateTime = LocalDateTime.now()
//    val formatter = DateTimeFormatter.ofPattern(dateFormat)
//    return currentDateTime.format(formatter)
//}
//
//// compare a date with before and after date inclusive
//fun Date.isBetween(startDate: Date, endDate: Date): Boolean {
//    return this in startDate..endDate;
//}
//
//fun getPrevTwelleveMonthsWithYear(): List<String> {
//    val currentMonth = LocalDate.now().withDayOfMonth(1)
//
//    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
//
//    val monthName = mutableListOf<String>()
//
//    for (i in 0 until 12) {
//        val month = currentMonth.minus(i.toLong(), ChronoUnit.MONTHS)
//        val formattedMonth = month.format(formatter)
//        monthName.add(formattedMonth)
//    }
//
//    return monthName
//}


//@RequiresApi(Build.VERSION_CODES.O)
//fun getFormattedDateTime(day: Int, month: Int, year: Int, withoutTime: Boolean? = false): String {
//    val localDate = LocalDate.of(year, month, day)
//    val localDateTime = LocalDateTime.of(localDate, LocalDateTime.MIN.toLocalTime())
//    val zonedDateTime = localDateTime.atZone(ZoneOffset.UTC)
//    val formatter = DateTimeFormatter.ISO_INSTANT
//    val dateFormat = SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH)
//    val calendar = Calendar.getInstance()
//    calendar.set(year, month - 1, day)
//    return if (withoutTime == true) dateFormat.format(calendar.time)
//    else formatter.format(zonedDateTime)
//}