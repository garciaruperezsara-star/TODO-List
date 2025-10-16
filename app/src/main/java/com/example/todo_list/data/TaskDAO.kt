package com.example.todo_list.data

import android.content.ContentValues
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

    fun insert(task: Task, category_id: String) {
        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(Task.COLUMN_TITLE, task.title)
        values.put(Task.COLUMN_DONE, false)
        values.put(Task.COLUMN_CATEGORY, category_id)
        try {
            open()
            // Insert the new row, returning the primary key value of the new row
            val newRowId = db.insert(Task.TABLE_NAME, null, values)
            Log.i("DATABASE", "New row inserted in table ${Task.TABLE_NAME} with id: $newRowId")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun update(task: Task, category_id: String) {
        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(Task.COLUMN_TITLE, task.title)
        values.put(Task.COLUMN_DONE, false)
        values.put(Task.COLUMN_CATEGORY, category_id)

        try {
            open()

            // Insert the new row, returning the primary key value of the new row
            val updatedRows = db.update(Task.TABLE_NAME, values, "${Task.COLUMN_ID} = ${task.id}", null)
            Log.i("DATABASE", "$updatedRows rows updated in table ${Task.TABLE_NAME}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun delete(id: Int) {
        try {
            open()

            // Insert the new row, returning the primary key value of the new row
            val deletedRows = db.delete(Task.TABLE_NAME, "${Task.COLUMN_ID} = $id", null)
            Log.i("DATABASE", "$deletedRows rows deleted in table ${Task.TABLE_NAME}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun findBy(id: Int) : Task? {
        var task: Task? = null

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(Task.COLUMN_ID, Task.COLUMN_TITLE, Task.COLUMN_DONE, Task.COLUMN_CATEGORY)

        // Filter results WHERE "title" = 'My Title'
        val selection = "${Task.COLUMN_ID} = $id"
        val selectionArgs = null

        // How you want the results sorted in the resulting Cursor
        val sortOrder = null

        try {
            open()
            val cursor = db.query(
                Task.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
            )

            // Read the cursor data
            if (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_TITLE))
                val done = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_DONE))!=0
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_CATEGORY))
                task = Task(id, title, done, categoryId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }

        return task
    }

}
