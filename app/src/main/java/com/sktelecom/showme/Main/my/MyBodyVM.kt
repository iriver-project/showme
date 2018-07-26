package com.sktelecom.showme.Main.my

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import android.os.Message
import android.widget.Toast
import com.android.volley.VolleyError
import com.sktelecom.showme.base.Model.PBean
import com.sktelecom.showme.base.Model.VoUserInfo
import com.sktelecom.showme.base.Model.VoVideo
import com.sktelecom.showme.base.Network.SmartNetWork
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PFragment
import com.sktelecom.showme.base.view.PViewModel
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MyBodyVM : PViewModel() {
    internal val TAG = MyBodyVM::class.java.simpleName
    internal lateinit var fruitList: MutableLiveData<List<PBean>>


    override fun asFragCreate(): PFragment {
        frag = MyBodyFrag.with(this, mICallBack);
        return frag
    }


    fun asFragResume(): PFragment {
        if (frag == null)
            frag = MyBodyFrag.with(this, mICallBack);
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
                        val fruitsStringList = ArrayList<PBean>()

//                        val id: String, val nickName: String, val level: String, val follower: String, val following: String,
//                        val desc: String, val imgUrl: String, val userType: String) : PBean()
                        fruitsStringList.add(VoUserInfo("myid", "whoami", "2", "1", "1", "what I am,,,?"
                                , "http://images6.fanpop.com/image/photos/37800000/-Hello-penguins-of-madagascar-37800672-500-500.gif"
                                , "AT"))
                        for (i in 0..(array.length() - 1)) {
                            row = array.optJSONObject(i)
// val contentsId: String, val heart: String, val title: String, val artistName: String, val artistId: String, val artistImgUrl: String, val imgUrl: String, val videoUrl: String

                            fruitsStringList.add(VoVideo(row.optString("CONTENTS_ID"), row.optString("CONTENTS_TYPE"), row.optString("TITLE"), row.optString("CREATE_USER_NAME")
                                    , row.optString("CREATE_USER"), row.optString("CREATE_USER_IMG_URL"), row.optString("CREATE_USER_IMG_URL"), row.optString("TITLE")))
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


    fun onClickVideo(vo: VoVideo) {
        Log.i("DUER", "here Touch!!!", vo.artistName);
    }


    fun onClickMyLev(vo: VoUserInfo) {
        Log.i("DUER", "here Touch!!!", vo.id);
    }

    fun onClickMyFollower(vo: VoUserInfo) {
        Log.i("DUER", "here Touch!!!", vo.id);
    }

    fun onClickMyFollowing(vo: VoUserInfo) {
        Log.i("DUER", "here Touch!!!", vo.id);
    }

    fun onClickProfile(vo: VoUserInfo) {
        Log.i("DUER", "here Touch!!!", vo.id);
    }

    fun onClickWallet(vo: VoUserInfo) {
        Log.i("DUER", "here Touch!!!", vo.id);
    }

    fun onClickFollow(vo: VoUserInfo) {
        Log.i("DUER", "here Touch!!!", vo.id);
    }

    fun onClickTip(vo: VoUserInfo) {
        Log.i("DUER", "here Touch!!!", vo.id);
    }


//    [kapt] An exception occurred: android.databinding.tool.util.LoggedErrorException: Found data binding errors.
//    ****/ data binding error ****msg:cannot find method onClickOne(android.view.View, com.sktelecom.showme.base.Model.VoContents)
//    in class com.sktelecom.showme.Main.my.MyBodyVM file:/Users/mang-gogim/AndroidStudioProjects/ShowMe/app/src/main/res/layout/my_item.xml loc:27:38 - 27:66 ****\ data binding error ****

//    override fun onCleared() {
//        super.onCleared()
//        Log.d(TAG, "on cleared called")
//    }

    internal val mICallBack = object : MyBodyFrag.ICallbackEvent {
        override fun getPage(page: Int) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}