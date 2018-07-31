package com.sktelecom.showme.selectlogin.register

import android.content.Context

import com.sktelecom.showme.base.view.PController
import com.sktelecom.showme.base.view.PFragment


class RegisterBodyCont(context: Context, mICallbackToAct: ICallbackToAct) : PController(context) {

    private val cb = CallbackEvent()
    private val mICallbackToAct: ICallbackToAct


    init {
        this.mICallbackToAct = mICallbackToAct
    }

    fun asFragCreate(): PFragment {
        frag = RegisterBodyFrag.with(cb)

        return frag as RegisterBodyFrag
    }

//    protected fun asFragResume(): PFragment {
//        if (frag == null) {
//            this.asFragCreate()
//        }
//        return frag!!.withView()
//    }


    fun selected(position: Int) {
        (frag as RegisterBodyFrag).selected(position)
    }

    protected inner class CallbackEvent : RegisterBodyFrag.ICallback {
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

    fun setUserImg(url:String){
        (frag as RegisterBodyFrag).setUserImg(url)
    }


    interface ICallbackToAct {
        fun selected(position: Int)

        fun scrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    }

}
