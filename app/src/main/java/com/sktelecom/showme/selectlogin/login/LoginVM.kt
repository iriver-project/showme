package com.sktelecom.showme.selectlogin.login

import android.arch.lifecycle.MutableLiveData
import com.android.volley.VolleyError
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.Model.VoContents
import com.sktelecom.showme.base.Network.SmartNetWork
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.base.view.PViewModel
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

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


    internal fun login() {
        val param = JSONObject()
        try {
            param.put("userId", "comperge@naver.com")
            param.put("userPw", "1111")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
//        Connection=keep-alive, Content-Type=application/json;charset=UTF-8
//        val url = SmartNetWork.URL + "user/login?userId=comperge@naver.com&userPw=1111"
        val url = SmartNetWork.URL + "user/login"
        Log.i("DUER", url)
        SmartNetWork().getCommonDataPostParamCookie(frag1.pActivity, url, param, object : SmartNetWork.SmartNetWorkListener {
            override fun onResponse(Tag: Int, response: JSONObject) {
                try {
                    Log.i(" 결과 getSellingContentsList_2=" + response.toString())

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onErrorResponse(Tag: Int, error: VolleyError) {
                Log.i("DUER...............................>>ERROR_Tag", Tag)

            }
        })
    }


    internal fun gets() {
        val param = JSONObject()
        try {
//            param.put("menuTypeCd", "MT0BASIC11")
//            param.put("limit", "5")
//            param.put("pg", "1")

            param.put("vocaType", "GT")
            param.put("id", "aaaa@aaaa")
            param.put("startRow", "0")
            param.put("numberOfRow", 50)
            param.put("contentsType", "115")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val url = SmartNetWork.URL + "getSellingContentsList_2" //_SHOW_ME // + "display/menu/gets"
        Log.i("DUER", url)
        SmartNetWork().getCommonDataPostParamCookie(frag1.pActivity, url, param, object : SmartNetWork.SmartNetWorkListener {
            override fun onResponse(Tag: Int, response: JSONObject) {
                try {
                    Log.i(" 결과 /gets=" + response.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onErrorResponse(Tag: Int, error: VolleyError) {
                Log.i("DUER...............................>>ERROR_Tag", Tag)

            }
        })
    }


    fun onClickLogin() {
        Log.i("DUER", "here onClickLogin!!!");
        login()
//        gets()
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