package com.example.kontrola.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kontrola.database.AppDatabase
import com.example.kontrola.datastore.DataStoreManager
import com.example.kontrola.model.Error1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ViewModel(application: Application): AndroidViewModel(application) {

    private val appDatabase = AppDatabase.getDatabase(application)
    val allError: LiveData<List<Error1>> = appDatabase.errorDao().getAllData()

    private val _itemAddEdit = MutableLiveData<Error1?>()
    val itemAddEdit: MutableLiveData<Error1?> = _itemAddEdit

    val dataStore = DataStoreManager(application)


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

    fun updateError(error: Error1) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.errorDao().update(error)
        }

    }
    fun readDataStore(key: String): String = runBlocking {
         dataStore.read(key) ?: ""
    }
    fun writeDataStore(key: String, value: String) {
        viewModelScope.launch {
            dataStore.write(key,value)
        }

    }

}
