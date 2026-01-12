package com.rafael.todo.controller;

import com.rafael.todo.config.JwtUtil;
import com.rafael.todo.entity.Task;
import com.rafael.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private String getUserFromToken(String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer "
        return jwtUtil.getUsernameFromToken(token);
    }
    
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@RequestHeader("Authorization") String authHeader) {
        String username = getUserFromToken(authHeader);
        List<Task> tasks = taskService.getAllTasksByUser(username);
        return ResponseEntity.ok(tasks);
    }
    
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestHeader("Authorization") String authHeader, 
                                           @RequestBody Task task) {
        String username = getUserFromToken(authHeader);
        task.setUserId(username);
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, 
                                           @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(updatedTask);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deletada com sucesso!");
    }
}
