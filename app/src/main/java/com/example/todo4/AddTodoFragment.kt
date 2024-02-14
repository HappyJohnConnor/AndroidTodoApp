package com.example.todo4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.todo4.databinding.FragmentAddTodoBinding
import com.example.todo4.databinding.FragmentTodoListBinding
import com.example.todo4.model.TodoModel

class AddTodoFragment : Fragment() {
    private var _binding:FragmentAddTodoBinding? = null
    private val binding get() = _binding!!

    private lateinit var titleEditText: EditText
    private lateinit var detailEditText: EditText
    private lateinit var saveBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTodoBinding.inflate(inflater, container, false);
        titleEditText = binding.addTitle
        detailEditText = binding.addDetail
        saveBtn = binding.saveBtn
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveBtn.setOnClickListener {
            val title = titleEditText.text.toString()
            val detail = detailEditText.text.toString()

            val todoModel = TodoModel(title, detail)
            //val bundle = bundleOf("Todo" to todoModel)
            val action = AddTodoFragmentDirections.setTodoAction(todoModel)
            view.findNavController().navigate(action)

        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}