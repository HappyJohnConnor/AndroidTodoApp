package com.example.todo4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo4.databinding.FragmentTodoListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var addBtn: FloatingActionButton

    private val viewModel: TodoListViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        addBtn = binding.addBtn
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TodoListAdapter {

            val action = TodoListFragmentDirections.actionTodoListFragmentToEditTodoFragment(it.id)

            this.findNavController().navigate(action)
        }

        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = GridLayoutManager(
            context,
            2,
            RecyclerView.VERTICAL,
            false
        )

        //recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        addBtn.setOnClickListener {
            this.findNavController().navigate(R.id.list_to_add)
        }

        viewModel.todos.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val fromPos = viewHolder.bindingAdapterPosition
                    val toPos = target.bindingAdapterPosition
                    adapter.notifyItemMoved(fromPos, toPos)
                    return true // true if moved, false otherwise
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.bindingAdapterPosition
                    if (direction == ItemTouchHelper.LEFT) {
                        viewModel.deleteItem(position)
                        adapter.notifyItemRemoved(position)

                    }
                }
            })
        mIth.attachToRecyclerView(recyclerView)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}