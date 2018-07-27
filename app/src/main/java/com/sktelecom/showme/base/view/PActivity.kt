package com.sktelecom.showme.base.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

abstract class PActivity : FragmentActivity() {
    var MY_READ_EXTERNAL_STORAGE = 40
    var REQUEST_SELECT_PICTURE = 100
    var REQUEST_SELECT_VIDEO = 200
    lateinit var mFragMan: FragmentManager
    lateinit var pCon: Context

    fun checkReadExternalStoragePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_READ_EXTERNAL_STORAGE)
                return false
            } else {
                return true
            }
        }

        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragMan = supportFragmentManager
        pCon = applicationContext
        onAfaterCreate(savedInstanceState)
    }

    abstract fun onAfaterCreate(savedInstanceState: Bundle?)


    fun pFragAdd(resId: Int, frag: Fragment) {
        if (frag != null) {
            val fragTrans = mFragMan.beginTransaction()
            fragTrans.add(resId, frag, "")
            fragTrans.commit()
        }
    }

    fun pFragReplace(resId: Int, frag: Fragment) {
        if (frag != null) {
            val fragTrans = mFragMan.beginTransaction()
            fragTrans.replace(resId, frag, "")
            fragTrans.commit()
        }
    }
}
