package com.gargour.warehouse.domain.use_case.registration

import android.annotation.SuppressLint
import android.os.Build
import com.gargour.warehouse.util.Constants.APP_IDENTIFIER

class GetSerial {
    @SuppressLint("PrivateApi", "HardwareIds")
    operator fun invoke(): String {
        var serialNumber: String

        try {
            if (Build.BRAND.equals("SUNMI", true) &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.R
            ) {
                serialNumber = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Build.getSerial() + APP_IDENTIFIER
                } else {
                    Build.SERIAL + APP_IDENTIFIER
                }
                return serialNumber.ifEmpty {
                    "unknown$APP_IDENTIFIER"
                }
            }
            val c = Class.forName("android.os.SystemProperties")
            val get = c.getMethod("get", String::class.java)
            serialNumber = (get.invoke(c, "gsm.sn1") as String)
            if (serialNumber.isEmpty()) {
                serialNumber = get.invoke(c, "ril.serialnumber") as String
            }
            if (serialNumber.isEmpty()) {
                serialNumber = get.invoke(c, "ro.serialno") as String
            }
            if (serialNumber.isEmpty()) {
                serialNumber = get.invoke(c, "sys.serialnumber") as String
            }
        } catch (e: Exception) {
            e.printStackTrace()
            serialNumber = ""
        }
        if (serialNumber.isNotEmpty()) {
            serialNumber += APP_IDENTIFIER
        }
        return serialNumber.ifEmpty {
            "unknown$APP_IDENTIFIER"
        }

    }
}