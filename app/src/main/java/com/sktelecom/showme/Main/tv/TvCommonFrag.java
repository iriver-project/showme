package com.sktelecom.showme.Main.tv;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.sktelecom.showme.R;
import com.sktelecom.showme.base.Model.PBean;
import com.sktelecom.showme.base.Model.VoContents;
import com.sktelecom.showme.base.util.Log;
import com.sktelecom.showme.base.view.PFragment;
import com.sktelecom.showme.databinding.TvCommonFragBinding;

import java.util.ArrayList;
import java.util.List;

public class TvCommonFrag extends PFragment {
    private ICallbackEvent mICallbackEvent;
    private LinearLayoutManager mLinearLayoutManager;
    private List<PBean> list = new ArrayList<>();

    private String title;
    private TvCommonFragBinding binded;
    private TvCommonVM vm;


    //exoplayer
    public String url;

    private ExoPlayer player;


    protected static TvCommonFrag with(String title, TvCommonVM vm, ICallbackEvent mICallbackEvent) {
        TvCommonFrag frag = new TvCommonFrag();
        frag.setCallback(title, vm, mICallbackEvent);
        return frag;
    }


    protected TvCommonFrag withView() {
        return this;
    }

    public void setCallback(String title, TvCommonVM vm, ICallbackEvent mICallbackEvent) {
        this.mICallbackEvent = mICallbackEvent;
        this.vm = vm;
        this.title = title;
    }

    @Override
    public View abCreateView(LayoutInflater inflater, ViewGroup container) {
        binded = DataBindingUtil.inflate(inflater, R.layout.tv_common_frag, container, false);
        binded.setViewmodel(vm);

        Log.INSTANCE.i("abCreateView");


        return binded.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.INSTANCE.e("minus", "onStart" + title);

        binded.getViewmodel().getList().observe(this, pBeans -> {
            list = pBeans;
            //list created

        });

        mLinearLayoutManager = new LinearLayoutManager(pCon);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.scrollToPosition(0);

        //binded.tvTitle.setText(title);

        if(player!=null)
            player.setPlayWhenReady(true);
    }

    @Override
    public void onCreated() {
        initPlayer();
    }

    public void initPlayer(){
        boolean needNewPlayer = player == null;
        Log.INSTANCE.e("minus", "initExo" + url);
        if (needNewPlayer) {
            DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
            TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            TrackSelector trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);

            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            binded.playerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
                @Override
                public void onVisibilityChange(int visibility) {
                    if(player!=null){
                        if(visibility==View.VISIBLE) {
                            //player.setPlayWhenReady(false);
                        }
                        else{
                            //player.setPlayWhenReady(true);
                        }
                    }
                }
            });

            player.addListener(new Player.EventListener() {

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    //add loading view... gu cha na
                    if (playbackState == Player.STATE_BUFFERING){
                        //progressBar.setVisibility(View.VISIBLE);
                    } else {
                        //progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    player.stop();
                    //Logger.d(TAG, ""+error.getCause().getMessage());
                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            });

            //player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            //simpleExoPlayerView.setControllerVisibilityListener(this);
            binded.playerView.setUseController(true);
            binded.playerView.requestFocus();
            binded.playerView.setPlayer(player);

            binded.playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

            //exoVideoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            //exoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

            //prepare
            DefaultHttpDataSourceFactory DATA_SOURCE_FACTORY = new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER);
            HlsMediaSource videoSource = new HlsMediaSource.Factory(DATA_SOURCE_FACTORY).createMediaSource(Uri.parse(url));

            player.prepare(videoSource);
        }
    }
    private void releasePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.release();
            player = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.INSTANCE.e("minus", "onResume" + title);
        initPlayer();
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        Log.INSTANCE.e("minus", "onDes" + title);
        super.onDestroy();
    }

    @Override
    public void onStop() {
        Log.INSTANCE.e("minus", "onStop" + title);
        super.onStop();
    }

    @Override
    public void onPause() {
        Log.INSTANCE.e("minus", "onPause" + title);
        releasePlayer();
        super.onPause();
    }

    protected interface ICallbackEvent {
        void onClick(VoContents vo);

        void getPage(int page);
    }

}