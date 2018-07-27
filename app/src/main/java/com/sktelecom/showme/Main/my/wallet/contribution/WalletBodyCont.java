package com.sktelecom.showme.Main.my.wallet.contribution;

import android.content.Context;

import com.sktelecom.showme.base.view.PController;
import com.sktelecom.showme.base.view.PFragment;


public class WalletBodyCont extends PController {
    protected WalletBodyFrag frag;
    private WalletBodyFrag.ICallback cb = new CallbackEvent();
    private ICallbackToAct mICallbackToAct = null;


    protected WalletBodyCont(Context context, ICallbackToAct mICallbackToAct) {
        super(context);
        this.mICallbackToAct = mICallbackToAct;
    }

    protected PFragment asFragCreate() {
        frag = WalletBodyFrag.with(cb);

        return frag;
    }

    protected PFragment asFragResume() {
        if (frag == null) {
            this.asFragCreate();
        }
        return frag.withView();
    }


    protected void selected(int position) {
        frag.selected(position);
    }

    protected class CallbackEvent implements WalletBodyFrag.ICallback {
        @Override
        public void init() {
//            if (frag != null) {
//                frag.setInitPosition(1);
//            }
        }

        @Override
        public void selected(int position) {
            mICallbackToAct.selected(position);
        }

        @Override
        public void scrolled(int position, float positionOffset, int positionOffsetPixels) {
            mICallbackToAct.scrolled(position, positionOffset, positionOffsetPixels);
        }

    }

   
    protected interface ICallbackToAct {
        void selected(int position);

        void scrolled(int position, float positionOffset, int positionOffsetPixels);
    }

}
