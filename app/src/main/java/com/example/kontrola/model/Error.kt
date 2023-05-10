package com.example.kontrola.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.time.LocalDate

class Error(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "datum") val datum: String = LocalDate.now().toString(),
    @ColumnInfo(name = "posel") var posel: String? = "",
    @ColumnInfo(name = "serijska") var serijska: String? = "",
    @ColumnInfo(name = "errorCode") var errorCode: Int? = 0,
    @ColumnInfo(name = "opomba") var opomba: String? = ""

) {
}