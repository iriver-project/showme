package com.sktelecom.showme.selectlogin

import android.content.Context

import com.sktelecom.showme.base.view.PController
import com.sktelecom.showme.base.view.PFragment


class SelectLoginBodyCont(context: Context, mICallbackToAct: ICallbackToAct) : PController(context) {

    private val cb = CallbackEvent()
    private val mICallbackToAct: ICallbackToAct


    init {
        this.mICallbackToAct = mICallbackToAct
    }

    fun asFragCreate(): PFragment {
        frag = SelectLoginBodyFrag.with(cb)

        return frag as SelectLoginBodyFrag
    }


    protected inner class CallbackEvent : SelectLoginBodyFrag.ICallback {
        override fun init() {
            //            if (frag != null) {
            //                frag.setInitPosition(1);
            //            }
        }

        override fun selected(position: Int) {
            mICallbackToAct.selected(position)
        }

        override fun scrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            mICallbackToAct.scrolled(position, positionOffset, positionOffsetPixels)
        }

    }


    interface ICallbackToAct {
        fun selected(position: Int)

        fun scrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    }

}
