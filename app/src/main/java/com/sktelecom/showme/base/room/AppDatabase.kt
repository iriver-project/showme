package com.sktelecom.showme.base.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.sktelecom.showme.base.room.dao.PropertyDao
import com.sktelecom.showme.base.room.entity.Property


@Database(entities = arrayOf(Property::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun propertyDao(): PropertyDao
}
