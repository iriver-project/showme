package com.sktelecom.showme.Main.home

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Toast
import com.android.volley.VolleyError
import com.sktelecom.showme.Main.common.CommonProfileActivity
import com.sktelecom.showme.Main.common.CommonVoteActivity
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.Model.VoArtist
import com.sktelecom.showme.base.Model.VoContents
import com.sktelecom.showme.base.Network.SmartNetWork
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.base.view.PViewModel
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class HomeBodyVM : PViewModel() {
    internal val TAG = HomeBodyVM::class.java.simpleName
    internal lateinit var resultList: MutableLiveData<List<PBean>>


    override fun asFragCreate(): PFragment {
        frag = HomeBodyFrag.with("AUDITION", this, mICallBack)
        return frag
    }


    fun asFragResume(): PFragment {
//        if (frag == null)
//            frag = HomeBodyFrag.with("title~", this, mICallBack);
        return frag
    }

    fun getList(): LiveData<List<PBean>> {
        resultList = MutableLiveData()
        callList()
        return resultList
    }

    internal fun callList() {
        val param = JSONObject()
        try {
            param.put("stagNo", 10000000002)
            param.put("prevStagNo", 10000000001)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val url = SmartNetWork.URL_SHOW_ME + "display/stage/artist/gets"
        Log.i("get artist url :: ", url)
        SmartNetWork().getCommonDataPostParam(frag.pActivity, url, param, object : SmartNetWork.SmartNetWorkListener {
            override fun onResponse(Tag: Int, response: JSONObject) {
                try {
                    Log.i("get artist result :: " + response.toString())
                    val msg = updateHandler.obtainMessage()
                    val isReturn = response.optString("resCd")
                    if (isReturn != null && isReturn == "0000") {
                        val array = response.getJSONArray("data")
                        var row: JSONObject
                        val artistStringList = ArrayList<PBean>()

                        for (i in 0..(array.length() - 1)) {
                            row = array.optJSONObject(i)
                            artistStringList.add(VoArtist(row.optString("atstId"), row.optString("atstNm"), row.optString("atstThumbnail"), row.optInt("ststStagRank")))
                        }
                        msg.what = 0
                        msg.obj = artistStringList
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

                resultList.setValue(msg.obj as List<PBean>?)
            }
        }
    }


    fun onClickOne(v: View, vo: VoContents) {
        Log.i("DUER", "here Touch!!!", vo.CONTENTS_ID)
    }

    fun onClickArtist(v: View, vo: VoArtist) {
        Log.i("DUER", "Touch Artist == ", vo.atstId)
        val intent = Intent(frag.activity, CommonProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        frag.activity!!.startActivityForResult(intent, 1)
    }

    fun onClickVote(v: View) {
        val intent = Intent(frag.activity, CommonVoteActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        frag.activity!!.startActivityForResult(intent, 2)
    }

//    override fun onCleared() {
//        super.onCleared()
//        Log.d(TAG, "on cleared called")
//    }

    internal val mICallBack = object : HomeBodyFrag.ICallbackEvent {
        override fun getPage(page: Int) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}