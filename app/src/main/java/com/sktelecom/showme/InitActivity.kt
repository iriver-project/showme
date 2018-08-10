package com.sktelecom.showme

import android.content.Intent
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.sktelecom.showme.Main.MainActivity
import com.sktelecom.showme.base.room.DBManager
import com.sktelecom.showme.base.util.CommonUtil
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PActivity


class InitActivity : PActivity() {
    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this);
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        setContentView(R.layout.activity_init)
        Log.i(CommonUtil.with.now())
        DBManager.withDB(this).withProperty().setProperty("time", System.currentTimeMillis().toString())
        Log.i(DBManager.withDB(this).withProperty().getProperty("time"))

        getToken()
        startNextActivity()
    }


    internal fun getToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this) { instanceIdResult ->
            val newToken = instanceIdResult.token
            Log.e("newToken", newToken)
        }
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    private fun startNextActivity() {
        val intent = Intent(pCon, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
        finish()
    }
}
