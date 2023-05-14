package com.example.kontrola.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity (tableName = "error_table")
data class Error1(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "datum") val datum: String = LocalDate.now().toString(),
    @ColumnInfo(name = "posel") var posel: String? = "",
    @ColumnInfo(name = "serijska") var serijska: String? = "",
    @ColumnInfo(name = "errorCode") var napaka: String? = "",
    @ColumnInfo(name = "opomba") var opomba: String? = "",
    @ColumnInfo(name = "exported") var exported: Boolean = false

) {
}