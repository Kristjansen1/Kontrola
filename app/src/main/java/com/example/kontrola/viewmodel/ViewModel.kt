package com.example.kontrola.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Database
import com.example.kontrola.database.AppDatabase
import com.example.kontrola.database.ErrorDao
import com.example.kontrola.model.Error1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application): AndroidViewModel(application) {

    private val appDatabase = AppDatabase.getDatabase(application)

    val allError: LiveData<List<Error1>> = appDatabase.errorDao().getAllData()

    private val _editIcon = MutableLiveData<Boolean>()
    val editIcon: LiveData<Boolean> = _editIcon


    val _x = MutableLiveData<String>()
    val x: LiveData<String> = _x

    init {
        Log.d("viewmodel","init")
    }

    fun setEditIconVisible(visible: Boolean) {
        _editIcon.value = visible
    }
    fun addError(errorList: ArrayList<Error1>) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.errorDao().insert(errorList)
        }

    }

}
