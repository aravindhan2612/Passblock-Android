package com.ab.an.core.utils

import android.content.Context
import android.telephony.TelephonyManager
import android.util.Base64
import java.util.Locale

object CommonUtils {

    fun getSimRegion(context: Context): String? {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.simCountryIso?.uppercase() // e.g. "US"
    }

    fun getNetworkRegion(context: Context): String? {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.networkCountryIso?.uppercase() // e.g. "IN"
    }

    fun getLocaleRegion(): String {
        return Locale.getDefault().country // e.g. "GB"
    }

    fun decodeBase64ToByteArray(base64String: String): ByteArray {
        return Base64.decode(base64String, Base64.DEFAULT)
    }

    fun convertByteArrayToBase64(bytes: ByteArray) : String {
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }
}