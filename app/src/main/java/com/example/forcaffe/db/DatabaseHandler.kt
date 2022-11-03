package com.example.forcaffe.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.forcaffe.model.Feedback

class DatabaseHandler (ctx: Context): SQLiteOpenHelper(ctx,DB_NAME,null,DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $DESCRICAO TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addfeedback(feedback: Feedback): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DESCRICAO, feedback.Descricao)
        val _success = db.insert(TABLE_NAME,null,values)
        return (("$_success").toInt() != -1)
    }

    @SuppressLint("Range")
    fun getfeedback(_id: Int): Feedback {
        val feedback = Feedback()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        feedback.Id = cursor.getInt(cursor.getColumnIndex(ID))
        feedback.Descricao = cursor.getString(cursor.getColumnIndex(DESCRICAO))
        cursor.close()
        return feedback
    }

    @SuppressLint("Range")
    fun feedbacks(): ArrayList<Feedback> {
        val feedbackList = ArrayList<Feedback>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val feedback = Feedback()
                    feedback.Id = cursor.getInt(cursor.getColumnIndex(ID))
                    feedback.Descricao = cursor.getString(cursor.getColumnIndex(DESCRICAO))
                    feedbackList.add(feedback)
                }while(cursor.moveToNext())
            }
        }
        cursor.close()
        return feedbackList
    }

    fun updatefeedback(feedback: Feedback): Boolean{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DESCRICAO, feedback.Descricao)
        }
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(feedback.Id.toString())).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    fun deletefeedback(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        return ("$_success").toInt() != -1
    }

    fun deleteAllfeedback(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null,null).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "forcaffe"
        private val TABLE_NAME = "feedback"
        private val ID = "Id"
        private val DESCRICAO = "Descricao"
    }
}