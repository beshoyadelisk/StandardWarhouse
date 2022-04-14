package com.gargour.warehouse

import android.app.Application
import com.gargour.warehouse.domain.model.User
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WarehouseApp : Application(){
    companion object {
        var user: User? = null
    }
}