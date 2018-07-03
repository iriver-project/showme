package com.sktelecom.showme.Main

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sktelecom.showme.Main.Home.HomeBodyVM
import com.sktelecom.showme.Main.feed.FeedBodyVM
import com.sktelecom.showme.R
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.databinding.MainBodyFragBinding


class MainBodyFrag : PFragment() {
    internal lateinit var mICallback: ICallback
    internal lateinit var homeVm: HomeBodyVM
    internal lateinit var feedVm: FeedBodyVM
    internal lateinit var binded: MainBodyFragBinding

    fun withView(): MainBodyFrag {
        return this
    }


    internal fun setCallback(mICallback: ICallback) {
        this.mICallback = mICallback
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {

        Log.i("DUER", "abCreateView  ")
        binded = DataBindingUtil.inflate(inflater, R.layout.main_body_frag, container, false)
        //
        homeVm = ViewModelProviders.of(this.activity!!).get(HomeBodyVM::class.java)
        feedVm = ViewModelProviders.of(this.activity!!).get(FeedBodyVM::class.java)
        //        rightCont = new WalletRightCont(getActivity(), this, null);


        val viewadapter = ViewIconPagerAdapter(fragmentManager!!)
        binded.pager.adapter = viewadapter
        binded.pager.offscreenPageLimit = 4
        binded.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //Log.i("positon " + position + " " + positionOffset + " " + positionOffsetPixels);
                mICallback.scrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                Log.i("DUER", position)
                mICallback.selected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


        homeVm.asFragCreate()
        feedVm.asFragCreate()
        //        leftCont.asViewModelCreate();
        //        rightCont.asViewModelCreate();
        //        rightVm.asFragCreate();


        return binded.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        val parentViewGroup = binded.root.parent as ViewGroup

        parentViewGroup.removeView(binded.root)

    }


    fun setInitPosition(position: Int) {
        binded.pager.currentItem = position
        mICallback.selected(position)
    }

    override fun onCreated() {
        mICallback.init()
    }


    internal inner class ViewIconPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(num: Int): PFragment? {
            when (num) {
                0 -> return homeVm.asFragResume()
                1 -> return feedVm.asFragResume()
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
        binded.pager.setCurrentItem(postion, true)
    }

    companion object {
        fun with(mICallback: ICallback): MainBodyFrag {
            val frag = MainBodyFrag()
            frag.setCallback(mICallback)
            return frag
        }
    }
}
