package com.sktelecom.showme.Wallet.CommonView;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.view.View;

import com.sktelecom.showme.base.Model.PBean;
import com.sktelecom.showme.base.Model.VoContents;
import com.sktelecom.showme.Wallet.CommonView.WalletCommonFrag.ICallbackEvent;
import com.sktelecom.showme.base.util.Log;
import com.sktelecom.showme.base.view.PFragment;
import com.sktelecom.showme.base.view.PViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WalletCommonVM extends PViewModel {


    private String TAG = WalletCommonVM.class.getSimpleName();
    private ICallback callback;

    public MutableLiveData fruitList;


    @NotNull
    @Override
    public PFragment asFragCreate() {
        frag = WalletCommonFrag.with("title~", this, new ICallbackEvent() {
            @Override
            public void onClick(VoContents vo) {

            }

            @Override
            public void getPage(int page) {

            }
        });
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