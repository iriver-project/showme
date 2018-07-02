package com.sktelecom.showme.base.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


class CommonUtil private constructor() {


    @SuppressLint("SimpleDateFormat")
    fun now(): String {
        val cal = Calendar.getInstance()
        val dateformat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        return dateformat.format(cal.time)
    }

    private object Holder { val INSTANCE = CommonUtil() }

    companion object {
        val with: CommonUtil by lazy { Holder.INSTANCE }
    }
}