package com.sktelecom.showme.Main.notification

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Toast
import com.android.volley.VolleyError
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.Model.VoContents
import com.sktelecom.showme.base.Network.SmartNetWork
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.base.view.PViewModel
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class NotificationBodyVM : PViewModel() {
    internal val TAG = NotificationBodyVM::class.java.simpleName
    internal lateinit var fruitList: MutableLiveData<List<PBean>>


    override fun asFragCreate(): PFragment {
        frag = NotificationBodyFrag.with("title~", this, mICallBack);
        return frag
    }


    fun asFragResume(): PFragment {
        if (frag == null)
            frag = NotificationBodyFrag.with("title~", this, mICallBack);
        return frag
    }

    fun getList(): LiveData<List<PBean>> {
        fruitList = MutableLiveData()
        callList()
        return fruitList
    }

    internal fun callList() {
        val param = JSONObject()
        try {
            param.put("vocaType", "GT")
            param.put("id", "aaaa@aaaa")
            param.put("startRow", "0")
            param.put("numberOfRow", 50)
            param.put("contentsType", "115")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val url = SmartNetWork.URL + "getSellingContentsList_2"
        Log.i("DUER", url)
        SmartNetWork().getCommonDataPostParam(frag.pActivity, url, param, object : SmartNetWork.SmartNetWorkListener {
            override fun onResponse(Tag: Int, response: JSONObject) {
                try {
                    Log.i(" 결과 getSellingContentsList_2=" + response.toString())
                    val msg = updateHandler.obtainMessage()
                    val isReturn = response.optString("return")
                    if (isReturn != null && isReturn == "true") {
                        val array = response.getJSONArray("result")
                        var row: JSONObject
                        val fruitsStringList = ArrayList<VoContents>()

                        for (i in 0..(array.length() - 1)) {
                            row = array.optJSONObject(i)
                            fruitsStringList.add(VoContents(row.optString("TITLE"), "type", "desc", row.optString("CONTENTS_ID")))
                        }
                        msg.what = 0
                        msg.obj = fruitsStringList;
                    } else {
                        msg.what = 0
                    }
                    updateHandler.sendMessage(msg)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onErrorResponse(Tag: Int, error: VolleyError) {
                Log.i("DUER...............................>>ERROR_Tag", Tag)
                val msg = updateHandler.obtainMessage()
                msg.what = 100
                updateHandler.sendMessageDelayed(msg, 10)
            }
        })
    }

    @SuppressLint("HandlerLeak")
    internal val updateHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.i("DUER", "핸들러까지옴")

            if (msg.what == 100) {
                Toast.makeText(frag.activity, "인터넷에 연결할 수 없습니다.", Toast.LENGTH_SHORT).show()
                //                    frag.getActivity().finish();
            } else if (msg.what == 0) {

                fruitList.setValue(msg.obj as List<PBean>?)
            }
        }
    }


    fun onClickOne(v: View, vo: VoContents) {
        Log.i("DUER", "here Touch!!!", vo.CONTENTS_ID);
    }

//    override fun onCleared() {
//        super.onCleared()
//        Log.d(TAG, "on cleared called")
//    }

    internal val mICallBack = object : NotificationBodyFrag.ICallbackEvent {
        override fun getPage(page: Int) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}