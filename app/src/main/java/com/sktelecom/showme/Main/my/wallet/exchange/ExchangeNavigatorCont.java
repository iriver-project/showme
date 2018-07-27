package com.sktelecom.showme.Main.my.wallet.exchange;

import android.content.Context;

import com.sktelecom.showme.base.view.PController;
import com.sktelecom.showme.base.view.PFragment;

public class ExchangeNavigatorCont extends PController {
    private ExchangeNavigatorFrag.ICallback cb = new CallbackEvent();
    private ICallbackToAct mICallbackToActEvent = null;

    protected ExchangeNavigatorCont(Context context, ICallbackToAct mICallbackToActEvent) {
        super(context);
        this.mICallbackToActEvent = mICallbackToActEvent;
    }

    protected PFragment asFragCreate() {
        frag = ExchangeNavigatorFrag.with(cb);
        return frag;
    }

//    protected PFragment asFragResume() {
//        if (frag == null) {
//            this.asFragCreate();
//        }
//        return pOnResume(frag.withView());
//    }

    protected class CallbackEvent implements ExchangeNavigatorFrag.ICallback {
        @Override
        public void selected(int position) {
            mICallbackToActEvent.selected(position);
        }
    }


    protected void selected(int position) {
        if (frag != null)
            ((ExchangeNavigatorFrag) frag).selected(position);
    }

    protected void scrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (frag != null)
            ((ExchangeNavigatorFrag) frag).scrolled(position, positionOffset, positionOffsetPixels);
    }

    protected interface ICallbackToAct {
        void selected(int position);

    }

    protected void setBarColor(int color) {
        if (frag != null)
            ((ExchangeNavigatorFrag) frag).setBarColor(color);
    }
}
