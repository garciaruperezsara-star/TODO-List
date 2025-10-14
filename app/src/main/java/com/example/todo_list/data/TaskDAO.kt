package com.example.todo_list.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.todo_list.utils.DatabaseManager

class TaskDAO(val context: Context) {
    private lateinit var db: SQLiteDatabase

    private fun open() {
        db = DatabaseManager(context).writableDatabase
    }

    private fun close() {
        db.close()
    }

}
