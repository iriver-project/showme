package com.sktelecom.showme.Main.upload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.sktelecom.showme.R;

import java.io.File;

//import com.yalantis.ucrop.UCrop;

public class HSViewRelativeLayout extends RelativeLayout {

    private LayoutInflater mInflater;

    private ImageView ivImg1, ivCrop, iv_coverMain;
    private Context context;
    private String imgAddr;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;

    public static String SELECTED_FILE_NAME = "";

    public HSViewRelativeLayout(Context context, SimpleExoPlayerView simpleExoPlayerView,
                                SimpleExoPlayer player, ImageView iv_coverMain, String imgAddr) {
        super(context);
        this.context = context;
        this.imgAddr = imgAddr;
        this.simpleExoPlayerView = simpleExoPlayerView;
        this.player = player;
        this.iv_coverMain = iv_coverMain;

        mInflater = LayoutInflater.from(context);
        init();
    }

    public HSViewRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mInflater = LayoutInflater.from(context);
        init();
    }

    public void init() {
        View v = mInflater.inflate(R.layout.photo_a_relative_layout, this, true);
        ivImg1 = (ImageView) v.findViewById(R.id.ivImg1);
        ivCrop = (ImageView) v.findViewById(R.id.ivCrop);
        ivCrop.setVisibility(GONE);
        Glide.with(context).load(imgAddr).into(ivImg1);

        // 이미지 클릭시 메인 이미지 뷰어 창의 이미지 교체
        ivImg1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (iv_coverMain != null) {

                    File imgFile = new File(imgAddr);

                    if (imgFile.exists()) {
                        player.stop();
                        //player.release();
                        simpleExoPlayerView.setVisibility(INVISIBLE);
                        iv_coverMain.setVisibility(VISIBLE);
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        iv_coverMain.setImageBitmap(myBitmap);
                        SELECTED_FILE_NAME = imgAddr;
                    }
                }
            }
        });
    }

}