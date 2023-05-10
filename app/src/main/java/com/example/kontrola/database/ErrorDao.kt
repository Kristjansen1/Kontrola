package com.example.kontrola.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ErrorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(error: Error)

}