package com.example.todo4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo4.data.Todo
import com.example.todo4.databinding.FragmentEditTodoBinding
import com.example.todo4.utility.DatePickerDialogFragment
import java.text.SimpleDateFormat
import java.util.Date

class EditTodoFragment : Fragment() {
    private var _binding: FragmentEditTodoBinding? = null
    private val binding get() = _binding!!
    private var dueDate: Date? = null
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

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_add_todo_fragment, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.todo_date -> {
                            val calDialogFragment = DatePickerDialogFragment()
                            val bundle = Bundle().apply {
                                if (dueDate != null) {
                                    putLong("time", dueDate!!.time)
                                }
                            }
                            calDialogFragment.arguments = bundle
                            calDialogFragment.show(childFragmentManager, "my_dialog")

                            childFragmentManager.setFragmentResultListener(
                                DatePickerDialogFragment.FRAGMENT_KEY,
                                viewLifecycleOwner,
                            ) { _, result: Bundle ->
                                val timeMills =
                                    result.getLong(DatePickerDialogFragment.BUNDLE_KEY_DUEDATE, 0)

                                if (timeMills != 0L) {
                                    dueDate = Date(timeMills)
                                    setDateToTextView(Date(timeMills))
                                }
                            }

                            return false
                        }

                        else -> false

                    }
                }

            },
            viewLifecycleOwner, Lifecycle.State.RESUMED
        )

        binding.saveBtn.setOnClickListener {
            viewModel.updateItem(
                Todo(
                    todoId,
                    binding.etTitle.text.toString(),
                    binding.etDetail.text.toString(),
                    dueDate
                )
            )
            findNavController().navigate(R.id.action_editTodoFragment_to_todoListFragment)
        }
    }

    private fun bind(item: Todo) {
        binding.apply {
            etTitle.setText(item.title)
            etDetail.setText(item.detail)
            if (item.dueDate != null) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                editTvDue.text = dateFormat.format(item.dueDate)
            }
        }
    }

    private fun setDateToTextView(date: Date) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        binding.editTvDue.text = dateFormat.format(date)
    }
}