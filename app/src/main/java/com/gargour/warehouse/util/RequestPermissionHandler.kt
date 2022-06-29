package com.gargour.warehouse.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.util.*

object RequestPermissionHandler {

    fun Fragment.requestPermission(
        activity: Activity,
        permissions: Array<String>,
        listener: RequestPermissionListener,
    ) {
        Log.d("RequestPermission", "requestPermission start at: ${System.currentTimeMillis()} ")
        if (!needRequestRuntimePermissions()) {
            listener.onSuccess()
            return
        }
        requestUnGrantedPermissions(permissions, listener, activity)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun needRequestRuntimePermissions(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    private fun Fragment.requestUnGrantedPermissions(
        permissions: Array<String>,
        listener: RequestPermissionListener,
        activity: Activity,
    ) {
        val unGrantedPermissions = findUnGrantedPermissions(permissions, activity)
        if (unGrantedPermissions.isEmpty()) {
            listener.onSuccess()
            return
        }
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            var isAllGranted = true
            result.values.forEach { granted ->
                isAllGranted = isAllGranted && granted
            }
            if (isAllGranted) {
                listener.onSuccess()
            } else {
                listener.onFailed()
            }
        }.launch(permissions)
    }


    private fun findUnGrantedPermissions(
        permissions: Array<String>,
        activity: Activity,
    ): Array<String> {
        val unGrantedPermissionList: MutableList<String> = ArrayList()
        for (permission in permissions) {
            if (!isPermissionGranted(permission, activity)) {
                unGrantedPermissionList.add(permission)
            }
        }
        return unGrantedPermissionList.toTypedArray()
    }

    private fun isPermissionGranted(permission: String, activity: Activity): Boolean {
        return (ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_GRANTED)
    }

    interface RequestPermissionListener {
        fun onSuccess()
        fun onFailed()
    }
}
