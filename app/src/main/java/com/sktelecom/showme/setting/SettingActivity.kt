package com.sktelecom.showme.setting

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sktelecom.showme.R
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivitySettingBinding


class SettingActivity : PActivity() {

    internal lateinit var binding: ActivitySettingBinding
    internal lateinit var mBody: SettingBodyCont

    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        binding.executePendingBindings()

        mBody = SettingBodyCont(this, object : SettingBodyCont.ICallbackToAct {

        })
        pFragAdd(binding.frameBody.id, mBody.asFragCreate())
    }

    internal lateinit var picAddr: String

}
