package org.ratpoisonfactory.base.util

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


object Log {

    private val DEBUG = true
    private val sf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
    private var func = String()
    private var locator = String()
    private val PREFIX = ">>"

    private var DT = false

    private val HEX_CHARS = "0123456789abcdef".toCharArray()

    // LAST_ERROR_500
    private var LAST_ERROR_500 = ""

    internal var s: Long = 0
    private var e: Long = 0

    fun l2(vararg args: Any) {
        if (!DEBUG)
            return
        val e = Exception()
        location2(e)
        android.util.Log.e(func, PREFIX + "f :" + _MESSAGE(*args) + locator)
    }

    fun ln(n: Int, vararg args: Any) {
        if (!DEBUG)
            return
        val e = Exception()
        locationN(n, e)
        android.util.Log.e(func, PREFIX + "f :" + _MESSAGE(*args) + locator)
    }

    fun e(vararg args: Any) {
        if (!DEBUG)
            return
        val e = Exception()
        location1(e)
        android.util.Log.e(func, PREFIX + "!!:" + _MESSAGE(*args) + locator)
        e.printStackTrace()
    }

    fun w(vararg args: Any) {
        if (!DEBUG)
            return
        val e = Exception()
        location1(e)
        android.util.Log.w(func, PREFIX + "! :" + _MESSAGE(*args) + locator)
        e.printStackTrace()
    }

    fun l() {
        if (!DEBUG)
            return
        val e = Exception()
        location1(e)
        android.util.Log.e(func, PREFIX + "f :" + locator)
    }

    fun f(vararg args: Any) {
        val e = Exception()
        location1(e)
        android.util.Log.e(func, PREFIX + "f :" + _MESSAGE(*args) + locator)
    }

    fun l(vararg args: Any) {
        if (!DEBUG)
            return
        val e = Exception()
        location1(e)
        android.util.Log.e(func, PREFIX + "f :" + _MESSAGE(*args) + locator)
    }

    fun ll(vararg args: Any) {
        if (!DEBUG)
            return

        val e = Exception()
        location1(e)

        val log = _MESSAGE(*args)
        android.util.Log.e(func, PREFIX + "ll : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + locator)
        val a = 200
        var i = 0
        i = 0
        while (i < log.length) {
            android.util.Log.e(func, log.substring(i, Math.min(log.length, i + a)))
            i += a
        }

        android.util.Log.e(func, PREFIX + "ll : <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + locator)
    }

    fun ii(vararg args: Any) {
        if (!DEBUG)
            return

        val e = Exception()
        location1(e)

        val log = _MESSAGE(*args)
        android.util.Log.i(func, PREFIX + "ll : >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + locator)
        val a = 200
        var i = 0
        i = 0
        while (i < log.length) {
            android.util.Log.i(func, log.substring(i, Math.min(log.length, i + a)))
            i += a
        }

        android.util.Log.i(func, PREFIX + "ll : <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + locator)
    }

    fun dt(vararg args: Any) {
        if (!DEBUG)
            return

        val e = Exception()
        location1(e)
        DT = true
        val log = _MESSAGE(*args)
        DT = false
        android.util.Log.e(func, PREFIX + "dt :" + log + locator)
    }

    fun c(vararg args: Any) {
        if (!DEBUG)
            return
        val e = Exception()
        location1(e)
        android.util.Log.e(func, PREFIX + "? :" + _MESSAGE(*args) + locator)
        e.printStackTrace()
    }

    fun d(vararg args: Any) {
        if (!DEBUG)
            return
        val e = Exception()
        location1(e)
        android.util.Log.e(func, PREFIX + "d :" + _MESSAGE(*args) + locator)
    }

    fun v(vararg args: Any) {
        if (!DEBUG)
            return
        val e = Exception()
        location1(e)
        android.util.Log.e(func, PREFIX + "v :" + _MESSAGE(*args) + locator)
    }

    fun i(vararg args: Any) {
        if (!DEBUG)
            return
        val e = Exception()
        location1(e)
        android.util.Log.i(func, PREFIX + "i :" + _MESSAGE(*args) + locator)
    }

    fun _MESSAGE(vararg args: Any): String {
        if (args == null)
            return "null[]"

        val sb = StringBuffer()
        for (`object` in args) {
            sb.append(",")

            var string: String? = ""
            if (`object` == null)
                string = "null"
            else if (`object` is Class<*>)
                string = _DUMP(`object`)
            else if (`object` is Cursor)
                string = _DUMP(`object`)
            else if (`object` is Intent)
                string = _DUMP(`object`)
            else if (`object` is Bundle)
                string = _DUMP(`object`)
            else if (`object` is Uri)
                string = _DUMP(`object`)
            else if (DT && `object` is Long)
                string = _DUMP(`object`)
            else if (`object` is Exception)
                string = _DUMP(`object`)
            else
                string = `object`.toString()

            sb.append(string)
        }
        return sb.toString()
    }

    private fun _DUMP(c: Cursor?): String? {
        if (c == null)
            return null

        val sb = StringBuilder()
        val count = c.count
        sb.append("<$count>")

        try {
            val columns = c.columnNames
            sb.append(Arrays.toString(columns))
            sb.append("\n")
        } catch (e: Exception) {
        }

        val isBeforeFirst = c.isBeforeFirst
        val countColumns = c.columnCount
        while (c.moveToNext()) {
            for (i in 0 until countColumns) {
                try {
                    sb.append(c.getString(i) + ",")
                } catch (e: Exception) {
                    sb.append("BLOB,")
                }

            }
            sb.append("\n")
            if (isBeforeFirst)
                break
        }
        return sb.toString()
    }

    fun cursor(c: Cursor?) {
        if (c == null)
            return

        val sb = StringBuilder()
        val count = c.count
        sb.append("<$count>")

        try {
            val columns = c.columnNames
            sb.append(Arrays.toString(columns))
            Log.l2(sb)
            sb.setLength(0)
        } catch (e: Exception) {
        }

        val isBeforeFirst = c.isBeforeFirst
        val countColumns = c.columnCount
        while (c.moveToNext()) {
            for (i in 0 until countColumns) {
                try {
                    sb.append(c.getString(i) + ",")
                } catch (e: Exception) {
                    sb.append("BLOB,")
                }

            }
            Log.l2(sb)
            sb.setLength(0)
            if (!isBeforeFirst)
                break
        }
    }

    fun cursor(context: Context, uri: Uri) {
        val c = context.contentResolver.query(uri, null, null, null, null) ?: return
        cursor(c)
        c.close()
    }

    private fun _DUMP(e: Exception): String? {
        e.printStackTrace()
        return e.message
    }

    private fun _DUMP(bundle: Bundle?): String {
        val sb = StringBuffer()

        val keys = bundle!!.keySet()
        var type: String? = null
        var value: String? = null
        for (key in keys) {
            val o = bundle.get(key)
            if (o == null) {
                type = "null"
                value = "null"
            } else {
                type = o.javaClass.simpleName
                value = o.toString()
            }
            sb.append("\r\n")
            sb.append("$key,$type,$value")
        }

        return sb.toString()
    }

    private fun _DUMP(cls: Class<*>?): String {
        return if (cls == null) "" else cls.simpleName + if (cls.superclass != null) ">>" + cls.superclass.simpleName else ""
    }

    private fun _DUMP(uri: Uri): String {
        return uri.toString()
        // StringBuffer sb = new StringBuffer();
        // sb.append("\r\n Uri                       ");
        // sb.append(uri.toString());
        // sb.append("\r\n Scheme                    ");
        // sb.append(uri.getScheme() != null ? uri.getScheme().toString() :
        // "null");
        // sb.append("\r\n Host                      ");
        // sb.append(uri.getHost() != null ? uri.getHost().toString() : "null");
        // sb.append("\r\n Port                      ");
        // sb.append(uri.getPort());
        // sb.append("\r\n Path                      ");
        // sb.append(uri.getPath() != null ? uri.getPath().toString() : "null");
        // sb.append("\r\n Query                     ");
        // sb.append(uri.getQuery() != null ? uri.getQuery().toString() :
        // "null");
        // sb.append("\r\n");
        // sb.append("\r\n Fragment                  ");
        // sb.append(uri.getFragment() != null ? uri.getFragment().toString() :
        // "null");
        // sb.append("\r\n LastPathSegment           ");
        // sb.append(uri.getLastPathSegment() != null ?
        // uri.getLastPathSegment().toString() : "null");
        // sb.append("\r\n SchemeSpecificPart        ");
        // sb.append(uri.getSchemeSpecificPart() != null ?
        // uri.getSchemeSpecificPart().toString() : "null");
        // sb.append("\r\n UserInfo                  ");
        // sb.append(uri.getUserInfo() != null ? uri.getUserInfo().toString() :
        // "null");
        // sb.append("\r\n PathSegments              ");
        // sb.append(uri.getPathSegments() != null ?
        // uri.getPathSegments().toString() : "null");
        // sb.append("\r\n Authority                 ");
        // sb.append(uri.getAuthority() != null ? uri.getAuthority().toString()
        // : "null");
        // sb.append("\r\n");
        // sb.append("\r\n EncodedAuthority          ");
        // sb.append(uri.getEncodedAuthority() != null ?
        // uri.getEncodedAuthority().toString() : "null");
        // sb.append("\r\n EncodedPath               ");
        // sb.append(uri.getEncodedPath() != null ?
        // uri.getEncodedPath().toString() : "null");
        // sb.append("\r\n EncodedQuery              ");
        // sb.append(uri.getEncodedQuery() != null ?
        // uri.getEncodedQuery().toString() : "null");
        // sb.append("\r\n EncodedFragment           ");
        // sb.append(uri.getEncodedFragment() != null ?
        // uri.getEncodedFragment().toString() : "null");
        // sb.append("\r\n EncodedSchemeSpecificPart ");
        // sb.append(uri.getEncodedSchemeSpecificPart() != null ?
        // uri.getEncodedSchemeSpecificPart().toString() : "null");
        // sb.append("\r\n EncodedUserInfo           ");
        // sb.append(uri.getEncodedUserInfo() != null ?
        // uri.getEncodedUserInfo().toString() : "null");
        // sb.append("\r\n");
        // return sb.toString();
    }

    private fun _DUMP(milliseconds: Long): String {
        return "<" + milliseconds + "," + sf.format(Date(milliseconds)) + ">"
    }

    private fun _DUMP(intent: Intent): String {
        val sb = StringBuffer()

        sb.append("\r\n Action     ")
        sb.append(if (intent.action != null) intent.action!!.toString() else "null")
        sb.append("\r\n Data       ")
        sb.append(if (intent.data != null) intent.data!!.toString() else "null")
        sb.append("\r\n Categories ")
        sb.append(if (intent.categories != null) intent.categories.toString() else "null")
        sb.append("\r\n Type       ")
        sb.append(if (intent.type != null) intent.type!!.toString() else "null")
        sb.append("\r\n Scheme     ")
        sb.append(if (intent.scheme != null) intent.scheme!!.toString() else "null")
        sb.append("\r\n Package    ")
        sb.append(if (intent.`package` != null) intent.`package`!!.toString() else "null")
        sb.append("\r\n Component  ")
        sb.append(if (intent.component != null) intent.component!!.toString() else "null")

        if (intent.extras != null)
            sb.append(_DUMP(intent.extras))

        sb.append("\r\n")
        return sb.toString()
    }

    fun location(): String {
        return locationN(1, Exception())
    }

    private fun location1(e: Exception): String {
        return locationN(1, e)
    }

    private fun location2(e: Exception): String {
        return locationN(2, e)
    }

    fun locationN(n: Int, e: Exception): String {
        val funcStack = e.stackTrace[n].toString()

        val posJava = funcStack.lastIndexOf("(")
        val posFunc = funcStack.lastIndexOf(".", posJava - 1)
        val posClass = funcStack.lastIndexOf(".", posFunc - 1)
        func = funcStack.substring(posClass + 1, posJava)// classfuncName
        locator = " :at" + funcStack.substring(posJava)// javaName
        return locator
    }

    fun t(context: Context, vararg args: Any) {
        Log.l(*args)
        Toast.makeText(context, _MESSAGE(*args), Toast.LENGTH_SHORT).show()
    }


    fun s2h(string_hex_format: String): ByteArray {
        val bytes = ByteArray(string_hex_format.length / 2)
        for (i in bytes.indices) {
            bytes[i] = Integer.parseInt(string_hex_format.substring(2 * i, 2 * i + 2), 16).toByte()
        }
        return bytes
    }

    fun SL(error: String) {
        LAST_ERROR_500 = error + LAST_ERROR_500.substring(0, 500)
    }

    fun GL(): String {
        return LAST_ERROR_500
    }

    fun CL() {
        LAST_ERROR_500 = ""
    }

    fun _s() {
        s = System.currentTimeMillis()
        e = s
        Log.l2(String.format("%20d%20d%20d", s, e, e - s))
    }

    fun _tic() {
        e = System.currentTimeMillis()
        Log.l2(String.format("%20d%20d%20d", s, e, e - s))
        s = e
    }

    fun _tic(log: String) {
        e = System.currentTimeMillis()
        Log.l2(log, String.format("%20d%20d%20d", s, e, e - s))
        s = e
    }

    fun _length(): Long {
        e = System.currentTimeMillis()
        Log.l2(String.format("%20d%20d%20d", s, e, e - s))
        val _tic_length = e - s
        s = e
        return _tic_length
    }
}
