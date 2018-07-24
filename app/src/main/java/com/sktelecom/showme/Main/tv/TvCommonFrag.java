package com.sktelecom.showme.Main.tv;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sktelecom.showme.BR;
import com.sktelecom.showme.R;
import com.sktelecom.showme.base.Model.EmptyVo;
import com.sktelecom.showme.base.Model.PBean;
import com.sktelecom.showme.base.Model.VoContents;
import com.sktelecom.showme.base.util.Log;
import com.sktelecom.showme.base.view.PFragment;
import com.sktelecom.showme.databinding.CommonEmptyItemBinding;
import com.sktelecom.showme.databinding.CommonLoadingItemBinding;
import com.sktelecom.showme.databinding.TvCommonFragBinding;
import com.sktelecom.showme.databinding.WalletCommonFragBinding;
import com.sktelecom.showme.databinding.WalletItemBinding;

import java.util.ArrayList;
import java.util.List;

public class TvCommonFrag extends PFragment {
    private ICallbackEvent mICallbackEvent;
    private LinearLayoutManager mLinearLayoutManager;
    private List<PBean> list = new ArrayList<>();

    private String title;
    private TvCommonFragBinding binded;
    private TvCommonVM vm;

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
        binded.getViewmodel().getList().observe(this, pBeans -> {
            list = pBeans;

        });

        mLinearLayoutManager = new LinearLayoutManager(pCon);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.scrollToPosition(0);

        binded.tvTitle.setText(title);
    }

    @Override
    public void onCreated() {

    }

    protected interface ICallbackEvent {
        void onClick(VoContents vo);

        void getPage(int page);
    }

}