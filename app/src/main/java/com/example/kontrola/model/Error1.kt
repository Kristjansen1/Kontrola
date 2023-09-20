package com.example.kontrola.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kontrola.util.Util
import java.time.LocalDate

@Entity (tableName = "error_table")
data class Error1(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "datum") var datum: String = Util.myDateFormat(LocalDate.now()),
    @ColumnInfo(name = "posel") var posel: String? = "",
    @ColumnInfo(name = "serijska") var serijska: String? = "",
    @ColumnInfo(name = "errorCode") var napaka: Int? = 0,
    @ColumnInfo(name = "opomba") var opomba: String? = "",
    @ColumnInfo(name = "exported") var exported: Boolean = false

) {
}