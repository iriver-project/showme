package com.sktelecom.showme.base.push

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Handler
import android.os.Message
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast

import com.android.volley.VolleyError
import com.google.firebase.messaging.RemoteMessage
import com.sktelecom.showme.InitActivity
import com.sktelecom.showme.R
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.Network.SmartNetWork

import org.json.JSONException
import org.json.JSONObject


class FirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {


    private val message = ""

    @SuppressLint("HandlerLeak")
    private val updateHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                Toast.makeText(this@FirebaseMessagingService, message, Toast.LENGTH_LONG).show()
                this.sendEmptyMessageDelayed(999, 3000)
            } else if (msg.what == 1) {
                Toast.makeText(this@FirebaseMessagingService, message, Toast.LENGTH_LONG).show()

            } else if (msg.what == 999) {
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(0)
            }
        }
    }

    override fun onNewToken(s: String?) {
        super.onNewToken(s)
        Log.e("NEW_TOKEN", s)
        loginDuplicatedChecker(s)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val pushMessage = remoteMessage.data["push"]
        Toast.makeText(this, pushMessage, Toast.LENGTH_SHORT).show()
        Log.i("DUER_message", remoteMessage.data["push"])
        try {
            val pushObj = JSONObject(pushMessage)
            checkType(pushObj)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    private fun checkType(obj: JSONObject) {
        if (obj.optString("type") == "LO") {  //강제 로그아웃
            val id = obj.optString("id")
        }
    }


    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, InitActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                .setContentTitle("FCM Push Test")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }


    private fun loginDuplicatedChecker(toKen: String?) {

        val `object` = JSONObject()
        try {
            //            object.put("id", DBManger.withDB(this).withSQLProperty().getProperty(TbEntityProperty.USER_ID));
            `object`.put("vocaType", "GT")
            //            object.put("fcmToken", DBManger.withDB(this).withSQLProperty().getProperty(TbEntityProperty.FCM_TOKEN_KEY));

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val url = SmartNetWork.URL + "check_duplication_login_2"

        SmartNetWork().getCommonDataPostParamCookie(this, url, `object`, object : SmartNetWork.SmartNetWorkListener {
            override fun onResponse(Tag: Int, response: JSONObject) {

            }

            override fun onErrorResponse(Tag: Int, error: VolleyError) {

            }
        })
    }


    interface IFCMChatMessage {
        fun sendChat(vo: PBean)
    }

    companion object {

        private val TAG = "FirebaseMsgService"
    }

}

