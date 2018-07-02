package com.sktelecom.showme.base.Network

import android.content.Context
import android.util.Log

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.sktelecom.showme.BackendApplication

import org.json.JSONObject


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

        BackendApplication.instance?.addToRequestQueue(request)
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
        if (param == null)
            param = JSONObject()

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
        val jsObjRequest = JsonObjectRequest(Request.Method.POST, url, JSONObject(), Response.Listener { response ->
            netWorkListener?.onResponse(0, response)
        }, Response.ErrorListener { error ->
            Log.i("onErrorResponse ", "")
            error.printStackTrace()
            netWorkListener?.onErrorResponse(0, error)
        })
        doRequestVolley(ctx, jsObjRequest)
    }

    companion object {
        val SOCK_TIMEOUT = 1000 * 40
        val URL = "https://1-dot-eduriley1.appspot.com/"
    }
}