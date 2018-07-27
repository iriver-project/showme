package com.sktelecom.showme.Main.my.wallet.exchange.left

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
import com.sktelecom.showme.databinding.TokenExchangeFragBinding


class TokenExchangeFrag : PFragment() {

    internal lateinit var mICallbackEvent: ICallbackEvent
    //    internal lateinit var mLinearLayoutManager: LinearLayoutManager
    internal lateinit var mGridLayoutManager: GridLayoutManager

    internal lateinit var vm: TokenExchangeVM
    internal lateinit var binding: TokenExchangeFragBinding


    fun setCallback(vm: TokenExchangeVM, mICallbackEvent: ICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent
        this.vm = vm;
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.token_exchange_frag, container, false)
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

        fun with(vm: TokenExchangeVM, mICallbackEvent: ICallbackEvent): TokenExchangeFrag {
            val frag = TokenExchangeFrag()
            frag.setCallback(vm, mICallbackEvent)
            return frag
        }

        fun withView(): Companion {
            return this
        }
    }
}