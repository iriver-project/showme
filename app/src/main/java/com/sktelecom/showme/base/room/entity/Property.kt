package com.sktelecom.showme.base.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "property")
class Property {

    @PrimaryKey
    @ColumnInfo(name = "property_key")
    var property_key = ""

    @ColumnInfo(name = "property_value")
    var property_value: String? = null

}
