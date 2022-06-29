package com.gargour.warehouse.view.settings

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gargour.warehouse.data.Response
import com.gargour.warehouse.domain.model.Warehouse
import com.gargour.warehouse.domain.use_case.settings.SettingsUseCases
import com.gargour.warehouse.domain.use_case.warehouse.GetWarehouses
import com.gargour.warehouse.util.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCases: SettingsUseCases,
    private val getWarehousesUseCase: GetWarehouses
) : ViewModel() {
    private var _loading = MutableLiveData(View.GONE)
    val loading: LiveData<Int> = _loading

    private var _exportResponse = MutableLiveData<SingleEvent<Boolean>>()
    val exportResponse: LiveData<SingleEvent<Boolean>> = _exportResponse

    private var _importResponse = MutableLiveData<SingleEvent<Boolean>>()
    val importResponse: LiveData<SingleEvent<Boolean>> = _importResponse

    private var _warehousesResponse = MutableLiveData<SingleEvent<List<Warehouse>>>()
    val warehousesResponse: LiveData<SingleEvent<List<Warehouse>>> = _warehousesResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getWarehouses() {
        viewModelScope.launch(Dispatchers.IO) {
            getWarehousesUseCase().collect { response ->
                when (response) {
                    is Response.Error -> _error.postValue(response.message as String)
                    is Response.Loading -> _loading.postValue(response.data as Int)
                    is Response.Success -> _warehousesResponse.postValue(SingleEvent(response.data as List<Warehouse>))
                }
            }
        }
    }

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
                        _exportResponse.postValue(SingleEvent(response.data as Boolean))
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
                        _importResponse.postValue(SingleEvent(response.data as Boolean))
                    }
                }
            }
        }

    }
}