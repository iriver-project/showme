package com.sktelecom.showme.Main.my.wallet

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.*
import com.sktelecom.showme.R
import com.sktelecom.showme.base.Network.AppHelper
import com.sktelecom.showme.base.Network.SmartNetWork
import com.sktelecom.showme.base.Network.VolleyMultipartRequest
import com.sktelecom.showme.base.util.CommonUtil
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivityProfileBinding
import com.sktelecom.showme.databinding.ActivityWalletInitBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class WalletInitActivity : PActivity() {

    internal lateinit var binding: ActivityWalletInitBinding
    internal lateinit var mBody: WalletInitBodyVM
    internal lateinit var picAddr: String

    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet_init)
        binding.executePendingBindings()

        mBody = ViewModelProviders.of(this).get(WalletInitBodyVM::class.java)
        mBody.asFragCreate()

        pFragAdd(binding.frameBody.id, mBody.asFragResume())
    }


}
