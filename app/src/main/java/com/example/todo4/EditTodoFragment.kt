package com.example.todo4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo4.data.Todo
import com.example.todo4.databinding.FragmentEditTodoBinding

class EditTodoFragment : Fragment() {
    private var _binding: FragmentEditTodoBinding? = null
    private val binding get() = _binding!!
    private val navigationArgs: EditTodoFragmentArgs by navArgs()
    private val viewModel: TodoListViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todoId = navigationArgs.todoId
        viewModel.retrieveItem(navigationArgs.todoId).apply {
            bind(this)
        }

        binding.saveBtn.setOnClickListener {
            viewModel.updateItem(
                Todo(
                    todoId,
                    binding.etTitle.text.toString(),
                    binding.etDetail.text.toString()
                )
            )
            findNavController().navigate(R.id.action_editTodoFragment_to_todoListFragment)
        }
    }

    private fun bind(item: Todo) {
        binding.apply {
            etTitle.setText(item.title)
            etDetail.setText(item.detail)
        }
    }
}