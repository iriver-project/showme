package com.sktelecom.showme.Main.common;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sktelecom.showme.R;
import com.sktelecom.showme.base.view.PFragment;
import com.sktelecom.showme.databinding.CommonProfileTopFragBinding;


public class CommonProfileTopFrag extends PFragment implements View.OnClickListener {
    private final String TAG = "CommonProfileTopFrag" + ":" + "J.K.S";
    private ICallback mICallback;
    private RelativeLayout.LayoutParams llIndicaterParam;
    private CommonProfileTopFragBinding binded;
    private CommonProfileTopCont cont;

    protected static CommonProfileTopFrag with(CommonProfileTopCont cont, ICallback mICallback) {
        CommonProfileTopFrag frag = new CommonProfileTopFrag();
        frag.setCallback(cont, mICallback);
        return frag;
    }

    protected CommonProfileTopFrag withView() {
        return this;
    }

    private void setCallback(CommonProfileTopCont cont, ICallback mICallback) {
        this.mICallback = mICallback;
        this.cont = cont;
    }

    @Override
    public View abCreateView(@NonNull LayoutInflater inflater, ViewGroup container) {
        binded = DataBindingUtil.inflate(inflater, R.layout.common_profile_top_frag, container, false);
        binded.setControl(cont);
        return binded.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binded != null) {
            ViewGroup parentViewGroup = (ViewGroup) binded.getRoot().getParent();

            if (null != parentViewGroup) {
                parentViewGroup.removeView(binded.getRoot());
            }
        }
    }


    private void selectedImage(int position) {

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.rlLeft) {
            this.mICallback.selected(0);
        } else if (v.getId() == R.id.rlRight) {
            this.mICallback.selected(1);
        }

    }


    protected void setBarColor(int color) {
        binded.rlMainNavi.setBackgroundColor(color);
    }

    @Override
    public void onCreated() {

    }

    public interface ICallback {
        void selected(int position);
    }
}
