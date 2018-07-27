package com.sktelecom.showme.Main.my.wallet

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
import com.sktelecom.showme.databinding.WalletInitBodyFragBinding


class WalletInitBodyFrag : PFragment() {

    internal lateinit var mICallbackEvent: ICallbackEvent
    //    internal lateinit var mLinearLayoutManager: LinearLayoutManager
    internal lateinit var mGridLayoutManager: GridLayoutManager

    internal lateinit var vm: WalletInitBodyVM
    internal lateinit var binding: WalletInitBodyFragBinding


    fun setCallback(vm: WalletInitBodyVM, mICallbackEvent: ICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent
        this.vm = vm;
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.wallet_init_body_frag, container, false)
        binding.viewmodel = vm
        binding.viewmodel!!.getList().observe(this, Observer {

            
        })



        return binding.getRoot()
    }

    override fun onCreated() {
    }


    interface ICallbackEvent {
        fun getPage(page: Int)
    }


    companion object {

        fun with(vm: WalletInitBodyVM, mICallbackEvent: ICallbackEvent): WalletInitBodyFrag {
            val frag = WalletInitBodyFrag()
            frag.setCallback(vm, mICallbackEvent)
            return frag
        }

        fun withView(): Companion {
            return this
        }
    }
}