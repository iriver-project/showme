package com.sktelecom.showme.selectlogin

import android.content.Intent
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sktelecom.showme.R
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.databinding.SelectLoginBodyFragBinding
import com.sktelecom.showme.selectlogin.login.LoginActivity
import com.sktelecom.showme.selectlogin.register.RegisterActivity


class SelectLoginBodyFrag : PFragment() {
    internal lateinit var mICallback: ICallback
    internal lateinit var binded: SelectLoginBodyFragBinding

    fun withView(): SelectLoginBodyFrag {
        return this
    }


    internal fun setCallback(mICallback: ICallback) {
        this.mICallback = mICallback
    }

    override fun abCreateView(inflater: LayoutInflater, container: ViewGroup?): View {

        Log.i("DUER", "abCreateView  ")
        binded = DataBindingUtil.inflate(inflater, R.layout.select_login_body_frag, container, false)
        //


        binded.rlLogin.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            activity!!.startActivityForResult(intent, 3)
            Log.i("DUER", "here         binded.rlLogin.setOnClickListener {\n!!!");
        }

        binded.rlReg.setOnClickListener {
            val intent = Intent(activity, RegisterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            activity!!.startActivityForResult(intent, 3)
            Log.i("DUER", "here rlReg!!!");
        }



        return binded.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        val parentViewGroup = binded.root.parent as ViewGroup

        parentViewGroup.removeView(binded.root)

    }


    override fun onCreated() {
        mICallback.init()
    }


    interface ICallback {
        fun init()
        fun selected(position: Int)
        fun scrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    }

    companion object {
        fun with(mICallback: ICallback): SelectLoginBodyFrag {
            val frag = SelectLoginBodyFrag()
            frag.setCallback(mICallback)
            return frag
        }
    }
}
