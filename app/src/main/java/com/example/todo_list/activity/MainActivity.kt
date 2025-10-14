package com.example.todo_list.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todo_list.R
import com.example.todo_list.data.Category
import com.example.todo_list.data.CategoryDAO
import com.example.todo_list.data.TaskDAO
import com.example.todo_list.databinding.ActivityMainBinding
import com.example.todo_list.utils.DatabaseManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var binding: ActivityMainBinding
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val categoryDAO = CategoryDAO(this)
        val taskDAO = TaskDAO(this)
        //createCategories()

    }

    fun createCategories() {
        val category1 = Category(-1, getString(R.string.category1))
        val category2 = Category(-1, getString(R.string.category2))
        val category3 = Category(-1, getString(R.string.category3))
        val category4 = Category(-1, getString(R.string.category4))
    }
}
