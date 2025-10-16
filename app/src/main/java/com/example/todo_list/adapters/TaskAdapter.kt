package com.example.todo_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.data.Task
import com.example.todo_list.databinding.ItemTaskBinding

    class TaskAdapter(
var items: List<Task>,
val onClickListener: (Int) -> Unit,
val onEditListener: (Int) -> Unit,
val onDoneListener: (Int) -> Unit,
val onDeleteListener: (Int) -> Unit
) : RecyclerView.Adapter<TaskViewHolder>() {

    // Cual es la vista para los elementos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(layoutInflater, parent, false)
        return TaskViewHolder(binding)
    }

    // Cuales son los datos para el elemento
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
        holder.binding.editButton.setOnClickListener {
            onEditListener(position)
        }
        holder.binding.checkbox.setOnCheckedChangeListener { _,_ ->
            if(holder.binding.checkbox.isPressed){
            onDoneListener(position)}
        }
        holder.binding.deleteButton.setOnClickListener {
            onDeleteListener(position)
        }
    }

    // Cuantos elementos se quieren listar?
    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<Task>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(task: Task) {
        binding.titleTextView.text = task.title
    }
}