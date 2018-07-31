package com.sktelecom.showme.selectlogin.register

import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.base.view.PViewModel

class RegisterVM : PViewModel() {
    internal val TAG = RegisterVM::class.java.simpleName
    internal lateinit var fruitList: MutableLiveData<List<PBean>>

    internal lateinit var frag1: RegisterEmailFrag
    internal lateinit var frag2: RegisterPasswordFrag
    internal lateinit var frag3: RegisterUserInfoFrag


    override fun asFragCreate(): PFragment {
        frag1 = RegisterEmailFrag.with(this, mICallBack)
        frag2 = RegisterPasswordFrag.with(this, mICallBack2)
        frag3 = RegisterUserInfoFrag.with(this, mICallBack3)
        return frag1
    }


    fun asFragResumeEmail(): PFragment {
//        if (frag1 == null)
//            frag1 = RegisterEmailFrag.with(this, mICallBack)
        return frag1
    }

    fun asFragResumePassword(): PFragment {
//        if (frag2 == null)
//            frag2 = RegisterPasswordFrag.with(this, mICallBack2)
        return frag2
    }


    fun asFragResumeUserInfo(): PFragment {
//        if (frag3 == null)
//            frag3 = RegisterUserInfoFrag.with(this, mICallBack3)
        return frag3
    }


    fun onClickImage() {
        Log.i("DUER", "here onClickImage!!!");
        if (!(frag3.activity as PActivity).checkReadExternalStoragePermission()) {
            return
        }
        frag3.activity!!.startActivityForResult(Intent.createChooser(Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), "Choose an image"), 100)
    }

    fun setUserImg(url :String){
        frag3.setUserImg(url)
    }


    internal val mICallBack = object : RegisterEmailFrag.ICallbackEvent {
        override fun getPage(page: Int) {
        }
    }


    internal val mICallBack2 = object : RegisterPasswordFrag.ICallbackEvent {
        override fun getPage(page: Int) {
        }
    }

    internal val mICallBack3 = object : RegisterUserInfoFrag.ICallbackEvent {
        override fun getPage(page: Int) {
        }
    }
}