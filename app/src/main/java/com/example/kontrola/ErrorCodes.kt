package com.example.kontrola

object ErrorCodes {
    fun getByCode(code: Int) : String {
        return when (code) {
            0 -> "CEE Priklop"
            1 -> "Razdelilec"
            2 -> "Kabli"
            3 -> "Vezava Razvodnice"
            4 -> "Finomontaža"
            5 -> "Kanal"
            else -> ""
        }
    }
}