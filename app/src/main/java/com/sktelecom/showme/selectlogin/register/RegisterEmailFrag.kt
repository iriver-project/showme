package com.sktelecom.showme.selectlogin.register

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.databinding.RegisterEmailFragBinding

class RegisterEmailFrag : PFragment() {

    internal lateinit var vm: RegisterVM
    internal lateinit var binding: RegisterEmailFragBinding
    internal lateinit var mICallbackEvent: ICallbackEvent

    fun setCallback(vm: RegisterVM, mICallbackEvent: ICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent
        this.vm = vm;
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_email_frag, container, false)
        binding.viewmodel = vm

        return binding.getRoot()
    }


    override fun onCreated() {

    }

    interface ICallbackEvent {

        fun getPage(page: Int)
    }


    companion object {

        fun with(vm: RegisterVM, mICallbackEvent: ICallbackEvent): RegisterEmailFrag {
            val frag = RegisterEmailFrag()
            frag.setCallback(vm, mICallbackEvent)
            return frag
        }

        fun withView(): Companion {
            return this
        }
    }
}