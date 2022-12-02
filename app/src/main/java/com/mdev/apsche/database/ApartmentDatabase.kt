package com.mdev.apsche.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.mdev.apsche.model.Apartment

class ApartmentDatabase (context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "test2.db"
        private const val DATABASE_VERSION = 1

        //recipe-details table
        private const val APARTMENT_TABLE = "apartmentDetails"
        private const val COL_APARTMENT_NO = "aptNo"
        private const val COL_APARTMENT_ID = "aptId"
        private const val COL_TENANT_NAME = "tenant_name"
        private const val COL_PHONE_NUMBER = "phone_no"
        private const val COL_LEASE_PERIOD = "lease_period"
        private const val COL_LEASE_AMOUNT = "lease_amount"
        private const val COL_BEDS = "beds"
        private const val COL_EMAIL_ID = "email_id"
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $APARTMENT_TABLE(" +
                "$COL_APARTMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$COL_TENANT_NAME TEXT,"+
                "$COL_PHONE_NUMBER TEXT,"+
                "$COL_LEASE_AMOUNT TEXT,"+
                "$COL_LEASE_PERIOD TEXT,"+
                "$COL_EMAIL_ID TEXT,"+
                "$COL_BEDS TEXT,"+
                "$COL_APARTMENT_NO TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $APARTMENT_TABLE")
    }

    fun insertRecipe(tenant_name: String?, apt_no: String?, phone_no: String?, lease_amount: String?,lease_period: String?, beds: String?, email_id: String?): Boolean {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TENANT_NAME, tenant_name)
        contentValues.put(COL_APARTMENT_NO, apt_no)
        contentValues.put(COL_PHONE_NUMBER, phone_no)
        contentValues.put(COL_LEASE_AMOUNT, lease_amount)
        contentValues.put(COL_LEASE_PERIOD, lease_period)
        contentValues.put(COL_BEDS, beds)
        contentValues.put(COL_EMAIL_ID, email_id)

        Log.d("content",contentValues.toString())

        val cursor = sqLiteDatabase.insert(APARTMENT_TABLE, null, contentValues)
        Log.d("recipeList", cursor.toString())
        getApartmentDetails("test@gmail.com")
        return !cursor.equals(-1)
    }

    fun getApartmentDetails(email_id: String?): ArrayList<Apartment> {
        val sqliteDatabase = this.readableDatabase
        Log.d("email in db", email_id.toString())
        val cursor =  sqliteDatabase.rawQuery("SELECT * FROM $APARTMENT_TABLE WHERE $COL_EMAIL_ID=?", arrayOf(email_id))
        val apartmentList: ArrayList<Apartment> = ArrayList()

        if (cursor.moveToFirst()) {
            do {
                apartmentList.add(
                    Apartment(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)
                    )
                )
            } while (cursor.moveToNext())

        }
        Log.d("recipeList", apartmentList.toString())
        return apartmentList;
    }

}