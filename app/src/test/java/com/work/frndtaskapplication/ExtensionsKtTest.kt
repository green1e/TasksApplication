package com.work.frndtaskapplication

import com.work.frndtaskapplication.data.model.Date
import org.junit.Assert.*

import org.junit.Test

class ExtensionsKtTest {

    @Test
    fun date_To_YearMonthDay_ForSorting_Test() {
        assertEquals("26-09-2023".toYearMonthDay(), "2023-09-26")
    }

    @Test
    fun date_To_DayMonthYear_ForDisplay_Test() {
        assertEquals("26-09-2023".getDayMonthYear(), "26 Sept 2023")
    }

    @Test
    fun date_To_StringDate_ForSaveApi_Test() {
        val date = Date(8, 2023, "26")
        assertEquals(date.getDate(), "26-09-2023")
    }

    @Test
    fun stringDate_isEqualTo_Date_Test() {
        val date = Date(8, 2023, "26")
        assertEquals("26-09-2023".isEqualTo(date), true)
    }

    @Test
    fun convert_SingleDigit_To_DoubleDigit__ForSaveApi_Test() {
        assertEquals(convert(7), "07")
    }
}