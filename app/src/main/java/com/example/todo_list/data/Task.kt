package com.example.todo_list.data

data class Task(
    val id: Int,
    val title: String,
    val done: Boolean
) {
    companion object {
        const val TABLE_NAME = "Task"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DONE = "done"
        const val COLUMN_CATEGORY_ID = "categoryId"

        const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_TITLE TEXT, " +
                    "$COLUMN_DONE BOOLEAN,"+
                    "$COLUMN_CATEGORY_ID FOREIGN KEY REFERENCES ${Category.TABLE_NAME}(${Category.COLUMN_ID}))"

        const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}