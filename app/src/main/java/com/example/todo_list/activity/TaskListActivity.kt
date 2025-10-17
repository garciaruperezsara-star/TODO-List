package com.example.todo_list.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todo_list.R
import com.example.todo_list.adapters.TaskAdapter
import com.example.todo_list.data.CategoryDAO
import com.example.todo_list.data.Task
import com.example.todo_list.data.TaskDAO
import com.example.todo_list.databinding.ActivityTaskListBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class TaskListActivity : AppCompatActivity() {

    lateinit var taskAdapter: TaskAdapter
    var taskList: List<Task> = emptyList()
    var categoryId by Delegates.notNull<Int>()

    lateinit var categoryDAO: CategoryDAO
    lateinit var taskDAO: TaskDAO
    lateinit var binding: ActivityTaskListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        categoryDAO = CategoryDAO(this)
        taskDAO = TaskDAO(this)
        categoryId = intent.getIntExtra("CATEGORY_ID", -1)
        taskList = taskDAO.findAllByCategory(categoryId)
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

        binding.recyclerViewTask.adapter = taskAdapter
        binding.categoryText.text = categoryDAO.find(categoryId)?.name



        binding.createButtom.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            intent.putExtra("CATEGORY_ID", categoryId)
            startActivity(intent)
        }
        fun doneFilter(done: Boolean) {
            if (done == true) {
                taskList = taskDAO.findAllByCategoryAndDone(categoryId, true)
                taskAdapter.updateItems(taskList)
            } else if (done == false) {
                loadData()
            }

        }

        var doneSelected: Boolean = false
        var undoneSelected: Boolean = false
        binding.doneCard.setOnClickListener {
            if (doneSelected == true&& undoneSelected == false) {
                doneSelected = false
                doneFilter(doneSelected)
                binding.doneCard.isSelected = doneSelected
                Log.i("CHEK", "done selected is $doneSelected")
                Log.i("CHEK", "undone selected is $undoneSelected")
            } else if (doneSelected == false) {
                doneSelected = true
                doneFilter(doneSelected)
                binding.doneCard.isSelected = doneSelected
                Log.i("CHEK", "done selected is $doneSelected")
                Log.i("CHEK", "undone selected is $undoneSelected")
            } else if (doneSelected == false && undoneSelected == true) {
                doneSelected = true
                undoneSelected = false
                doneFilter(doneSelected)
                binding.doneCard.isSelected = doneSelected
                binding.undoneCard.isSelected = undoneSelected
                Log.i("CHEK", "done selected is $doneSelected")
                Log.i("CHEK", "undone selected is $undoneSelected")
            }
        }
        binding.undoneCard.setOnClickListener {
            if (undoneSelected == true&& doneSelected == false) {
                undoneSelected = false
                doneFilter(undoneSelected)
                binding.undoneCard.isSelected = undoneSelected
                Log.i("CHEK", "done selected is $doneSelected")
                Log.i("CHEK", "undone selected is $undoneSelected")
            } else if (undoneSelected == false) {
                undoneSelected = true
                doneFilter(undoneSelected)
                binding.undoneCard.isSelected = undoneSelected
                Log.i("CHEK", "done selected is $doneSelected")
                Log.i("CHEK", "undone selected is $undoneSelected")
            } else if (undoneSelected == false && doneSelected == true) {
                undoneSelected = true
                doneSelected = false
                doneFilter(undoneSelected)
                binding.undoneCard.isSelected = doneSelected
                binding.undoneCard.isSelected = undoneSelected
                Log.i("CHEK", "done selected is $doneSelected")
                Log.i("CHEK", "undone selected is $undoneSelected")
            }
        }


    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    fun loadData() {
        taskList = taskDAO.findAllByCategory(categoryId)
        taskAdapter.updateItems(taskList)
    }

}
