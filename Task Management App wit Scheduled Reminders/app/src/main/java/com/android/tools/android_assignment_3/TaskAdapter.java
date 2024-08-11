package com.android.tools.android_assignment_3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }

    // Inside TaskAdapter.TaskViewHolder
    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskNameTextView;
        private final TextView priorityTextView;
        private final TextView executionTimeTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.textViewTaskName);
            priorityTextView = itemView.findViewById(R.id.textViewTaskPriority);
            executionTimeTextView = itemView.findViewById(R.id.editTextDelay);
        }

        public void bind(Task task) {
            taskNameTextView.setText(task.getName());
            priorityTextView.setText("Priority: " + task.getPriority());

            // Check if executionTimeTextView is not null before setting the text
            if (executionTimeTextView != null) {
                executionTimeTextView.setText("Execution Time: " + task.getExecutionTime() + " ms");
            }
        }
    }

}

