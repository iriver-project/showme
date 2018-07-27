package com.sktelecom.showme.Main.common;

import android.content.Context;

import com.sktelecom.showme.base.view.PController;
import com.sktelecom.showme.base.view.PFragment;


public class CommonProfileTopCont extends PController {
    protected CommonProfileTopFrag frag;
    private CommonProfileTopFrag.ICallback cb = new CallbackEvent();
    private ICallbackToAct mICallbackToAct = null;


    protected CommonProfileTopCont(Context context, ICallbackToAct mICallbackToAct) {
        super(context);
        this.mICallbackToAct = mICallbackToAct;
    }

    protected PFragment asFragCreate() {
        frag = CommonProfileTopFrag.with(cb);

        return frag;
    }

    protected PFragment asFragResume() {
        if (frag == null) {
            this.asFragCreate();
        }
        return frag.withView();
    }


    protected class CallbackEvent implements CommonProfileTopFrag.ICallback {


        @Override
        public void selected(int position) {
            mICallbackToAct.selected(position);
        }


    }


    protected interface ICallbackToAct {
        void selected(int position);

        void scrolled(int position, float positionOffset, int positionOffsetPixels);
    }

}
