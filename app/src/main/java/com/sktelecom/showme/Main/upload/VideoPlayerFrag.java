package com.sktelecom.showme.Main.upload;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.sktelecom.showme.R;
import com.sktelecom.showme.base.Model.PBean;
import com.sktelecom.showme.base.util.CommonUtil;
import com.sktelecom.showme.base.util.Log;
import com.sktelecom.showme.base.view.PFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class VideoPlayerFrag extends PFragment {

    private View root;
//TODO : replace it to PlayerView
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;

    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;
    private Button btn_upload;
    private String movAddr;
    private int duration = 0; //동영상 파일의 시간(초)
    private String tempImg;

    private ImageView iv_coverMain;
    private LinearLayout llInPhotoList;
    //private ImageView ivHideControllerButton;

    private ICallback mICallback;

    private Thread makePicThread;
    private int ADD_PIC = 1;

    public static VideoPlayerFrag with(ICallback mICallback, String movAddr) {
        VideoPlayerFrag frag = new VideoPlayerFrag();
        frag.setCallback(mICallback);
        frag.setMovAddr(movAddr);
        return frag;
    }

    private void setCallback(ICallback mICallback) {
        this.mICallback = mICallback;
    }

    private void setMovAddr(String movAddr) {
        this.movAddr = movAddr;
    }

    protected String suffix(int serial) {
        String suffix = String.format("%04d", serial);
        return suffix;
    }

    @Override
    public void onCreated() {

    }

    protected interface ICallback {
        void init();

        void selected(int position);

        void scrolled(int position, float positionOffset, int positionOffsetPixels);

        void onLongClick(PBean one, View v, DragEvent event);

        void openWriteBoardAct(String localPicAddr, String coverFileAddr, Context context);

        void openCameraRecordAct(Context context);

    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);

        window = new Timeline.Window();

        iv_coverMain = (ImageView) root.findViewById(R.id.iv_coverMain);
        iv_coverMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_coverMain.setVisibility(View.INVISIBLE);
                simpleExoPlayerView.setVisibility(View.VISIBLE);
                initializePlayer();
            }
        });

        makePics();
    }

    private void initializePlayer() {

        Log.INSTANCE.i("DUAL", "initializePlayer movAddr", movAddr);

        btn_upload = (Button) root.findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.INSTANCE.i("DUAL", "onClick upload");
                if (mICallback != null) {
                    mICallback.openWriteBoardAct(movAddr, HSViewRelativeLayout.SELECTED_FILE_NAME, getContext());
                }
            }
        });

        simpleExoPlayerView = (SimpleExoPlayerView) root.findViewById(R.id.player_view);
        //ivHideControllerButton = (ImageView) root.findViewById(R.id.exo_controller);
        simpleExoPlayerView.requestFocus();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        simpleExoPlayerView.setPlayer(player);
        player.setPlayWhenReady(false);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

//        MediaSource mediaSource = new HlsMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"),
//                mediaDataSourceFactory, mainHandler, null);
//
//        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"),
//                mediaDataSourceFactory, extractorsFactory, null, null);

        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(new File(movAddr).toString()),
                mediaDataSourceFactory, extractorsFactory, null, null);

        player.prepare(mediaSource);

//        ivHideControllerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                simpleExoPlayerView.hideController();
//            }
//        });
    }

    @SuppressLint("HandlerLeak")
    private android.os.Handler updatedeHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == ADD_PIC) {
                //addLayoutA(coverFileNames);

                // 커버 사진으로 추출된 이미지를 뷰에 추가.
                addLayoutA(tempImg);
            }
        }
    };

    //쓰래드 처리 할 것.
    public void makePics() {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

        try {
            mediaMetadataRetriever.setDataSource(movAddr);

            String sDuration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            duration = Integer.parseInt(sDuration) / 1000;
            Log.INSTANCE.i("video duration =" + duration + " Second"); //리턴 : 3000 -> 3초

            if (duration > 0) {
                llInPhotoList.removeAllViews();

                makePicThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                            mediaMetadataRetriever.setDataSource(movAddr);

                            String gifFileName = "";
                            Bitmap bitmap;

                            for (int i = 0; i <= duration; i++) {
                                gifFileName = System.currentTimeMillis() + "_COVER_";
                                gifFileName = gifFileName + suffix(i);
                                bitmap = mediaMetadataRetriever.getFrameAtTime(i * 1000000);//영상 추출

                                if (bitmap != null) {
                                    OutputStream output = new FileOutputStream(new File(CommonUtil.Companion.getWith().getTempMediaPath() + gifFileName + ".jpg"));
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, output);
                                    //coverFileNames.add(CommonUtil.with().getTempMediaPath() + gifFileName + ".jpg");
                                    tempImg = CommonUtil.Companion.getWith().getTempMediaPath() + gifFileName + ".jpg";

                                    Message msg = updatedeHandler.obtainMessage();
                                    msg.what = ADD_PIC;
                                    updatedeHandler.sendMessage(msg);
                                } else {
                                    Log.INSTANCE.i("!!! bitmap is NULL !!!!");
                                }

//                                if (i == duration) {
//                                    Message msg = updatedeHandler.obtainMessage();
//                                    msg.what = 1;
//                                    updatedeHandler.sendMessage(msg);
//
//                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                makePicThread.start();
                //addLayoutA(coverFileNames);
            } else {
                Log.INSTANCE.i("File duration is 0 Second");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.INSTANCE.i("makePics() Error = " + e.toString());
        }
    }

    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }


    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public View abCreateView(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_video_player, container, false);

        llInPhotoList = (LinearLayout) root.findViewById(R.id.llInPhotoList);
        return root;
    }

    protected void addLayoutA(ArrayList<String> imgAddr) {
        llInPhotoList.removeAllViews();
        for (String temp : imgAddr) {
            Log.INSTANCE.i("DUER", temp);
            llInPhotoList.addView(new HSViewRelativeLayout(pCon, simpleExoPlayerView, player, iv_coverMain, temp));
        }
    }

    protected void addLayoutA(String imgAddr) {
        //llInPhotoList.removeAllViews();
        llInPhotoList.addView(new HSViewRelativeLayout(pCon, simpleExoPlayerView, player, iv_coverMain, imgAddr));
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
}
