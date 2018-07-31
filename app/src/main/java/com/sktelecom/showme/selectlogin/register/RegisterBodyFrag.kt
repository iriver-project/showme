package com.sktelecom.showme.selectlogin.register

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
import com.sktelecom.showme.databinding.RegisterBodyFragBinding


class RegisterBodyFrag : PFragment() {
    internal lateinit var mICallback: ICallback
    internal lateinit var mVM: RegisterVM
    internal lateinit var binded: RegisterBodyFragBinding

    fun withView(): RegisterBodyFrag {
        return this
    }


    internal fun setCallback(mICallback: ICallback) {
        this.mICallback = mICallback
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {

        Log.i("DUER", "abCreateView  ")
        binded = DataBindingUtil.inflate(inflater, R.layout.register_body_frag, container, false)
        //
        mVM = ViewModelProviders.of(this.activity!!).get(RegisterVM::class.java)

        val viewadapter = ViewIconPagerAdapter(fragmentManager!!)
        binded.regPager.adapter = viewadapter
        binded.regPager.offscreenPageLimit = 3
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

        binded.rlNext.setOnClickListener {
            if (binded.regPager.currentItem == 2) {
                Log.i("CURRENT == 2")
            } else
                binded.regPager.setCurrentItem(++binded.regPager.currentItem)
        }

        binded.tab.setupWithViewPager(binded.regPager, true)
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
                0 -> return mVM.asFragResumeEmail()
                1 -> return mVM.asFragResumePassword()
                2 -> return mVM.asFragResumeUserInfo()
                else -> return null
            }
        }

        override fun getCount(): Int {
            return 3
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


    fun setUserImg(url :String){
        mVM.setUserImg(url)
    }

    companion object {
        fun with(mICallback: ICallback): RegisterBodyFrag {
            val frag = RegisterBodyFrag()
            frag.setCallback(mICallback)
            return frag
        }
    }
}
