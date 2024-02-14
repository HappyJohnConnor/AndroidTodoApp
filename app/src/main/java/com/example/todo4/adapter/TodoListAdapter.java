package com.example.todo4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo4.R;
import com.example.todo4.model.TodoModel;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {
    private List<TodoModel> todoList;

    public TodoListAdapter(List<TodoModel> todoList) {
        this.todoList = todoList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            detail = itemView.findViewById(R.id.item_detail);
        }
    }

    @NonNull
    @Override
    public TodoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListAdapter.ViewHolder holder, int position) {
        TodoModel todo = todoList.get(position);
        holder.title.setText(todo.getTitle());
        holder.detail.setText(todo.getDetail());
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }


}
