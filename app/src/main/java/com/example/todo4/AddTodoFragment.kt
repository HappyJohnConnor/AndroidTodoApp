package com.example.todo4

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
import com.example.todo4.utility.DatePickerDialogFragment
import java.text.SimpleDateFormat
import java.util.Date

class AddTodoFragment : Fragment() {
    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoListViewModel by activityViewModels()
    private lateinit var titleEditText: EditText
    private lateinit var detailEditText: EditText
    private lateinit var saveBtn: Button
    private var dueDate: Date? = null

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
        var dueDate: Date? = null

        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_add_todo_fragment, menu)

                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.todo_date -> {
                            val calDialogFragment = DatePickerDialogFragment()
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

        saveBtn.setOnClickListener {
            val title = titleEditText.text.toString()
            val detail = detailEditText.text.toString()
            viewModel.addItem(title, detail, dueDate)
            findNavController().navigate(R.id.back_to_list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDateToTextView(date: Date) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        binding.dueDate.text = dateFormat.format(date)
    }
}