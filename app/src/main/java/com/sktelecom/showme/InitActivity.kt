package com.sktelecom.showme

import android.content.Intent
import android.os.Bundle
import com.sktelecom.showme.Main.MainActivity
import com.sktelecom.showme.Wallet.WalletActivity
import com.sktelecom.showme.base.view.PActivity
import org.ratpoisonfactory.base.util.CommonUtil
import org.ratpoisonfactory.base.util.Log

class InitActivity : PActivity() {
    override fun onAfaterCreate(savedInstanceState: Bundle?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        setContentView(R.layout.activity_init)
        Log.i(CommonUtil.with.now())
        startNextActivity()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    private fun startNextActivity() {
        val intent = Intent(pCon, WalletActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
        finish()
    }
}
