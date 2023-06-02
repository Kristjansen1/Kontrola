package com.example.kontrola.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kontrola.model.Error1

@Dao
abstract class ErrorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(error: Error1)

    @Query("SELECT * FROM error_table")
    abstract fun getAllData(): LiveData<List<Error1>>

    @Delete
    abstract suspend fun delete(error: Error1)

    @Update
    abstract fun update(error: Error1)

}