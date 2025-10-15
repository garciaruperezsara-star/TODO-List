package com.example.todo_list

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todo_list.data.Category
import com.example.todo_list.data.CategoryDAO
import com.example.todo_list.databinding.ActivityCategoryBinding


class CategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryBinding

    lateinit var categoryDAO: CategoryDAO
    lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getIntExtra("CATEGORY_ID", -1)

        categoryDAO = CategoryDAO(this)

        if (id != -1) {
            // Edit
            category = categoryDAO.find(id)!!
        } else {
            // Crear
            category = Category(-1, "")
        }

        binding.nameEditText.editText?.setText(category.name)

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.editText?.text.toString()

            category.name = name

            if (category.id == -1) {
                categoryDAO.insert(category)
            } else {
                categoryDAO.update(category)
            }

            finish()
        }
    }
}