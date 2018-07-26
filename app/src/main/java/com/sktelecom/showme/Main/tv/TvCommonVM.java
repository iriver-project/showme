package com.sktelecom.showme.Main.tv;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.view.View;

import com.sktelecom.showme.base.Model.PBean;
import com.sktelecom.showme.base.Model.VoContents;
import com.sktelecom.showme.base.util.Log;
import com.sktelecom.showme.base.view.PFragment;
import com.sktelecom.showme.base.view.PViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TvCommonVM extends PViewModel {


    private String TAG = TvCommonVM.class.getSimpleName();
    private ICallback callback;

    public MutableLiveData fruitList;

    private String url;


    //dataSet
    public void setData(String url){
        this.url = url;
        //this.url = "https://storage.googleapis.com/ratpoisonfactory-147417.appspot.com/golden_gate_bridge_timelapse_hd_stock_video.mp4";
    }

    @NotNull
    @Override
    public PFragment asFragCreate() {
        frag = TvCommonFrag.with("title~", this, new TvCommonFrag.ICallbackEvent() {
            @Override
            public void onClick(VoContents vo) {

            }

            @Override
            public void getPage(int page) {

            }
        });

        //setData
        ((TvCommonFrag)frag).url = url;
        //((TvCommonFrag)frag).initPlayer();

        return frag;
    }


    public void setCallback(ICallback callback) {
        this.callback = callback;
    }

    public PFragment asFragResume() {
        if (frag == null) {
            this.asFragCreate();
        }
        return frag;
    }


    public LiveData<List<PBean>> getList() {
        fruitList = new MutableLiveData();
        if (callback != null) callback.callList();
        return fruitList;
    }


    public void onClickOne(View v, VoContents vo) {
        Log.INSTANCE.i("DUER", "here Touch!!!", vo.getCONTENTS_ID());
    }


    public interface ICallback {
        void callList();
    }
}