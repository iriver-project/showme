package com.sktelecom.showme.Main.upload;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import com.sktelecom.showme.R;
import com.sktelecom.showme.base.Model.PBean;
import com.sktelecom.showme.base.view.PActivity;

/**
 * Created by white on 2017-09-17.
 */

public class VideoPlayerActivity extends PActivity {

    public static String ACT_TO_ACT_INNER_MOV_ADDR = "INNER_MOV_ADDR";

    private VideoPlayerCont mVideoPlayerCont;
    private String movAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        // 녹화된 동영상의 주소 취득.
        Bundle extras = getIntent().getExtras();
        movAddr = extras.getString(ACT_TO_ACT_INNER_MOV_ADDR);

        // if its not null do something with it
        if (movAddr == null || movAddr.equals("")) {
            Log.i("DUER", " 없네요 movAddr");
        }

        mVideoPlayerCont = new VideoPlayerCont(this, new VideoPlayerCont.ICallbackToAct() {
            @Override
            public void selected(int position) {
            }

            @Override
            public void scrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onLongClick(PBean one, View v, DragEvent event) {
            }

            @Override
            public void openWriteBoardAct(String localPicAddr, String coverPicAddr, Context context) {
            }

            @Override
            public void openCameraRecordAct(Context context) {
            }
        });


        pFragAdd(R.id.player_view, mVideoPlayerCont.asFragCreate(movAddr));

    }


    @Override
    public void onAfaterCreate(@Nullable Bundle savedInstanceState) {

    }
}