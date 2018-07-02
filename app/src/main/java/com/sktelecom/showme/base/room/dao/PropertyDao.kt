package com.sktelecom.showme.base.room.dao


import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.sktelecom.showme.base.room.entity.Property

@Dao
interface PropertyDao {

    @Query("SELECT property_value FROM property where property_key =  :property_key")
    fun getProperty(property_key: String): String
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setProperty(property: Property)

//    @get:Query("SELECT * FROM property")
//    val all: List<Property>

//    @Query("SELECT COUNT(*) from property")
//    fun count(): Int

//    @Insert
//    fun insertAll(vararg properties: Property)

//    @Delete
//    fun delete(property: Property)
}
