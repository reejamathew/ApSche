package com.mdev.apsche.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.mdev.apsche.model.Apartment
import java.util.*

class ApartmentDatabase (context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "test5.db"
        private const val DATABASE_VERSION = 1

        //recipe-details table
        private const val APARTMENT_TABLE = "apartmentDetails"
        private const val COL_APARTMENT_ID = "aptId"
        private const val COL_APARTMENT_NO = "aptNo"
        private const val COL_TENANT_NAME = "tenant_name"
        private const val COL_PHONE_NUMBER = "phone_no"
        private const val COL_LEASE_AMOUNT = "lease_amount"
      //  private const val COL_LEASE_CURRENCY = "currency_code"
        private const val COL_LEASE_PERIOD = "lease_period"
        private const val COL_BEDS = "beds_bath"
        private const val COL_EMAIL_ID = "email_id"
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $APARTMENT_TABLE(" +
                "$COL_APARTMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+"$COL_APARTMENT_NO INTEGER,"+
                "$COL_TENANT_NAME TEXT,"+
                "$COL_PHONE_NUMBER INTEGER,"+
                "$COL_LEASE_AMOUNT INTEGER,"+
                "$COL_LEASE_PERIOD TEXT,"+
                "$COL_EMAIL_ID TEXT,"+
                "$COL_BEDS TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $APARTMENT_TABLE")
    }

    fun insertApartment(tenant_name: String?, apt_no: String?, phone_no: String?, lease_amount: String?, lease_period: String?, beds: String?, email_id: String?): Boolean {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        Log.d("apartmentnumberininsert", apt_no.toString())
        Log.d("insertvalues",apt_no+tenant_name+phone_no+lease_amount+lease_period+beds+email_id)
        contentValues.put(ApartmentDatabase.COL_APARTMENT_NO, apt_no)
        contentValues.put(ApartmentDatabase.COL_TENANT_NAME, tenant_name)
        contentValues.put(ApartmentDatabase.COL_PHONE_NUMBER, phone_no)
        contentValues.put(ApartmentDatabase.COL_LEASE_AMOUNT, lease_amount)
     //   contentValues.put(ApartmentDatabase.COL_LEASE_CURRENCY,currency_code)
        contentValues.put(ApartmentDatabase.COL_LEASE_PERIOD, lease_period)
        contentValues.put(ApartmentDatabase.COL_BEDS, beds)
        contentValues.put(ApartmentDatabase.COL_EMAIL_ID, email_id)

        Log.d("content",contentValues.toString())

        val cursor = sqLiteDatabase.insert(ApartmentDatabase.APARTMENT_TABLE, null, contentValues)


       // sqLiteDatabase.close()
        return !cursor.equals(-1)
    }
    fun getApartmentDetails(email_id: String?): ArrayList<Apartment> {
        val sqliteDatabase = this.readableDatabase
        if (email_id != null) {
            Log.d("get apartment",email_id)
        }
        val cursor =  sqliteDatabase.rawQuery("SELECT * FROM $APARTMENT_TABLE WHERE $COL_EMAIL_ID=?", arrayOf(email_id))

        val apartmentList: ArrayList<Apartment> = ArrayList()
      Log.d("apartmentvalue",cursor.count.toString())
        if (cursor.moveToFirst()) {
            do {
                Log.d("value1",cursor.getInt(1).toString())
                apartmentList.add(

                    Apartment(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                  //      cursor.getString(7)

                    )
                )
            } while (cursor.moveToNext())

        }

        return apartmentList
    }
    fun getApartmentDetailsById(aptId: String?): ArrayList<Apartment> {
        Log.d("id",aptId.toString())
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
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                      //  cursor.getString(7)

                    )
                )
            } while (cursor.moveToNext())

        }

        return apartmentList;
    }
    fun getApartmentDetailsByNoticeInformation(notice: String?): ArrayList<Apartment> {
        Log.d("notice",notice.toString())
        val sqliteDatabase = this.readableDatabase
        val cursor =  sqliteDatabase.rawQuery("SELECT * FROM $APARTMENT_TABLE WHERE $COL_LEASE_PERIOD LIKE ? ", arrayOf(notice))
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
                        cursor.getInt(5),
                        cursor.getString(6),
                        //  cursor.getString(7)

                    )
                )
            } while (cursor.moveToNext())

        }

        return apartmentList;
    }

    fun updateApartment(aptId: String?, apt_no: String?, tenant_name: String?, phone_no: String?, lease_period: String?, lease_amount: String?,  currency:String?, beds: String?): Boolean {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_APARTMENT_ID, aptId)
        contentValues.put(COL_APARTMENT_NO, apt_no)
        contentValues.put(COL_TENANT_NAME, tenant_name)
        contentValues.put(COL_PHONE_NUMBER, phone_no)
        contentValues.put(COL_LEASE_PERIOD, lease_period)
        contentValues.put(COL_LEASE_AMOUNT, lease_amount)
       // contentValues.put(COL_LEASE_CURRENCY,currency_code)
        contentValues.put(COL_BEDS, beds)
        contentValues.put(COL_EMAIL_ID, "test@gmail.com")

        Log.d("content", contentValues.toString())

        val cursor = sqLiteDatabase.update(APARTMENT_TABLE, contentValues, "$COL_APARTMENT_ID =$aptId",null)
        Log.d("recipeList", cursor.toString())
        getApartmentDetailsById(aptId)
        return !cursor.equals(-1)
    }


}