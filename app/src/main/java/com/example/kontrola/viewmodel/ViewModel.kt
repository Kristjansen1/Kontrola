package com.example.kontrola.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kontrola.database.AppDatabase
import com.example.kontrola.model.Error1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application): AndroidViewModel(application) {

    private val appDatabase = AppDatabase.getDatabase(application)

    val allError: LiveData<List<Error1>> = appDatabase.errorDao().getAllData()

    private val _itemAddEdit = MutableLiveData<Error1?>()
    val itemAddEdit: MutableLiveData<Error1?> = _itemAddEdit



    init {
        Log.d("viewmodel","init")
    }

    fun setItemAddEdit(item: Error1?) {
        _itemAddEdit.value = item
    }
    fun addError(error: Error1) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.errorDao().insert(error)
        }

    }
    fun deleteError(error: Error1) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.errorDao().delete(error)
        }
    }

}
