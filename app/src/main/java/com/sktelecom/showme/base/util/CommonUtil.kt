package com.sktelecom.showme.base.util

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.TypedValue
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class CommonUtil private constructor() {


    @SuppressLint("SimpleDateFormat")
    fun now(): String {
        val cal = Calendar.getInstance()
        val dateformat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        return dateformat.format(cal.time)
    }

    private object Holder {
        val INSTANCE = CommonUtil()
    }

    //dp to px
    fun dpTopx(pCon: Context, dp: Int): Int {
        val r = pCon.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics).toInt()
    }

    fun getPathFromUri(context: Context, uri: Uri): String {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToNext()
        val path = cursor.getString(cursor.getColumnIndex("_data"))
        cursor.close()
        return path
    }


    fun getPath(context: Context, uri: Uri): String? {

        try {

            // DocumentProvider
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (DocumentsContract.isDocumentUri(context, uri)) {
                    // ExternalStorageProvider
                    if (isExternalStorageDocument(uri)) {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val type = split[0]

                        if ("primary".equals(type, ignoreCase = true)) {
                            return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                        }

                        //  handle non-primary volumes
                    } else if (isDownloadsDocument(uri)) {

                        val id = DocumentsContract.getDocumentId(uri)
                        val contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))

                        return getDataColumn(context, contentUri, null, null)
                    } else if (isMediaDocument(uri)) {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val type = split[0]

                        var contentUri: Uri? = null
                        if ("image" == type) {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        } else if ("video" == type) {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        } else if ("audio" == type) {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        }

                        val selection = "_id=?"
                        val selectionArgs = arrayOf(split[1])

                        return getDataColumn(context, contentUri, selection, selectionArgs)
                    }// MediaProvider
                    // DownloadsProvider
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {
                return getDataColumn(context, uri, null, null)
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }// File
            // MediaStore (and general)
        }catch (e:Exception){

        }


        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    internal fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    internal fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    internal fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                      selectionArgs: Array<String>?): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    var PROPERTIY_DIR = Environment.getExternalStorageDirectory().toString() + "/RPFACTORY/"
    private val PROPERTIY_DIR_NOMEDIA1 = Environment.getExternalStorageDirectory().toString() + "/RPFACTORY/TEMP_TF/"
    private val PROPERTIY_DIR_NOMEDIA2 = Environment.getExternalStorageDirectory().toString() + "/RPFACTORY/TEMP_TF/.nomedia"

    fun getTempMediaPath(): String {
        val m_profile_dir = File(PROPERTIY_DIR)
        val m_profile_dir_nomedia1 = File(PROPERTIY_DIR_NOMEDIA1)
        val m_profile_dir_nomedia2 = File(PROPERTIY_DIR_NOMEDIA2)

        try {
            if (!m_profile_dir.exists()) {
                if (!m_profile_dir.mkdir())
                    throw Exception("$PROPERTIY_DIR failL to creat ")
            }
            if (!m_profile_dir_nomedia1.exists()) {
                if (!m_profile_dir_nomedia1.mkdir())
                    throw Exception("$PROPERTIY_DIR_NOMEDIA1 failM to creat set")
            }
            if (!m_profile_dir_nomedia2.exists()) {
                if (!m_profile_dir_nomedia2.mkdir())
                    throw Exception(PROPERTIY_DIR_NOMEDIA2 + "fail to creat ")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return PROPERTIY_DIR_NOMEDIA1
    }


    // Bitmap to File
    //bitmap에는 비트맵, strFilePath에 는 파일을 저장할 경로, strFilePath 에는 파일 이름을 할당해주면 됩니다.
    fun createSmallBMP(orgialFileFullPath: String, strFilePath: String, filename: String): String {
        return createSmallBMP(orgialFileFullPath, strFilePath, filename, 500000)
    }

    fun createSmallBMP(orgialFileFullPath: String, strFilePath: String, filename: String, reduceSize: Int): String {
        val degree = getPhotoOrientation(orgialFileFullPath)
        val originalFile = File(orgialFileFullPath)
        if (!originalFile.exists()) {
            return ""
        }
        val length = originalFile.length().toInt()
        Log.i("DUER", "LENGTH $length")
        var divided = length / reduceSize * 2
        if (divided <= 0)
            divided = 0
        if (divided > 6)
            divided = 6
        Log.i("DUER", "DIVIDER $divided")
        val option = BitmapFactory.Options()
        option.inSampleSize = divided
        var bitmap = BitmapFactory.decodeFile(orgialFileFullPath, option)

        val file = File(strFilePath)
        // If no folders
        if (!file.exists()) {
            file.mkdirs()
            // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
        val fileCacheItem = File(strFilePath + filename)
        var out: OutputStream? = null
        try {
            val height = bitmap.height
            val width = bitmap.width
            fileCacheItem.createNewFile()
            out = FileOutputStream(fileCacheItem)

            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                out!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        Log.i("DUER", "LENGTH after.." + fileCacheItem.length())


        return createSmallBMP(safeDecodeBitmapFile(strFilePath + filename, degree), strFilePath, filename)
    }


    fun createSmallBMP(bitmap: Bitmap?, strFilePath: String, filename: String): String {
        var bitmap = bitmap
        val file = File(strFilePath)
        // If no folders
        if (!file.exists()) {
            file.mkdirs()
            // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
        val fileCacheItem = File(strFilePath + filename)
        var out: OutputStream? = null
        try {
            val height = bitmap!!.height
            val width = bitmap.width
            fileCacheItem.createNewFile()
            out = FileOutputStream(fileCacheItem)
            //160 부분을 자신이 원하는 크기로 변경할 수 있습니다.
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, out)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                out!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return strFilePath + filename
    }


    /**
     * 이미지 각도 Orientation정보 얻기
     */
    fun getPhotoOrientation(filepath: String): Int {
        var degree = 0
        var exif: ExifInterface? = null
        try {
            exif = ExifInterface(filepath)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (exif != null) {
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
                }
            }
        }
        return degree
    }

    /**
     * 이미지를 불러와 이미지 orantation을 조사하여 이미지 Rotation 해주는함수
     */
    fun safeDecodeBitmapFile(strFilePath: String, degree: Int): Bitmap? {
        try {
            val file = File(strFilePath)
            if (file.exists() == false) {
                Log.i("DUER", "[ImageDownloader] SafeDecodeBitmapFile : File does not exist !!")
                return null
            }
            // Max image size
            val IMAGE_MAX_SIZE = 9999999
            val bfo = BitmapFactory.Options()
            bfo.inJustDecodeBounds = true
            BitmapFactory.decodeFile(strFilePath, bfo)

            if (bfo.outHeight * bfo.outWidth >= IMAGE_MAX_SIZE * IMAGE_MAX_SIZE) {
                bfo.inSampleSize = Math.pow(2.0, Math.round(Math.log(IMAGE_MAX_SIZE / Math.max(bfo.outHeight, bfo.outWidth).toDouble()) / Math.log(0.5)).toInt().toDouble()).toInt()
            }
            bfo.inJustDecodeBounds = false
            bfo.inPurgeable = true
            bfo.inDither = true

            val bitmap = BitmapFactory.decodeFile(strFilePath, bfo)
            //            int degree = getPhotoOrientation(strFilePath);
            return getRotatedBitmap(bitmap, degree)
        } catch (ex: OutOfMemoryError) {
            ex.printStackTrace()
            return null
        }

    }


    /**
     * 이미지 각도를 회전하는 함수
     */
    fun getRotatedBitmap(bitmap: Bitmap?, degrees: Int): Bitmap? {
        var bitmap = bitmap
        if (degrees != 0 && bitmap != null) {
            val m = Matrix()
            m.setRotate(degrees.toFloat(), bitmap.width.toFloat() / 2, bitmap.height.toFloat() / 2)
            try {
                val b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)
                if (bitmap != b2) {
                    bitmap.recycle()
                    bitmap = b2
                }
            } catch (ex: OutOfMemoryError) {
                // We have no memory to rotate. Return the original bitmap.
            }

        }
        return bitmap
    }



    companion object {
        val with: CommonUtil by lazy { Holder.INSTANCE }
    }
}