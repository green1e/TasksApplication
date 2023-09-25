package com.work.frndtaskapplication

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.widget.Toast
import com.work.frndtaskapplication.data.ApiResult
import com.work.frndtaskapplication.data.model.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.Serializable

fun <T> safeApiCall(block: suspend () -> Response<T>): Flow<ApiResult<T?>> {
    return flow {
        emit(ApiResult.Loading())
        val response = block()
        if (response.isSuccessful) emit(ApiResult.Success(response.body()))
        else emit(ApiResult.Error())
    }.flowOn(Dispatchers.IO).catch {
        Log.d("lmao", it.toString())
        emit(ApiResult.Error())
    }
}


@Suppress("DEPRECATION")
inline fun <reified T : Serializable> Intent.getSerializable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(key, T::class.java)
    } else {
        getSerializableExtra(key) as? T
    }
}

fun String.toYearMonthDay(): String {
    val (day, month, year) = this.split("-")
    return "$year-$month-$day"
}

fun String.getDayMonthYear(): String {
    val (day, month, year) = this.split("-")
    return "$day ${month.getMonthText()} $year"
}

fun Date.getDate(): String {
    return "${convert(this.day.toInt())}-${convert(this.month + 1)}-${this.year}"
}

fun String.isEqualTo(date: Date): Boolean {
    val (day, month, year) = this.split("-")
    return date.day.toInt() == day.toInt() && date.month == month.toInt() - 1 && date.year == year.toInt()
}

fun Resources.dpToPx(dp: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics)
        .toInt()
}

fun String.getMonthText(): String {
    return when (this) {
        "01" -> "Jan"
        "02" -> "Feb"
        "03" -> "Mar"
        "04" -> "Apr"
        "05" -> "May"
        "06" -> "June"
        "07" -> "July"
        "08" -> "Aug"
        "09" -> "Sept"
        "10" -> "Oct"
        "11" -> "Nov"
        "12" -> "Dec"
        else -> ""
    }
}

fun Int.getMonthFullText(): String {
    return when (this) {
        0 -> "January"
        1 -> "February"
        2 -> "March"
        3 -> "April"
        4 -> "May"
        5 -> "June"
        6 -> "July"
        7 -> "August"
        8 -> "September"
        9 -> "October"
        10 -> "November"
        11 -> "December"
        else -> ""
    }
}

fun convert(num: Int): String {
    return if (num < 10) "0$num" else num.toString()
}

fun Context?.showToast(message: String) {
    this ?: return
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}