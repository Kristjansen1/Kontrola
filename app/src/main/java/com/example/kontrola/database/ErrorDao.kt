package com.example.kontrola.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kontrola.model.Error1

@Dao
interface ErrorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(error: Error1)

    @Query("SELECT * FROM error_table")
    fun getAllData(): LiveData<List<Error1>>

    @Delete
    suspend fun delete(error: Error1)

}