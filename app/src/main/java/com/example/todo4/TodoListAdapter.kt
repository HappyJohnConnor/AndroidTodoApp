package com.example.todo4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo4.data.Todo
import com.example.todo4.databinding.TodoItemBinding
import java.text.SimpleDateFormat

class TodoListAdapter(private val onItemClicked: (Todo) -> Unit) :
    ListAdapter<Todo, TodoListAdapter.ItemViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            TodoItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.itemTitle.text = todo.title
            binding.itemDetail.text = todo.detail
            if (todo.dueDate != null) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")

                binding.listTvDeu.text = dateFormat.format(todo.dueDate)
            }
        }

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldTodo: Todo, newTodo: Todo): Boolean {
                return oldTodo === newTodo
            }

            override fun areContentsTheSame(oldTodo: Todo, newTodo: Todo): Boolean {
                return oldTodo.id == newTodo.id
            }
        }
    }
}