package com.sktelecom.showme.Main.common;

import android.content.Context;
import android.content.Intent;

import com.sktelecom.showme.base.view.PController;
import com.sktelecom.showme.base.view.PFragment;
import com.sktelecom.showme.setting.SettingActivity;


public class CommonProfileTopCont extends PController {
    protected CommonProfileTopFrag frag;
    private CommonProfileTopFrag.ICallback cb = new CallbackEvent();
    private ICallbackToAct mICallbackToAct = null;


    protected CommonProfileTopCont(Context context, ICallbackToAct mICallbackToAct) {
        super(context);
        this.mICallbackToAct = mICallbackToAct;
    }

    protected PFragment asFragCreate() {
        frag = CommonProfileTopFrag.with(this, cb);

        return frag;
    }

    protected PFragment asFragResume() {
        if (frag == null) {
            this.asFragCreate();
        }
        return frag.withView();
    }


    public void onClickSetting() {
        Intent intent = new Intent(frag.pActivity, SettingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        frag.pActivity.startActivityForResult(intent, 1);
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
