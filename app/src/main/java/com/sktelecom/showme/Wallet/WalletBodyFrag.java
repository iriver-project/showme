package com.sktelecom.showme.Wallet;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sktelecom.showme.R;
import com.sktelecom.showme.base.view.PFragment;
import com.sktelecom.showme.databinding.WalletBodyFragBinding;

import org.ratpoisonfactory.base.util.Log;

public class WalletBodyFrag extends PFragment {
    private ICallback mICallback;
    private WalletLeftCont leftCont;
    private WalletRightCont rightCont;
    private WalletBodyFragBinding binded;

    protected static WalletBodyFrag with(ICallback mICallback) {
        WalletBodyFrag frag = new WalletBodyFrag();
        frag.setCallback(mICallback);
        return frag;
    }

    protected WalletBodyFrag withView() {
        return this;
    }


    private void setCallback(ICallback mICallback) {
        this.mICallback = mICallback;
    }

    @Override
    public View abCreateView(LayoutInflater inflater, ViewGroup container) {

        Log.INSTANCE.i("DUER", "abCreateView  !!!!!!!!");
        binded = DataBindingUtil.inflate(inflater, R.layout.wallet_body_frag, container, false);

        leftCont = new WalletLeftCont(getActivity(), this, null);
        rightCont = new WalletRightCont(getActivity(), this, null);


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


        leftCont.asViewModelCreate();
        rightCont.asViewModelCreate();
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
                    return leftCont.asViewModelResume().asFragCreate();
                case 1:
                    return rightCont.asViewModelResume().asFragResume();
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
