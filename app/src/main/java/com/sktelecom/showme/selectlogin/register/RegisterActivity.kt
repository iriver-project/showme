package com.sktelecom.showme.selectlogin.register

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivityRegisterBinding


class RegisterActivity : PActivity() {

    internal lateinit var binding: ActivityRegisterBinding
    internal lateinit var mBody: RegisterBodyCont

    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.executePendingBindings()

        mBody = RegisterBodyCont(this, object : RegisterBodyCont.ICallbackToAct {
            override fun selected(position: Int) {
            }

            override fun scrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })
        pFragAdd(binding.frameBody.id, mBody.asFragCreate())
    }
}
