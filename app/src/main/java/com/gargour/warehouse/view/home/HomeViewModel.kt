package com.gargour.warehouse.view.home

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.gargour.warehouse.util.DispatcherProvider
import com.gargour.warehouse.util.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val dispatcherProvider: DispatcherProvider) :
    ViewModel() {

    private val _navigationEvent = MutableLiveData<SingleEvent<NavDirections>>()
    val navigationEvent: LiveData<SingleEvent<NavDirections>> get() = _navigationEvent

    fun logout(preferences: SharedPreferences) {
        viewModelScope.launch(dispatcherProvider.io) {
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
            //todo(1) navigate back to login
//            _navigationEvent.postValue(SingleEvent())
        }
    }
}