package com.sktelecom.showme.selectlogin.login

import android.arch.lifecycle.MutableLiveData
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.base.view.PViewModel

class LoginVM : PViewModel() {
    internal val TAG = LoginVM::class.java.simpleName
    internal lateinit var fruitList: MutableLiveData<List<PBean>>

    internal lateinit var frag1: LoginFirstFrag
    internal lateinit var frag2: LoginForgetFrag


    override fun asFragCreate(): PFragment {
        frag1 = LoginFirstFrag.with(this, mICallBack)
        frag2 = LoginForgetFrag.with(this, mICallBack2)
        return frag1
    }


    fun asFragResumeFirst(): PFragment {
//        if (frag1 == null)
//            frag1 = LoginFirstFrag.with(this, mICallBack)
        return frag1
    }

    fun asFragResumeSecond(): PFragment {
//        if (frag2 == null)
//            frag2 = RegisterPasswordFrag.with(this, mICallBack2)
        return frag2
    }


    fun onClickLogin() {
        Log.i("DUER", "here onClickLogin!!!");
        frag1.activity!!.finish()
    }


    internal val mICallBack = object : LoginFirstFrag.ICallbackEvent {
        override fun getPage(page: Int) {
        }
    }


    internal val mICallBack2 = object : LoginForgetFrag.ICallbackEvent {
        override fun getPage(page: Int) {
        }
    }

}