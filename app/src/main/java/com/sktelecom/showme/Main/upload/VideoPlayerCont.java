package com.sktelecom.showme.Main.upload;

import android.content.Context;
import android.os.Build;
import android.view.DragEvent;
import android.view.View;

import com.sktelecom.showme.base.Model.PBean;
import com.sktelecom.showme.base.view.PController;
import com.sktelecom.showme.base.view.PFragment;

/**
 * Created by white on 2017-09-29.
 */

public class VideoPlayerCont extends PController {

    protected VideoPlayerFrag frag;
    private VideoPlayerFrag.ICallback cb = new CallbackEvent();
    private ICallbackToAct mICallbackToAct = null;


    protected interface ICallbackToAct {
        void selected(int position);

        void scrolled(int position, float positionOffset, int positionOffsetPixels);

        void onLongClick(PBean one, View v, DragEvent event);

        void openWriteBoardAct(String localPicAddr, String coverFileAddr, Context context);

        void openCameraRecordAct(Context context);
    }


    protected class CallbackEvent implements VideoPlayerFrag.ICallback {
        @Override
        public void init() {
        }

        @Override
        public void selected(int position) {
            mICallbackToAct.selected(position);
        }

        @Override
        public void scrolled(int position, float positionOffset, int positionOffsetPixels) {
            mICallbackToAct.scrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onLongClick(PBean one, View v, DragEvent event) {
            mICallbackToAct.onLongClick(one, v, event);
        }

        @Override
        public void openWriteBoardAct(String localPicAddr, String coverFileAddr, Context context) {
            if (mICallbackToAct != null) {
                mICallbackToAct.openWriteBoardAct(localPicAddr, coverFileAddr, frag.getContext());
            }
        }

        @Override
        public void openCameraRecordAct(Context context) {
            if (mICallbackToAct != null) {
                mICallbackToAct.openCameraRecordAct(frag.getContext());
            }
        }
    }


    protected PFragment asFragCreate(String movAddr) {
        frag = VideoPlayerFrag.with(cb, movAddr);
        return frag;
    }

    public VideoPlayerCont(Context context, ICallbackToAct mICallbackToAct) {
        super(context);
        this.mICallbackToAct = mICallbackToAct;
    }

}
