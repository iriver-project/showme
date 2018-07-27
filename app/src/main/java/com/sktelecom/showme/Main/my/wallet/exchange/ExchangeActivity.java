package com.sktelecom.showme.Main.my.wallet.exchange;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.sktelecom.showme.Main.my.wallet.contribution.WalletBodyCont;
import com.sktelecom.showme.Main.my.wallet.contribution.WalletNavigatorCont;
import com.sktelecom.showme.R;
import com.sktelecom.showme.base.view.PActivity;
import com.sktelecom.showme.databinding.ActivityExchangeBinding;
import com.sktelecom.showme.databinding.ActivityWalletBinding;

import org.jetbrains.annotations.Nullable;

public class ExchangeActivity extends PActivity {

    private ExchangeBodyCont mMainTabBodyCont;
    private ExchangeNavigatorCont mainTabNavigatorCont;
    private ActivityExchangeBinding binded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binded = DataBindingUtil.setContentView(this, R.layout.activity_exchange);
//        setContentView(R.layout.activity_main_tab);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(Color.rgb(0, 105, 92));
//            getWindow().setNavigationBarColor(Color.rgb(77, 182, 172));
//        }

        mainTabNavigatorCont = new ExchangeNavigatorCont(this, position -> {
            if (mMainTabBodyCont != null) {
                mMainTabBodyCont.selected(position);
            }
        });

        mMainTabBodyCont = new ExchangeBodyCont(this, new ExchangeBodyCont.ICallbackToAct() {
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