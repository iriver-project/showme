package com.sktelecom.showme.base.room.accessor

import android.os.AsyncTask
import com.sktelecom.showme.base.room.AppDatabase
import com.sktelecom.showme.base.room.entity.Property

class PropretyAc(db: AppDatabase) : PAccessor(db) {

    private val TAG = PropretyAc::class.java.name


    fun setProperty(key: String, value: String) {
//        AsyncTask.execute {
        val property = Property()
        property.property_key = key
        property.property_value = value
        db.propertyDao().setProperty(property)
//        }
    }


    fun getProperty(key: String): String {
//        AsyncTask.execute {
        return db.propertyDao().getProperty(key)
//        }
    }
}
