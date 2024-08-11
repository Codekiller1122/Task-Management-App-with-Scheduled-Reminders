package com.android.tools.android_assignment_3;

public class Task implements Comparable<Task> {

    private String name;
    private int priority;
    private long executionTime; // in milliseconds

    public Task(String name, int priority, long executionTime) {
        this.name = name;
        this.priority = priority;
        this.executionTime = executionTime;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    @Override
    public int compareTo(Task anotherTask) {
        // Compare tasks based on priority, but in descending order (higher priority first)
        return Integer.compare(anotherTask.getPriority(), this.priority);
    }
}
