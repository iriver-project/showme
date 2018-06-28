package com.sktelecom.showme.Wallet;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.sktelecom.showme.R;
import com.sktelecom.showme.base.view.PActivity;
import com.sktelecom.showme.databinding.ActivityWalletBinding;

import org.jetbrains.annotations.Nullable;

public class WalletActivity extends PActivity {

    private WalletBodyCont mMainTabBodyCont;
    private WalletNavigatorCont mainTabNavigatorCont;
    private ActivityWalletBinding binded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binded = DataBindingUtil.setContentView(this, R.layout.activity_wallet);
//        setContentView(R.layout.activity_main_tab);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(Color.rgb(0, 105, 92));
//            getWindow().setNavigationBarColor(Color.rgb(77, 182, 172));
//        }

        mainTabNavigatorCont = new WalletNavigatorCont(this, position -> {
            if (mMainTabBodyCont != null) {
                mMainTabBodyCont.selected(position);
            }
        });

        mMainTabBodyCont = new WalletBodyCont(this, new WalletBodyCont.ICallbackToAct() {
            @Override
            public void selected(int position) {
                if (mainTabNavigatorCont != null) {
                    mainTabNavigatorCont.selected(position);
                }
            }

            @Override
            public void scrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mainTabNavigatorCont != null) {
                    mainTabNavigatorCont.scrolled(position, positionOffset, positionOffsetPixels);
                }
            }
        });

//        WalletCommonVM leftVm = ViewModelProviders.of(this).get(WalletCommonVM.class);

        pFragAdd(binded.frameTab.getId(), mainTabNavigatorCont.asFragCreate());
        pFragAdd(binded.frameBody.getId(), mMainTabBodyCont.asFragCreate());
    }


    @Override
    public void onAfaterCreate(@Nullable Bundle savedInstanceState) {

    }
}