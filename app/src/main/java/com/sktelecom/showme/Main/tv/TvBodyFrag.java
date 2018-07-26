package com.sktelecom.showme.Main.tv;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sktelecom.showme.R;
import com.sktelecom.showme.base.util.Log;
import com.sktelecom.showme.base.view.PFragment;
import com.sktelecom.showme.base.view.animation.CubeTransformer;
import com.sktelecom.showme.databinding.TvBodyFragBinding;

public class TvBodyFrag extends PFragment {
    private ICallback mICallback;

    private TvBodyFragBinding binded;
    private TvCommonVM p1;
    private TvCommonVM p2;
    private TvCommonVM p3;
    private TvCommonVM p4;
    private TvCommonVM p5;


    protected static TvBodyFrag with(ICallback mICallback) {
        TvBodyFrag frag = new TvBodyFrag();
        frag.setCallback(mICallback);
        return frag;
    }

    protected TvBodyFrag withView() {
        return this;
    }


    private void setCallback(ICallback mICallback) {
        this.mICallback = mICallback;
    }

    @Override
    public View abCreateView(LayoutInflater inflater, ViewGroup container) {

        Log.INSTANCE.i("DUER", "abCreateView  !!!!!!!!");
        binded = DataBindingUtil.inflate(inflater, R.layout.tv_body_frag, container, false);


        return binded.getRoot();
    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();

//    }

    @Override
    public void onDestroyView() {

        if (binded != null) {
            ViewGroup parentViewGroup = (ViewGroup) binded.getRoot().getParent();

            if (null != parentViewGroup) {
                parentViewGroup.removeView(binded.getRoot());
            }
        }
        FragmentManager mFragmentMgr = getFragmentManager();
        FragmentTransaction mTransaction = mFragmentMgr.beginTransaction();
        mTransaction.remove(p1.frag);
        mTransaction.remove(p2.frag);
        mTransaction.remove(p3.frag);
        mTransaction.remove(p4.frag);
        mTransaction.remove(p5.frag);
        mTransaction.commitAllowingStateLoss();
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCreated() {
        Log.INSTANCE.i("DUER", "onCreated  !!!!!!!!");

        binded.pager.setPageTransformer(true, new CubeTransformer());

        if (p1 == null) {

            p1 = ViewModelProviders.of(this).get("p1", TvCommonVM.class);
            p2 = ViewModelProviders.of(this).get("p2", TvCommonVM.class);
            p3 = ViewModelProviders.of(this).get("p3", TvCommonVM.class);
            p4 = ViewModelProviders.of(this).get("p4", TvCommonVM.class);
            p5 = ViewModelProviders.of(this).get("p5", TvCommonVM.class);

            Log.INSTANCE.i("DUER", "onCreated");

            p1.setData("https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_16x9/bipbop_16x9_variant.m3u8");
            p2.setData("http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8");
            p3.setData("https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8");
            p4.setData("https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8");
            p5.setData("http://www.streambox.fr/playlists/test_001/stream.m3u8");

            p1.asFragCreate();
            p2.asFragCreate();
            p3.asFragCreate();
            p4.asFragCreate();
            p5.asFragCreate();

        }
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

        if (mICallback != null)
            mICallback.init();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class ViewIconPagerAdapter extends FragmentPagerAdapter {

        private ViewIconPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public PFragment getItem(int num) {
            switch (num) {
                case 0:
                    //return p1.asFragResume();
                    return p1.asFragResume();
                case 1:
                    //return p2.asFragResume();
                    return p2.asFragResume();
                case 2:
                    //return p3.asFragResume();
                    return p3.asFragResume();
                case 3:
                    //return p4.asFragResume();
                    return p4.asFragResume();
                case 4:
                    //return p5.asFragResume();
                    return p5.asFragResume();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
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
