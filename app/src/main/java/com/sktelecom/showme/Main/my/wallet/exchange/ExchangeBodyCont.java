package com.sktelecom.showme.Main.my.wallet.exchange;

import android.content.Context;

import com.sktelecom.showme.base.view.PController;
import com.sktelecom.showme.base.view.PFragment;


public class ExchangeBodyCont extends PController {
    protected ExchangeBodyFrag frag;
    private ExchangeBodyFrag.ICallback cb = new CallbackEvent();
    private ICallbackToAct mICallbackToAct = null;


    protected ExchangeBodyCont(Context context, ICallbackToAct mICallbackToAct) {
        super(context);
        this.mICallbackToAct = mICallbackToAct;
    }

    protected PFragment asFragCreate() {
        frag = ExchangeBodyFrag.with(cb);

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

    protected class CallbackEvent implements ExchangeBodyFrag.ICallback {
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
