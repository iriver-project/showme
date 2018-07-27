package com.sktelecom.showme.Main.my.wallet.exchange;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sktelecom.showme.Main.my.wallet.contribution.Left.WalletLeftCont;
import com.sktelecom.showme.Main.my.wallet.contribution.Right.WalletRightCont;
import com.sktelecom.showme.Main.my.wallet.exchange.left.TokenExchangeVM;
import com.sktelecom.showme.R;
import com.sktelecom.showme.base.util.Log;
import com.sktelecom.showme.base.view.PFragment;
import com.sktelecom.showme.databinding.ExchangeBodyFragBinding;


public class ExchangeBodyFrag extends PFragment {
    private ICallback mICallback;
    private TokenExchangeVM leftCont;
    private TokenExchangeVM rightCont;
    private ExchangeBodyFragBinding binded;

    protected static ExchangeBodyFrag with(ICallback mICallback) {
        ExchangeBodyFrag frag = new ExchangeBodyFrag();
        frag.setCallback(mICallback);
        return frag;
    }

    protected ExchangeBodyFrag withView() {
        return this;
    }


    private void setCallback(ICallback mICallback) {
        this.mICallback = mICallback;
    }

    @Override
    public View abCreateView(LayoutInflater inflater, ViewGroup container) {

        Log.INSTANCE.i("DUER", "abCreateView  !!!!!!!!");
        binded = DataBindingUtil.inflate(inflater, R.layout.exchange_body_frag, container, false);
//        vm = ViewModelProviders.of(motherfrag).get("LEFT", WalletCommonVM.class);
        leftCont = ViewModelProviders.of(this).get("LEFT", TokenExchangeVM.class);
        rightCont = ViewModelProviders.of(this).get("RIGHT", TokenExchangeVM.class);

//        rightCont = new WalletRightCont(getActivity(), this, null);


        ViewIconPagerAdapter viewadapter = new ViewIconPagerAdapter(getFragmentManager());
        binded.pager.setAdapter(viewadapter);
        binded.pager.setOffscreenPageLimit(1);
        binded.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i("positon " + position + " " + positionOffset + " " + positionOffsetPixels);
                if (mICallback != null)
                    mICallback.scrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.INSTANCE.i("DUER", position);
                if (mICallback != null)
                    mICallback.selected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        leftCont.asFragCreate();
        rightCont.asFragCreate();
//        rightVm.asFragCreate();

        if (mICallback != null)
            mICallback.init();

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


    protected void setInitPosition(int position) {
        binded.pager.setCurrentItem(position);
        if (mICallback != null)
            mICallback.selected(position);
    }

    @Override
    public void onCreated() {

    }


    private class ViewIconPagerAdapter extends FragmentPagerAdapter {

        private ViewIconPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public PFragment getItem(int num) {
            switch (num) {
                case 0:
                    return leftCont.asFragResume();
                case 1:
                    return rightCont.asFragResume();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

    }


    protected interface ICallback {
        void init();

        void selected(int position);

        void scrolled(int position, float positionOffset, int positionOffsetPixels);
    }

    protected void selected(int postion) {

        binded.pager.setCurrentItem(postion, true);

    }


}
