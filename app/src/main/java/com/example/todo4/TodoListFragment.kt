package com.example.todo4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo4.adapter.TodoListAdapter
import com.example.todo4.databinding.FragmentTodoListBinding
import com.example.todo4.model.TodoModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!
    private val args: TodoListFragmentArgs by navArgs()
    private lateinit var  recyclerView: RecyclerView
    private lateinit var addBtn:FloatingActionButton
    private var todoList:MutableList<TodoModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        addBtn = binding.addBtn
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        recyclerView.setHasFixedSize(true);
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = TodoListAdapter(todoList)
        recyclerView.adapter = adapter

        addBtn.setOnClickListener {
            navController.navigate(R.id.action_todoListFragment_to_addTodoFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}