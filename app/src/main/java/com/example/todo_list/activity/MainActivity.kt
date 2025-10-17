package com.example.todo_list.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todo_list.R
import com.example.todo_list.adapters.CategoryAdapter
import com.example.todo_list.adapters.TaskAdapter
import com.example.todo_list.data.Category
import com.example.todo_list.data.CategoryDAO
import com.example.todo_list.data.Task
import com.example.todo_list.data.TaskDAO
import com.example.todo_list.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var categoryAdapter: CategoryAdapter
    lateinit var taskAdapter: TaskAdapter
    var categoryList: List<Category> = emptyList()
    var taskList: List<Task> = emptyList()

    lateinit var categoryDAO: CategoryDAO
    lateinit var taskDAO: TaskDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        categoryDAO = CategoryDAO(this)
        taskDAO = TaskDAO(this)



        categoryAdapter = CategoryAdapter(categoryList, { position ->
            // Click
            val category = categoryList[position]
            val intent = Intent(this, TaskListActivity::class.java)
            intent.putExtra("CATEGORY_ID", category.id)
            startActivity(intent)

        }, { position ->
            // Edit
            val category = categoryList[position]
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("CATEGORY_ID", category.id)
            startActivity(intent)
        }, { position ->
            // Delete
            val category = categoryList[position]

            val dialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_category))
                .setMessage(getString(R.string.delete_check) + "${category.name}?")
                .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                    categoryDAO.delete(category.id)
                    loadData()
                    Snackbar.make(
                        binding.root,
                        getString(R.string.delete_positive_confirm),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    //Toast.makeText(this, getString(R.string.delete_positive_confirm), Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(getString(R.string.no), null)
                .create()

            dialog.show()

        })

        taskAdapter = TaskAdapter(taskList, { position ->
            // Click
        }, { position ->
            // Edit
            val task = taskList[position]
            val intent = Intent(this, TaskActivity::class.java)
            intent.putExtra("TASK_ID", task.id)
            intent.putExtra("CATEGORY_ID", task.categoryId)
            startActivity(intent)
        }, { position ->
            // Edit
            val task = taskList[position]

        }, { position ->
            // Delete
            val task = taskList[position]

            val dialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_category))
                .setMessage(getString(R.string.delete_check) + "${task.title}?")
                .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                    taskDAO.delete(task.id)
                    loadData()
                    Snackbar.make(
                        binding.root,
                        getString(R.string.delete_positive_confirm),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    //Toast.makeText(this, getString(R.string.delete_positive_confirm), Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(getString(R.string.no), null)
                .create()
            dialog.show()
        })

        binding.recyclerViewCategory.adapter = categoryAdapter
        binding.recyclerViewTask.adapter = taskAdapter

        binding.createButtom.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    fun loadData() {
        categoryList = categoryDAO.findAll()
        taskList = taskDAO.findAll()
        categoryAdapter.updateItems(categoryList)
        taskAdapter.updateItems(taskList)

    }
}
