package com.sktelecom.showme.base.push;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.firebase.messaging.RemoteMessage;
import com.sktelecom.showme.InitActivity;
import com.sktelecom.showme.R;
import com.sktelecom.showme.base.Model.PBean;
import com.sktelecom.showme.base.Network.SmartNetWork;

import org.json.JSONException;
import org.json.JSONObject;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "FirebaseMsgService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN", s);
        loginDuplicatedChecker(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String pushMessage = remoteMessage.getData().get("push");
        Toast.makeText(this, pushMessage, Toast.LENGTH_SHORT).show();
        Log.i("DUER_message", remoteMessage.getData().get("push"));
        try {
            JSONObject pushObj = new JSONObject(pushMessage);
            checkType(pushObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private String message = "";

    private void checkType(JSONObject obj) {
        if (obj.optString("type").equals("LO")) {  //강제 로그아웃
            String id = obj.optString("id");
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Toast.makeText(FirebaseMessagingService.this, message, Toast.LENGTH_LONG).show();
                updateHandler.sendEmptyMessageDelayed(999, 3000);
            } else if (msg.what == 1) {
                Toast.makeText(FirebaseMessagingService.this, message, Toast.LENGTH_LONG).show();

            } else if (msg.what == 999) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        }
    };


    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, InitActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                .setContentTitle("FCM Push Test")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


    private void loginDuplicatedChecker(String toKen) {

        JSONObject object = new JSONObject();
        try {
//            object.put("id", DBManger.withDB(this).withSQLProperty().getProperty(TbEntityProperty.USER_ID));
            object.put("vocaType", "GT");
//            object.put("fcmToken", DBManger.withDB(this).withSQLProperty().getProperty(TbEntityProperty.FCM_TOKEN_KEY));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = SmartNetWork.Companion.getURL() + "check_duplication_login_2";

        new SmartNetWork().getCommonDataPostParamCookie(this, url, object, new SmartNetWork.SmartNetWorkListener() {
            @Override
            public void onResponse(int Tag, JSONObject response) {

            }

            @Override
            public void onErrorResponse(int Tag, VolleyError error) {

            }
        });
    }


    public interface IFCMChatMessage {
        void sendChat(PBean vo);
    }

}

