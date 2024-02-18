package com.example.todo4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import com.example.todo4.data.Todo

class TodoListViewModel : ViewModel() {
    private val todosRaw = mutableListOf<Todo>()

    private val _todos = MutableLiveData<List<Todo>>(emptyList())
    private var index: Int = 0
    val todos: LiveData<List<Todo>> = _todos.distinctUntilChanged()

    fun retrieveItem(id: Int): Todo {
        lateinit var item: Todo

        todos.value?.onEach {
            if (it.id == id) item = it
        }
        return item
    }

    fun addItem(title: String, detail: String) {
        val newTodo = Todo(index, title, detail)
        todosRaw.add(newTodo)
        _todos.value = ArrayList(todosRaw)
        index += 1
    }

    fun updateItem(item: Todo) {
        val index = todosRaw.indexOfFirst { it.id == item.id }

        if (index != -1) {
            todosRaw[index] = item
            _todos.value = ArrayList(todosRaw)
        }
    }

    fun getItemIndex(item: Todo) {}

}