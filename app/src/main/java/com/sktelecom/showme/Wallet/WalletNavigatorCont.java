package com.sktelecom.showme.Wallet;

import android.content.Context;

import com.sktelecom.showme.base.view.PController;
import com.sktelecom.showme.base.view.PFragment;

public class WalletNavigatorCont extends PController {
    private WalletNavigatorFrag.ICallback cb = new CallbackEvent();
    private ICallbackToAct mICallbackToActEvent = null;

    protected WalletNavigatorCont(Context context, ICallbackToAct mICallbackToActEvent) {
        super(context);
        this.mICallbackToActEvent = mICallbackToActEvent;
    }

    protected PFragment asFragCreate() {
        frag = WalletNavigatorFrag.with(cb);
        return frag;
    }

//    protected PFragment asFragResume() {
//        if (frag == null) {
//            this.asFragCreate();
//        }
//        return pOnResume(frag.withView());
//    }

    protected class CallbackEvent implements WalletNavigatorFrag.ICallback {
        @Override
        public void selected(int position) {
            mICallbackToActEvent.selected(position);
        }
    }


    protected void selected(int position) {
        if (frag != null)
            ((WalletNavigatorFrag) frag).selected(position);
    }

    protected void scrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (frag != null)
            ((WalletNavigatorFrag) frag).scrolled(position, positionOffset, positionOffsetPixels);
    }

    protected interface ICallbackToAct {
        void selected(int position);

    }

    protected void setBarColor(int color) {
        if (frag != null)
            ((WalletNavigatorFrag) frag).setBarColor(color);
    }
}
