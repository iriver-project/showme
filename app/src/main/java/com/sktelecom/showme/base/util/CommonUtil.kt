package com.sktelecom.showme.base.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import java.text.SimpleDateFormat
import java.util.*


class CommonUtil private constructor() {


    @SuppressLint("SimpleDateFormat")
    fun now(): String {
        val cal = Calendar.getInstance()
        val dateformat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        return dateformat.format(cal.time)
    }

    private object Holder {
        val INSTANCE = CommonUtil()
    }

    //dp to px
    fun dpTopx(pCon: Context, dp: Int): Int {
        val r = pCon.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics).toInt()
    }

    companion object {
        val with: CommonUtil by lazy { Holder.INSTANCE }
    }
}