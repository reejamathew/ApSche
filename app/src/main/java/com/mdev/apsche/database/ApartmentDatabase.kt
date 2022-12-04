package com.mdev.apsche.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.mdev.apsche.model.Apartment
import java.util.*

class ApartmentDatabase (context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys = ON")
        db.execSQL("CREATE TABLE $USER_DETAILS_TABLE("+
                "$COL_EMAIL_ID TEXT PRIMARY KEY ,"+
                " $COL_USER_NAME TEXT,"+
                " $COL_PASSWORD TEXT)")

        db.execSQL("CREATE TABLE $APARTMENT_TABLE(" +
                "$COL_APARTMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+"$COL_APARTMENT_NO TEXT,"+
                "$COL_TENANT_NAME TEXT,"+
                "$COL_PHONE_NUMBER TEXT,"+
                "$COL_LEASE_AMOUNT TEXT,"+
                "$COL_LEASE_INFORMATION TEXT,"+
                "$COL_BEDS_BATH TEXT,"+
                "$COL_EMAIL_ID TEXT,"+
                "FOREIGN KEY($COL_EMAIL_ID) REFERENCES $USER_DETAILS_TABLE($COL_EMAIL_ID))")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $APARTMENT_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $USER_DETAILS_TABLE")
    }

    fun insertApartment(tenant_name: String?, apt_no: String?, phone_no: String?, lease_amount: String?, lease_period: String?, beds: String?, email_id: String?): Boolean {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ApartmentDatabase.COL_APARTMENT_NO, apt_no)
        contentValues.put(ApartmentDatabase.COL_TENANT_NAME, tenant_name)
        contentValues.put(ApartmentDatabase.COL_PHONE_NUMBER, phone_no)
        contentValues.put(ApartmentDatabase.COL_LEASE_AMOUNT, lease_amount)
        contentValues.put(ApartmentDatabase.COL_LEASE_INFORMATION, lease_period)
        contentValues.put(ApartmentDatabase.COL_BEDS_BATH, beds)
        contentValues.put(ApartmentDatabase.COL_EMAIL_ID, email_id)

        Log.d("content",contentValues.toString())

        val cursor = sqLiteDatabase.insert(ApartmentDatabase.APARTMENT_TABLE, null, contentValues)


       // sqLiteDatabase.close()
        return !cursor.equals(-1)
    }
    fun getApartmentDetails(email_id: String?): ArrayList<Apartment> {
        val sqliteDatabase = this.readableDatabase

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
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6),


                    )
                )
            } while (cursor.moveToNext())

        }

        return apartmentList
    }
    fun getApartmentDetailsById(aptId: String?): ArrayList<Apartment> {

        val sqliteDatabase = this.readableDatabase
        val cursor =  sqliteDatabase.rawQuery("SELECT * FROM $APARTMENT_TABLE WHERE $COL_APARTMENT_ID=?", arrayOf(aptId))
        val apartmentList: ArrayList<Apartment> = ArrayList()

        if (cursor.moveToFirst()) {
            do {

                apartmentList.add(
                    Apartment(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6)
                      //  cursor.getString(7)

                    )
                )
            } while (cursor.moveToNext())

        }

        return apartmentList;
    }
    fun getApartmentDetailsByNoticeInformation(notice: String?,email:String?): ArrayList<Apartment> {

        val sqliteDatabase = this.readableDatabase
        val cursor =  sqliteDatabase.rawQuery("SELECT * FROM $APARTMENT_TABLE WHERE $COL_LEASE_INFORMATION LIKE '%'||?||'%' AND $COL_EMAIL_ID=? ", arrayOf(notice,email))
        val apartmentList: ArrayList<Apartment> = ArrayList()

        if (cursor.moveToFirst()) {
            do {
                apartmentList.add(
                    Apartment(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6)
                        //  cursor.getString(7)

                    )
                )
            } while (cursor.moveToNext())

        }

        return apartmentList;
    }

    fun updateApartment(aptId: String?, apt_no: String?, tenant_name: String?, phone_no: String?, lease_period: String?, lease_amount: String?, beds: String?,email:String?): Boolean {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_APARTMENT_ID, aptId)
        contentValues.put(COL_APARTMENT_NO, apt_no)
        contentValues.put(COL_TENANT_NAME, tenant_name)
        contentValues.put(COL_PHONE_NUMBER, phone_no)
        contentValues.put(COL_LEASE_INFORMATION, lease_period)
        contentValues.put(COL_LEASE_AMOUNT, lease_amount)
        contentValues.put(COL_BEDS_BATH, beds)
        contentValues.put(COL_EMAIL_ID, email)

        Log.d("content", contentValues.toString())

        val cursor = sqLiteDatabase.update(APARTMENT_TABLE, contentValues, "$COL_APARTMENT_ID =$aptId",null)

        getApartmentDetailsById(aptId)
        return !cursor.equals(-1)
    }

    fun deleteApartment(aptId: String): Boolean {
        val sqLiteDatabase = this.writableDatabase

        val result =
            sqLiteDatabase.rawQuery("DELETE  FROM $APARTMENT_TABLE WHERE $COL_APARTMENT_ID=?", arrayOf(aptId))
        //sqLiteDatabase.close()
        return !result.count.equals(0)


    }
    fun insertUser(email:String?, username: String?, password: String?): Boolean {

        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_EMAIL_ID,email)
        contentValues.put(COL_USER_NAME, username)
        contentValues.put(COL_PASSWORD, password)
        val result = sqLiteDatabase.insert(USER_DETAILS_TABLE, null, contentValues)
     //   sqLiteDatabase.close()
        return !result.equals(-1)
    }

    fun checkEmail(email: String): Boolean {
        val sqLiteDatabase = this.writableDatabase
        Log.d("email in check email", email)
        val result =
            sqLiteDatabase.rawQuery("SELECT * FROM $USER_DETAILS_TABLE WHERE $COL_EMAIL_ID=?", arrayOf(email))
        //sqLiteDatabase.close()
        return !result.count.equals(0)


    }
    fun checkLogin(email: String, password: String): Boolean {
        val sqLiteDatabase = this.readableDatabase
        val result = sqLiteDatabase.rawQuery(
            "SELECT * FROM $USER_DETAILS_TABLE WHERE $COL_EMAIL_ID=? AND $COL_PASSWORD=?",
            arrayOf(email, password)
        )
      //  sqLiteDatabase.close()

        Log.d("result",result.toString())

        return !result.count.equals(0)
    }
    companion object{
        private const val DATABASE_NAME = "test8.db"
        private const val DATABASE_VERSION = 1
        private const val APARTMENT_TABLE = "apartmentDetails"
        private const val USER_DETAILS_TABLE = "user"
        private const val COL_APARTMENT_ID = "aptId"
        private const val COL_APARTMENT_NO = "aptNo"
        private const val COL_TENANT_NAME = "tenant_name"
        private const val COL_PHONE_NUMBER = "phone_no"
        private const val COL_LEASE_AMOUNT = "lease_amount"
        private const val COL_LEASE_INFORMATION = "lease_information"
        private const val COL_BEDS_BATH = "beds_bath"
        private const val COL_USER_NAME = "user_name"
        private const val COL_PASSWORD = "password"
        private const val COL_EMAIL_ID = "email_id"
    }

}