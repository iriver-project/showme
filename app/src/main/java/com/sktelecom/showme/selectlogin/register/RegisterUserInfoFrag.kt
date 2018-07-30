package com.sktelecom.showme.selectlogin.register

import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sktelecom.showme.R
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.databinding.RegisterUserinfoFragBinding
import java.util.*

class RegisterUserInfoFrag : PFragment() {

    internal lateinit var mICallbackEvent: ICallbackEvent
    internal lateinit var mLinearLayoutManager: LinearLayoutManager
    internal var list: ArrayList<PBean> = ArrayList()
    internal var page = 0

    internal lateinit var vm: RegisterVM
    internal lateinit var binding: RegisterUserinfoFragBinding


    fun setCallback(vm: RegisterVM, mICallbackEvent: ICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent
        this.vm = vm;
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_userinfo_frag, container, false)
        binding.viewmodel = vm

        return binding.getRoot()
    }


    override fun onCreated() {

    }

    interface ICallbackEvent {
        fun getPage(page: Int)
    }


    companion object {
        fun with(vm: RegisterVM, mICallbackEvent: ICallbackEvent): RegisterUserInfoFrag {
            val frag = RegisterUserInfoFrag()
            frag.setCallback(vm, mICallbackEvent)
            return frag
        }

        fun withView(): Companion {
            return this
        }
    }
}