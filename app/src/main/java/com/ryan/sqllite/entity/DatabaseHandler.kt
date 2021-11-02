package com.ryan.sqllite.entity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHandler (
    context: Context
): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private const val DATABASE_NAME = "EmployeeDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_CONTACTS = "EmployeeTable"

        private const val KEY_ID = "_id"
        private const val KEY_FIRST_NAME = "firstName"
        private const val KEY_LAST_NAME = "lastName"
        private const val KEY_AGE = "age"
        //val CREATE_CONTACT_TABLE = "CREATE TABLE $TABLE_CONTACTS( $KEY_ID INT PRIMARY KEY, $KEY_FIRST_NAME TEXT, $KEY_LAST_NAME TEXT, $KEY_AGE TEXT )"
        private const val CREATE_CONTACT_TABLE =
            "CREATE TABLE ${TABLE_CONTACTS} (" +
                "$KEY_ID INT NOT NULL AUTO_INCREMENT, " +
                "$KEY_FIRST_NAME TEXT, " +
                "$KEY_LAST_NAME TEXT, " +
                "$KEY_AGE TEXT, " +
                " PRIMARY KEY ($KEY_ID)" +
                    ");"
        private const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_CONTACTS"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_CONTACT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    //Method to insert Data
    fun insetEmployee(emp: Person): Long {

        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            try {
                put(KEY_FIRST_NAME, emp.firstName)
                put(KEY_LAST_NAME, emp.lastName)
                put(KEY_AGE, emp.age)
                Log.d("DatabaseFragment", "Inserted Successfully")
            }catch (e: SQLiteException){
                Log.d("DatabaseFragment", e.message.toString())
            }
        }

        var success: Long? = 0
        //Inserting Row
        try{
            success = db?.insert(TABLE_CONTACTS, null, contentValues)
        } catch (e: SQLiteException){
            Log.d("DatabaseHandler", e.message.toString())
        }

        db.close()
        return success!!
    }
    //Method to Read Data
    @SuppressLint("Range")
    fun readEmployee(): ArrayList<Person> {
        val empList: ArrayList<Person> = ArrayList<Person>()
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch(e: SQLiteException) {
            Log.d("DatabaseHandler: ", e.message.toString())
            return ArrayList()
        }
        var id: Int
        var firstName: String
        var lastName: String
        var age: String
        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                firstName = cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME))
                lastName = cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME))
                age = cursor.getString(cursor.getColumnIndex(KEY_AGE))

                val emp = Person(id = id, firstName = firstName, lastName = lastName, age = age)
                empList.add(emp)

            } while (cursor.moveToNext())
        }
        return empList
    }

}