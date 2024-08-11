package com.android.tools.android_assignment_3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;  // Import ArrayList
import java.util.Comparator;
import java.util.PriorityQueue;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private PriorityQueue<Task> taskQueue;

    private Button btnAddTask;
    private EditText editTextTaskName, editTextPriority, editTextDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewTasks);
        btnAddTask = findViewById(R.id.btnAddTask);
        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextPriority = findViewById(R.id.editTextPriority);
        editTextDelay = findViewById(R.id.editTextDelay);

        taskAdapter = new TaskAdapter();
        taskQueue = new PriorityQueue<>(Comparator.comparingInt(Task::getPriority).thenComparingLong(Task::getExecutionTime));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    private void addTask() {
        String taskName = editTextTaskName.getText().toString();
        String priorityString = editTextPriority.getText().toString();
        String delayString = editTextDelay.getText().toString();

        if (!taskName.isEmpty() && !priorityString.isEmpty() && !delayString.isEmpty()) {
            int priority = Integer.parseInt(priorityString);
            long delay = Long.parseLong(delayString);

            Task newTask = new Task(taskName, priority, delay);
            taskQueue.add(newTask);
            taskAdapter.setTasks(new ArrayList<>(taskQueue));
            scheduleTaskNotifications();
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void scheduleTaskNotifications() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PriorityQueue<Task> sortedTasks = new PriorityQueue<>(taskQueue);

                while (!sortedTasks.isEmpty()) {
                    Task task = sortedTasks.poll();

                    try {
                        Thread.sleep(task.getExecutionTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            showTaskNotification(task);
                        }
                    });
                }
            }
        }).start();
    }

    private void showTaskNotification(Task task) {
        String notificationMessage = "Reminder: " + task.getName() + " (Priority: " + task.getPriority() + ")";
        Toast.makeText(MainActivity.this, notificationMessage, Toast.LENGTH_SHORT).show();
    }
}
