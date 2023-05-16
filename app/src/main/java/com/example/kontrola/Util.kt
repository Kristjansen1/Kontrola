package com.example.kontrola

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Util {
    fun myDateFormat(date: LocalDate): String {
        val dateFormat = DateTimeFormatter.ofPattern("d. M. yyyy")
        return date.format(dateFormat)
    }
}