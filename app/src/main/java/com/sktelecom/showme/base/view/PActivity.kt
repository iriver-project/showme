package com.sktelecom.showme.base.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

abstract class PActivity : FragmentActivity() {

    lateinit var mFragMan: FragmentManager
    lateinit var pCon: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragMan = supportFragmentManager
        pCon = applicationContext
        onAfaterCreate(savedInstanceState)
    }

    abstract fun onAfaterCreate(savedInstanceState: Bundle?)


    fun pFragAdd(resId: Int, frag: Fragment?) {
        if (frag != null) {
            val fragTrans = mFragMan.beginTransaction()
            fragTrans.add(resId, frag, "")
            fragTrans.commit()
        }
    }
}
