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

    private val dataStore = DataStoreManager(application)

    private val _menuAddIcon = MutableLiveData<Boolean>()
    val menuAddIcon: LiveData<Boolean> = _menuAddIcon

    private val _menuUpdateIcon = MutableLiveData<Boolean>()
    val menuUpdateIcon: LiveData<Boolean> = _menuUpdateIcon

    private val _menuSettingsIcon = MutableLiveData<Boolean>()
    val menuSettingsIcon: LiveData<Boolean> = _menuSettingsIcon

    private val _menuExportIcon = MutableLiveData<Boolean>()
    val menuExportIcon: LiveData<Boolean> = _menuExportIcon

    private val _menuDeleteIcon = MutableLiveData<Boolean>()
    val menuDeleteIcon: LiveData<Boolean> = _menuDeleteIcon

    private val _menuEditItemIcon = MutableLiveData<Boolean>()
    val menuEditItemIcon = _menuEditItemIcon

    private val _lastLongClickSelection = MutableLiveData<Int>()
    val lastLongClickSelection: LiveData<Int> = _lastLongClickSelection





    init {
        Log.d("viewmodel","init")
    }

    fun setLastLongClickSelection(value: Int) {
        _lastLongClickSelection.value = value
    }
    fun setMenuEditItemIconVisibility(value: Boolean) {
        _menuEditItemIcon.value = value
    }
    fun setMenuAddIconVisibility(value: Boolean) {
        _menuAddIcon.value = value
    }
    fun setMenuUpdateIconVisibility(value: Boolean) {
        _menuUpdateIcon.value = value
    }
    fun setMenuSettingsIconVisibility(value: Boolean) {
        _menuSettingsIcon.value = value
    }
    fun setMenuExportIconVisibility(value: Boolean) {
        _menuExportIcon.value = value
    }
    fun setMenuDeleteIconVisibility(value: Boolean) {
        _menuDeleteIcon.value = value
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
