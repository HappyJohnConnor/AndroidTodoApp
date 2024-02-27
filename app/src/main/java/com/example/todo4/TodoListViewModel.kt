package com.example.todo4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import com.example.todo4.data.Todo
import java.util.Date

class TodoListViewModel : ViewModel() {
    private val todosRaw =
        mutableListOf<Todo>(
            Todo(1, "Title1", "Detail2", null),
            Todo(2, "Title2", "Detail2", Date())
        )

    private val _todos = MutableLiveData<List<Todo>>(
        emptyList()
    )
    private var index: Int = 2
    val todos: LiveData<List<Todo>> = _todos.distinctUntilChanged()

    init {
        _todos.value = todosRaw
    }

    fun retrieveItem(id: Int): Todo {
        lateinit var item: Todo

        todos.value?.onEach {
            if (it.id == id) item = it
        }
        return item
    }

    fun addItem(title: String, detail: String, due: Date?) {
        val newTodo = Todo(index, title, detail, due)
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

    fun deleteItem(item: Todo) {
        todosRaw.remove(item)
        _todos.value = ArrayList(todosRaw)
    }

    fun deleteItem(position: Int) {
        todosRaw.removeAt(position)
        _todos.value = ArrayList(todosRaw)
    }

    fun getItemIndex(item: Todo) {}

}