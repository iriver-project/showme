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

    //    public void populateAsync() {
    //        PopulateDbAsync task = new PopulateDbAsync();
    //        task.execute();
    //    }
    //
    //    public void populateSync() {
    //        populateWithTestData();
    //    }
    //
    //    private User addUser(User user) {
    //        db.userDao().insertAll(user);
    //        return user;
    //    }


    //    private void populateWithTestData() {
    //        User user = new User();
    //        user.setFirstName("Ajay");
    //        user.setLastName("Saini");
    //        user.setAge(25);
    //        addUser(user);
    //
    //        List<User> userList = db.userDao().getAll();
    //        Log.d(DBManager.TAG, "Rows Count: " + userList.size());
    //    }
    //
    //    private class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
    //        @Override
    //        protected Void doInBackground(final Void... params) {
    //            populateWithTestData();
    //            return null;
    //        }
    //    }


}
