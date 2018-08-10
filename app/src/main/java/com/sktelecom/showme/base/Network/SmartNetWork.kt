package com.sktelecom.showme.base.Network

import android.content.Context
import android.os.Build
import com.sktelecom.showme.base.util.Log
import com.android.volley.*

import com.android.volley.toolbox.JsonObjectRequest
import com.sktelecom.showme.BackendApplication

import org.json.JSONObject
import java.util.*


class SmartNetWork {

    interface SmartNetWorkListener {
        fun onResponse(Tag: Int, response: JSONObject)

        fun onErrorResponse(Tag: Int, error: VolleyError)
    }


    private fun doRequestVolley(ctx: Context, request: Request<*>) {
        request.retryPolicy = DefaultRetryPolicy(
                SOCK_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        BackendApplication.instance!!.addToRequestQueue(request)
    }


    fun getCommonData(ctx: Context, url: String, param: JSONObject?, netWorkListener: SmartNetWorkListener?) {
        var param = param
        Log.i("DUER>>>>>>>>>>>>>", url)
        if (param == null)
            param = JSONObject()


        val jsObjRequest = JsonObjectRequest(Request.Method.GET, url, param, Response.Listener { response ->
            //
            netWorkListener?.onResponse(0, response)
        }, Response.ErrorListener { error ->
            Log.i("onErrorResponse ", "")
            error.printStackTrace()
            netWorkListener?.onErrorResponse(0, error)
        })

        doRequestVolley(ctx, jsObjRequest)
    }

    fun getCommonDataPostParam(ctx: Context, url: String, param: JSONObject?, netWorkListener: SmartNetWorkListener) {
        var param = param
        Log.i("DUER>>>>>>>>>>>>>", url)
        if (param == null) {
            Log.i("DUER>>>>>>>>>>>>>param is null")
            param = JSONObject()
        }
        val jsObjRequest = JsonObjectRequest(Request.Method.POST, url, param, Response.Listener { response ->
            //
            netWorkListener.onResponse(0, response)
        }, Response.ErrorListener { error ->
            Log.i("onErrorResponse ", "")
            error.printStackTrace()
            netWorkListener.onErrorResponse(0, error)
        })
        doRequestVolley(ctx, jsObjRequest)
    }


    fun getCommonDataGet(ctx: Context, url: String, netWorkListener: SmartNetWorkListener?) {
        Log.i("DUER>>>>>>>>>>>>>", url)
        val jsObjRequest = JsonObjectRequest(Request.Method.GET, url, JSONObject(), Response.Listener { response ->
            netWorkListener?.onResponse(0, response)
        }, Response.ErrorListener { error ->
            Log.i("onErrorResponse ", "")
            error.printStackTrace()
            netWorkListener?.onErrorResponse(0, error)
        })
        doRequestVolley(ctx, jsObjRequest)
    }


    fun getCommonDataPostParamCookie(ctx: Context, url: String, param: JSONObject, netWorkListener: SmartNetWorkListener?) {

        var param = param
        var url = url;
        Log.i("DUER>>>>>>>>>>>>>url=", url)
        if (param == null) {
            Log.i("DUER>>>>>>>>>>>>>param is null")
            param = JSONObject()
        } else {

            var i = 0;
            val key = param.keys()
            while (key.hasNext()) {
                val b = key.next().toString()
                Log.d("˜˜˜˜", b)
                if (i == 0) {
                    url = url + "?"
                } else {
                    url = url + "&"
                }
                url = url + b + "=" + param.getString(b);

                ++i
            }
        }
        Log.i("DUER>>>>>>>>>>>>>urlOver=", url)

        val jsObjRequest = object : JsonObjectRequest(Request.Method.POST, url, param, Response.Listener { response ->
            netWorkListener?.onResponse(0, response)
        }, Response.ErrorListener { error ->
            error.printStackTrace()
            netWorkListener?.onErrorResponse(0, error)
        }) {
            override fun parseNetworkResponse(response: NetworkResponse): Response<JSONObject> {
                Log.i("response", response.headers.toString())
                val responseHeaders = response.headers
                val rawCookies = responseHeaders["Set-Cookie"]
                if (rawCookies != null) {
                    Log.i("cookies", rawCookies)
                    val subcookie = rawCookies.subSequence(3, rawCookies.length).toString().split(";").get(0)
                    Log.i("subcookie", subcookie)

//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        Log.i(Base64.getDecoder().decode(subcookie).toString())
//                    }else{
//
//                    }
                    val data = android.util.Base64.decode(subcookie, android.util.Base64.DEFAULT)
                    Log.i("data", String(data))

                }
                return super.parseNetworkResponse(response)
            }

//            override fun getHeaders(): Map<String, String> {
//                    Log.i("DUER", "해더 변환~~")
//                    return getAuthHeader()
//            }
        }
        doRequestVolley(ctx, jsObjRequest)
    }


    fun getAuthHeader(): Map<String, String> {
        val headerMap = HashMap<String, String>()
//        Connection=keep-alive, Content-Type=application/json;charset=UTF-8
//        headerMap.put("token", auth)
//        headerMap.put("Api-key", API_KEY)
        headerMap.put("Content-Type", "application/json")
        headerMap.put("charset", "UTF-8")
        headerMap.put("Connection", "keep-alive")
        headerMap.put("Transfer-Encoding", "chunked")
        return headerMap
    }

    companion object {
        val SOCK_TIMEOUT = 1000 * 10
        val URL = "https://1-dot-eduriley1.appspot.com/"
        val URL_SHOW_ME = "http://sk-music-sm-poc-ALB-1645921147.ap-northeast-2.elb.amazonaws.com:9001/sapi/"
    }
}