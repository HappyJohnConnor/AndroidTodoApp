package com.example.todo4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo4.databinding.FragmentAddTodoBinding

class AddTodoFragment : Fragment() {
    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoListViewModel by activityViewModels()
    private val navigationArgs: EditTodoFragmentArgs by navArgs()
    private lateinit var titleEditText: EditText
    private lateinit var detailEditText: EditText
    private lateinit var saveBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        titleEditText = binding.addTitle
        detailEditText = binding.addDetail
        saveBtn = binding.saveBtn
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //val id = navigationArgs.todoId
        saveBtn.setOnClickListener {
            val title = titleEditText.text.toString()
            val detail = detailEditText.text.toString()
            viewModel.addItem(title, detail)
            findNavController().navigate(R.id.back_to_list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}