package com.gargour.warehouse.view.settings

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.use_case.settings.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {
    private var _loading = MutableLiveData(View.GONE)
    val loading: LiveData<Int> = _loading

    private var _exportResponse = MutableLiveData<Boolean>()
    val exportResponse: LiveData<Boolean> = _exportResponse

    private var _importResponse = MutableLiveData<Boolean>()
    val importResponse: LiveData<Boolean> = _importResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    @SuppressLint("SdCardPath")
    fun export(databasePath: File) {
        viewModelScope.launch {
            settingsUseCases.exportDatabase(databasePath).collect { response ->
                when (response) {
                    is Response.Error -> {
                        _error.postValue(response.message as String)
                    }
                    is Response.Loading -> {
                        _loading.postValue(response.data as Int)
                    }
                    is Response.Success -> {
                        _exportResponse.postValue(response.data as Boolean)
                    }
                }
            }
        }
    }

    fun import(databasePath: File) {
        viewModelScope.launch {
            settingsUseCases.importDatabase(databasePath).collect { response ->
                when (response) {
                    is Response.Error -> {
                        _error.postValue(response.message as String)
                    }
                    is Response.Loading -> {
                        _loading.postValue(response.data as Int)
                    }
                    is Response.Success -> {
                        _importResponse.postValue(response.data as Boolean)
                    }
                }
            }
        }

    }
}