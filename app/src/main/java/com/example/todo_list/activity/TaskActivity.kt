package com.example.todo_list.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todo_list.R
import com.example.todo_list.data.Task
import com.example.todo_list.data.TaskDAO
import com.example.todo_list.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity() {
    lateinit var task : Task
    lateinit var taskDAO : TaskDAO
    lateinit var binding: ActivityTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val task_id = intent.getIntExtra("TASK_ID", -1)
        val category_id = intent.getIntExtra("CATEGORY_ID", -1)

        taskDAO = TaskDAO(this)

        if (task_id != -1) {
            // Edit
            task = taskDAO.findById(task_id)!!
        } else {
            // Crear
            task = Task(-1, "", false, category_id)
        }

        binding.nameEditText.editText?.setText(task.title)

        binding.saveButton.setOnClickListener {
            val title = binding.nameEditText.editText?.text.toString()

            task.title = title

            if (task.id == -1) {
                taskDAO.insert(task,category_id)
                Log.i("CREATE", category_id.toString())
            } else {
                taskDAO.update(task,category_id)
                Log.i("CREATE", category_id.toString())
            }

            finish()
        }
    }
    }
