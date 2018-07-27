package com.sktelecom.showme.Main.common

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.sktelecom.showme.Main.my.MyBodyVM
import com.sktelecom.showme.R
import com.sktelecom.showme.base.util.CommonUtil
import com.sktelecom.showme.base.util.Log
import com.sktelecom.showme.base.view.PActivity
import com.sktelecom.showme.databinding.ActivityCommonProfileBinding

class CommonProfileActivity : PActivity() {

    internal lateinit var binding: ActivityCommonProfileBinding
    internal lateinit var myVm: MyBodyVM
    internal lateinit var topCont: CommonProfileTopCont
    internal lateinit var picAddr: String

    override fun onAfaterCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_common_profile)
        binding.executePendingBindings()

        myVm = ViewModelProviders.of(this).get(MyBodyVM::class.java)
        myVm.asFragCreate()


        topCont = CommonProfileTopCont(this, null)


        pFragAdd(binding.frameTab.id, topCont.asFragResume())
        pFragAdd(binding.frameBody.id, myVm.asFragResume())
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data == null) return

        if (resultCode == RESULT_OK) {  //게시판 게시물 엑티비티에서 넘어온결과.
            if (requestCode == REQUEST_SELECT_PICTURE) {
                val imageUri = data.data

                try {
                    Log.d("image selected path", imageUri!!.path)
                    Log.i("DUER", CommonUtil.with.getPathFromUri(this, imageUri))
                    picAddr = CommonUtil.with.getPathFromUri(this, imageUri)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("image selected path", imageUri!!.path)
                    Log.i("DUER", CommonUtil.with.getPath(this, imageUri)!!)
                    picAddr = CommonUtil.with.getPath(this, imageUri)!!
                    saveProfileAccount()
                    return
                }

                if (picAddr == null)
                    picAddr = CommonUtil.with.getPath(this, imageUri)!!
                saveProfileAccount()

            }
        }
    }


    private fun saveProfileAccount() {
//        myVm.setUserImg(picAddr)
//        val files = picAddr.split("/".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
//        Log.i("DUER", CommonUtil.with.getTempMediaPath() + "" + files[files.size - 1])
//        picAddr = CommonUtil.with.createSmallBMP(picAddr, CommonUtil.with.getTempMediaPath(), files[files.size - 1])
//        Log.i("DUER picAddr", picAddr)
//
//        if (mMainTabBodyCont != null)
//            mMainTabBodyCont.userInfoprogresBarVisible(View.VISIBLE)
//        var url = ""
//        Log.i("DUER", SmartNetWork.URL + "profileUpload_2.do")
//        url = SmartNetWork.URL + "profileUpload_2.do"
//
//        val multipartRequest = object : VolleyMultipartRequest(Request.Method.POST, url, Response.Listener<NetworkResponse> { response ->
//            val resultResponse = String(response.data)
//            Log.i("DUER", response.toString())
//            try {
//                val result = JSONObject(resultResponse)
//                val status = result.getString("return")
//
//                if (status != "false") {  //성공했다면.
//                    // tell everybody you have succed upload image and post strings
//                    Log.i("result", result.optString("result"))
//                    DBManger.withDB(pCon).withSQLProperty().setProperty(TbEntityProperty.USER_IMG_URL, result.optString("result"))
//                    if (mMainTabBodyCont != null)
//                        mMainTabBodyCont.updateUserInfo()
//                } else {
//                    Log.i("실패 result", result)
//                }
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//        }, Response.ErrorListener { error ->
//            if (mMainTabBodyCont != null)
//                mMainTabBodyCont.userInfoprogresBarVisible(View.GONE)
//
//            val networkResponse = error.networkResponse
//            var errorMessage = "Unknown error"
//            if (networkResponse == null) {
//                if (error.javaClass == TimeoutError::class.java) {
//                    errorMessage = "Request timeout"
//                } else if (error.javaClass == NoConnectionError::class.java) {
//                    errorMessage = "Failed to connect server"
//                }
//            } else {
//                val result = String(networkResponse.data)
//                try {
//                    val response = JSONObject(result)
//                    val status = response.getString("status")
//                    val message = response.getString("message")
//
//                    Log.e("Error Status", status)
//                    Log.e("Error Message", message)
//
//                    if (networkResponse.statusCode == 404) {
//                        errorMessage = "Resource not found"
//                    } else if (networkResponse.statusCode == 401) {
//                        errorMessage = "$message Please login again"
//                    } else if (networkResponse.statusCode == 400) {
//                        errorMessage = "$message Check your inputs"
//                    } else if (networkResponse.statusCode == 500) {
//                        errorMessage = "$message Something is getting wrong"
//                    }
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//
//            }
//            Toast.makeText(this, "사진 등록을 실패했습니다.", Toast.LENGTH_LONG).show()
//            Log.i("Error", errorMessage)
//            error.printStackTrace()
//        }) {
//            protected//                params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
//            val params: Map<String, String>
//                get() {
//                    val params = HashMap<String, String>()
////                    params["id"] = DBManger.withDB(pCon).withSQLProperty().getProperty(TbEntityProperty.USER_ID)
//                    params["ext"] = picAddr.substring(picAddr.length - 3, picAddr.length)
//                    params["vocaType"] = "GT"
//                    Log.i("DUER", picAddr.substring(picAddr.length - 3, picAddr.length))
//                    return params
//                }
//
//            protected val byteData: Map<String, DataPart>
//                get() {
//                    val params = HashMap<K, V>()
//                    val filePath = picAddr
//                    params["file"] = VolleyMultipartRequest.DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(this, Drawable.createFromPath(filePath)), "image/jpeg")
//                    return params
//                }
//        }
//        VolleySingleton.getInstance(this).addToRequestQueue(multipartRequest)
    }

}
