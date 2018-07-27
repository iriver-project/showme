package com.sktelecom.showme.Main.my.wallet.exchange;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sktelecom.showme.R;
import com.sktelecom.showme.base.util.Log;
import com.sktelecom.showme.base.view.PFragment;
import com.sktelecom.showme.databinding.ExchangeNavigationFragBinding;
import com.sktelecom.showme.databinding.WalletNavigationFragBinding;


public class ExchangeNavigatorFrag extends PFragment implements View.OnClickListener {
    private final String TAG = "ExchangeNavigatorFrag" + ":" + "J.K.S";
    private ICallback mICallback;
    private float singleMarginLeftSize = 0;
    private int currentMargenLeftSize = 0;
    private RelativeLayout.LayoutParams llIndicaterParam;
    private ExchangeNavigationFragBinding binded;

    protected static ExchangeNavigatorFrag with(ICallback mICallback) {
        ExchangeNavigatorFrag frag = new ExchangeNavigatorFrag();
        frag.setCallback(mICallback);
        return frag;
    }

    protected ExchangeNavigatorFrag withView() {
        return this;
    }

    private void setCallback(ICallback mICallback) {
        this.mICallback = mICallback;
    }

    @Override
    public View abCreateView(@NonNull LayoutInflater inflater, ViewGroup container) {
        binded = DataBindingUtil.inflate(inflater, R.layout.exchange_navigation_frag, container, false);


        DisplayMetrics display = pCon.getResources().getDisplayMetrics();
        singleMarginLeftSize = (float) display.widthPixels / 2;
        llIndicaterParam = (RelativeLayout.LayoutParams) binded.llIndicater.getLayoutParams();
        llIndicaterParam.width = (int) singleMarginLeftSize;
        binded.llIndicater.setLayoutParams(llIndicaterParam);

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

    protected void selected(int position) {
        Log.INSTANCE.i("position", position);

    }

    protected void scrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.i("DUER", position + " " + positionOffset + " " + positionOffsetPixels);
        if (positionOffset < 1 && positionOffset > 0) {
            llIndicaterParam = (RelativeLayout.LayoutParams) binded.llIndicater.getLayoutParams();
            currentMargenLeftSize = ((int) (singleMarginLeftSize * positionOffset)) + ((int) (position * singleMarginLeftSize));
            llIndicaterParam.leftMargin = currentMargenLeftSize;
            binded.llIndicater.setLayoutParams(llIndicaterParam);
        }
        if (positionOffset == 0 && positionOffsetPixels == 0)
            selectedImage(position);
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
