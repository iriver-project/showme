package com.sktelecom.showme.selectlogin

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sktelecom.showme.R
import com.sktelecom.showme.base.util.CommonUtil
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivitySelectLoginBinding


class SelectLoginActivity : PActivity() {

    internal lateinit var binding: ActivitySelectLoginBinding
    internal lateinit var mBody: SelectLoginBodyCont

    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_login)
        Log.i(CommonUtil.with.now())
        binding.executePendingBindings()

        mBody = SelectLoginBodyCont(this, object : SelectLoginBodyCont.ICallbackToAct {
            override fun selected(position: Int) {
            }

            override fun scrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })
        pFragAdd(binding.frameBody.id, mBody.asFragCreate())
    }
}
