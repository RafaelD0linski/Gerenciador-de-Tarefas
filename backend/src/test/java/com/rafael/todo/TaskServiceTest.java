package com.rafael.todo;

import com.rafael.todo.entity.Task;
import com.rafael.todo.repository.TaskRepository;
import com.rafael.todo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceTest {
    
    @Mock
    private TaskRepository taskRepository;
    
    @InjectMocks
    private TaskService taskService;
    
    @Test
    void testCreateTask() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setUserId("user123");
        
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        
        Task created = taskService.createTask(task);
        
        assertNotNull(created);
        assertEquals("PENDENTE", created.getStatus());
        verify(taskRepository, times(1)).save(task);
    }
    
    @Test
    void testGetAllTasksByUser() {
        Task task1 = new Task();
        task1.setUserId("user123");
        Task task2 = new Task();
        task2.setUserId("user123");
        
        when(taskRepository.findByUserId("user123")).thenReturn(Arrays.asList(task1, task2));
        
        List<Task> tasks = taskService.getAllTasksByUser("user123");
        
        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findByUserId("user123");
    }
    
    @Test
    void testUpdateTask() {
        Task existingTask = new Task();
        existingTask.setId("1");
        existingTask.setTitle("Old Title");
        
        Task updatedTask = new Task();
        updatedTask.setTitle("New Title");
        updatedTask.setStatus("CONCLUIDA");
        
        when(taskRepository.findById("1")).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);
        
        Task result = taskService.updateTask("1", updatedTask);
        
        assertEquals("New Title", result.getTitle());
        assertEquals("CONCLUIDA", result.getStatus());
    }
    
    @Test
    void testDeleteTask() {
        Task task = new Task();
        task.setId("1");
        
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);
        
        taskService.deleteTask("1");
        
        verify(taskRepository, times(1)).delete(task);
    }
}
