package com.sktelecom.showme.base.room


import android.content.Context
import com.sktelecom.showme.BackendApplication

import com.sktelecom.showme.base.room.accessor.PropretyAc


class DBManager private constructor(context: Context) {
    private val db: AppDatabase

    init {
        db = BackendApplication.database
    }


    fun withProperty(): PropretyAc {
        return PropretyAc(db)
    }

    companion object {
        private lateinit var dbManager: DBManager


        fun withDB(context: Context): DBManager {

            synchronized(DBManager::class.java) {
                dbManager = DBManager(context)
            }

            return dbManager
        }
    }
}
