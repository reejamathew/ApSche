package com.mdev.apsche.database

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.util.Log

class UserDetailsDatabase(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $USER_DETAILS_TABLE($COL_EMAIL_ID TEXT PRIMARY KEY , $COL_USER_NAME TEXT, $COL_PASSWORD TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $USER_DETAILS_TABLE")
    }

    fun insertUser(email:String?, username: String?, password: String?): Boolean {
        Log.d("number","3")
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_EMAIL_ID,email)
        contentValues.put(COL_USER_NAME, username)
        contentValues.put(COL_PASSWORD, password)
        Log.d("number","2")
        val result = sqLiteDatabase.insert(USER_DETAILS_TABLE, null, contentValues)
        sqLiteDatabase.close()
        Log.d("insert function",result.toString())
        return !result.equals(-1)
    }

    fun checkEmail(email: String): Boolean {
        val sqLiteDatabase = this.writableDatabase
        Log.d("email in check email", email)
        val result =
            sqLiteDatabase.rawQuery("SELECT * FROM $USER_DETAILS_TABLE WHERE $COL_EMAIL_ID=?", arrayOf(email))
        Log.d("check email", result.count.toString())
        //sqLiteDatabase.close()
        return !result.count.equals(0)


    }

    fun checkLogin(email: String, password: String): Boolean {
        val sqLiteDatabase = this.readableDatabase
        val result = sqLiteDatabase.rawQuery(
            "SELECT * FROM $USER_DETAILS_TABLE WHERE $COL_EMAIL_ID=? AND $COL_PASSWORD=?",
            arrayOf(email, password)
        )
        Log.d("check user", result.count.toString())
        sqLiteDatabase.close()



        return !result.count.equals(0)
    }


    companion object{
        private const val DATABASE_NAME = "ApSche.db"
        private const val DATABASE_VERSION = 1
        private const val USER_DETAILS_TABLE = "user"
        private const val COL_USER_NAME = "user_name"
        private const val COL_PASSWORD = "password"
        private const val COL_EMAIL_ID = "email_id"

    }

}