package com.sktelecom.showme.selectlogin.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivityLoginBinding
import com.sktelecom.showme.databinding.ActivityRegisterBinding


class LoginActivity : PActivity() {

    internal lateinit var binding: ActivityLoginBinding
    internal lateinit var mBody: LoginBodyCont

    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.executePendingBindings()

        mBody = LoginBodyCont(this, object : LoginBodyCont.ICallbackToAct {
            override fun selected(position: Int) {
            }

            override fun scrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })
        pFragAdd(binding.frameBody.id, mBody.asFragCreate())
    }
}
