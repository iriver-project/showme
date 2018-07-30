package com.sktelecom.showme.selectlogin.login

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sktelecom.showme.R
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.databinding.LoginBodyFragBinding


class LoginBodyFrag : PFragment() {
    internal lateinit var mICallback: ICallback
    internal lateinit var mVM: LoginVM
    internal lateinit var binded: LoginBodyFragBinding

    fun withView(): LoginBodyFrag {
        return this
    }


    internal fun setCallback(mICallback: ICallback) {
        this.mICallback = mICallback
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {

        Log.i("DUER", "abCreateView  ")
        binded = DataBindingUtil.inflate(inflater, R.layout.login_body_frag, container, false)
        //
        mVM = ViewModelProviders.of(this.activity!!).get(LoginVM::class.java)

        val viewadapter = ViewIconPagerAdapter(fragmentManager!!)
        binded.regPager.adapter = viewadapter
        binded.regPager.offscreenPageLimit = 2
//        binded.regPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                //Log.i("positon " + position + " " + positionOffset + " " + positionOffsetPixels);
//                mICallback.scrolled(position, positionOffset, positionOffsetPixels)
//            }
//
//            override fun onPageSelected(position: Int) {
//                Log.i("DUER", position)
//                mICallback.selected(position)
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//
//            }
//        })

        binded.tvForgetPw.setOnClickListener {
            binded.regPager.setCurrentItem(1)
        }

        mVM.asFragCreate()

        return binded.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        val parentViewGroup = binded.root.parent as ViewGroup

        parentViewGroup.removeView(binded.root)

    }


    override fun onCreated() {
        mICallback.init()
    }


    internal inner class ViewIconPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(num: Int): PFragment? {
            when (num) {
                0 -> return mVM.asFragResumeFirst()
                1 -> return mVM.asFragResumeSecond()
                else -> return null
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }


    interface ICallback {
        fun init()
        fun selected(position: Int)
        fun scrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    }

    fun selected(postion: Int) {
        binded.regPager.setCurrentItem(postion, true)
    }

    companion object {
        fun with(mICallback: ICallback): LoginBodyFrag {
            val frag = LoginBodyFrag()
            frag.setCallback(mICallback)
            return frag
        }
    }
}
