package com.sktelecom.showme.Main.my.profile

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.base.view.animation.CircleAngleAnimation
import com.sktelecom.showme.databinding.ProfileBodyFragBinding


class ProfilelBodyFrag : PFragment() {

    internal lateinit var mICallbackEvent: ICallbackEvent
    //    internal lateinit var mLinearLayoutManager: LinearLayoutManager
    internal lateinit var mGridLayoutManager: GridLayoutManager

    internal lateinit var vm: ProfileBodyVM
    internal lateinit var binding: ProfileBodyFragBinding


    fun setCallback(vm: ProfileBodyVM, mICallbackEvent: ICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent
        this.vm = vm;
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_body_frag, container, false)
        binding.viewmodel = vm
        binding.viewmodel!!.getList().observe(this, Observer {

            setUserImg("https://pbs.twimg.com/profile_images/547798344567779328/GRhJMA8t_400x400.png")
        })



        return binding.getRoot()
    }

    override fun onCreated() {
    }


    fun setUserImg(url: String) {
        Glide.with(pCon).load(url).apply(RequestOptions().circleCrop()).into(binding.ivMyImg)
    }

    interface ICallbackEvent {
        fun getPage(page: Int)
    }


    companion object {

        fun with(vm: ProfileBodyVM, mICallbackEvent: ICallbackEvent): ProfilelBodyFrag {
            val frag = ProfilelBodyFrag()
            frag.setCallback(vm, mICallbackEvent)
            return frag
        }

        fun withView(): Companion {
            return this
        }
    }
}