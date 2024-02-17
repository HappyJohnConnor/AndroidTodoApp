package com.example.todo4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import com.example.todo4.data.Todo

class TodoListViewModel() : ViewModel(){
    private val todosRaw = mutableListOf<Todo>()

    private val _todos = MutableLiveData<List<Todo>>(emptyList())
    private var index = 0L
    val todos:LiveData<List<Todo>> = _todos.distinctUntilChanged()

    fun addItem(title:String, detail:String){
        val newTodo = Todo(index,title, detail);
        todosRaw.add(newTodo)
        _todos.value = ArrayList(todosRaw)

        index += 1
    }

}