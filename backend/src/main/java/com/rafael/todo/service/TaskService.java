package com.rafael.todo.service;

import com.rafael.todo.entity.Task;
import com.rafael.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    public List<Task> getAllTasksByUser(String userId) {
        return taskRepository.findByUserId(userId);
    }
    
    public Task createTask(Task task) {
        task.setStatus("PENDENTE");
        return taskRepository.save(task);
    }
    
    public Task updateTask(String id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada!"));
        
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        
        return taskRepository.save(task);
    }
    
    public void deleteTask(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada!"));
        taskRepository.delete(task);
    }
}
