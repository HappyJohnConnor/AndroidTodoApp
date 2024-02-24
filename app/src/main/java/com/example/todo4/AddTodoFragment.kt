package com.example.todo4

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.todo4.databinding.FragmentAddTodoBinding
import java.util.Calendar

class AddTodoFragment : Fragment() {
    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoListViewModel by activityViewModels()
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

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_add_todo_fragment, menu)

                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.todo_date -> {
                            val calendar = Calendar.getInstance()

                            val MONTH = calendar[Calendar.MONTH]
                            val YEAR = calendar[Calendar.YEAR]
                            val DAY = calendar[Calendar.DATE]

                            val datePickerDialog = DatePickerDialog(
                                context!!,
                                { view, year, month, dayOfMonth ->
                                    var month = month
                                    month = month + 1
                                }, YEAR, MONTH, DAY
                            )

                            datePickerDialog.show()
                            return true
                        }

                        else -> true

                    }
                }

            },
            viewLifecycleOwner, Lifecycle.State.RESUMED
        )

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