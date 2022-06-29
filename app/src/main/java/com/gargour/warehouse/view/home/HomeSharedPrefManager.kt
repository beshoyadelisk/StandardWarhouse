package com.gargour.warehouse.view.home

import android.content.Context
import android.content.SharedPreferences
import com.gargour.warehouse.domain.model.Warehouse
import com.gargour.warehouse.util.Constants

object HomeSharedPrefManager {
    fun saveMainWarehouseToSharedPref(warehouse: Warehouse, context: Context) {
        val editor: SharedPreferences.Editor =
            context.getSharedPreferences(
                Constants.WAREHOUSE_PREF_KEY,
                Context.MODE_PRIVATE
            )
                .edit()
        editor.putString(Constants.WAREHOUSE_PREF_NAME, warehouse.name)
        editor.putString(Constants.WAREHOUSE_PREF_CODE, warehouse.code)
        editor.apply()
    }

    fun getMainWarehouseSharedPref(context: Context): Warehouse? {
        val prefs: SharedPreferences =
            context.getSharedPreferences(
                Constants.WAREHOUSE_PREF_KEY,
                Context.MODE_PRIVATE
            )
        val name = prefs.getString(Constants.WAREHOUSE_PREF_NAME, null)
        val code = prefs.getString(Constants.WAREHOUSE_PREF_CODE, null)
        if (name != null && code != null) {
            return Warehouse(code, name, true)
        }
        return null
    }

    fun clearUserSharedPref(context: Context) {
        val editor: SharedPreferences.Editor =
            context.getSharedPreferences(
                Constants.WAREHOUSE_PREF_KEY,
                Context.MODE_PRIVATE
            )
                .edit()
        editor.remove(Constants.WAREHOUSE_PREF_KEY_USER)
        editor.remove(Constants.WAREHOUSE_PREF_KEY_PASS)
        editor.apply()
    }
}