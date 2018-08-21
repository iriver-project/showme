package com.sktelecom.showme.setting

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.databinding.SettingBodyFragBinding


class SettingBodyFrag : PFragment() {

    internal lateinit var mICallbackEvent: ICallbackEvent

    internal lateinit var cont: SettingBodyCont
    internal lateinit var binding: SettingBodyFragBinding


    fun setCallback(cont: SettingBodyCont, mICallbackEvent: ICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent
        this.cont = cont;
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.setting_body_frag, container, false)
        binding.cont = cont


        return binding.getRoot()
    }

    override fun onCreated() {
    }


    interface ICallbackEvent {

    }


    companion object {
        fun with(vm: SettingBodyCont, mICallbackEvent: ICallbackEvent): SettingBodyFrag {
            val frag = SettingBodyFrag()
            frag.setCallback(vm, mICallbackEvent)
            return frag
        }

        fun withView(): Companion {
            return this
        }
    }
}