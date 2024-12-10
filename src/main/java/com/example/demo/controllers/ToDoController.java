package com.example.demo.controllers;

import com.example.demo.models.Task;
import com.example.demo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ToDoController {
    // Auto instancia la interfacee
    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping(value = "/")
    public String holaMundo(){
        return "Hola Mundo";
    }

    @GetMapping(value = "/tasks")
    public List<Task> getTasks(){
        return toDoRepository.findAll();
    }

    @GetMapping(value = "/tasks/{id}")
    public Optional<Task> getTaskById (@PathVariable Long id){
        return toDoRepository.findById(id);
    }


    @PostMapping(value = "/saveTasks")
    public ResponseEntity<Task> saveTask(@RequestBody Task task) {
        System.out.println("Task recibido: " + task);
        Task savedTask = toDoRepository.save(task);
        return ResponseEntity.ok(savedTask);
    }

    @DeleteMapping(value = "/tasks/{id}")
    public void deleteTasks(@PathVariable ("id") Long id){
        toDoRepository.deleteById(id);
    }

    @PutMapping(value = "/tasks/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task){
        Optional<Task> taskEdit = toDoRepository.findById(id);
        if(taskEdit.isPresent()){
            Task task1 = taskEdit.get();
            task1.setTitulo(task.getTitulo());
            task1.setDescripcion(task.getDescripcion());
            Task updatedTask = toDoRepository.save(task1);
            return toDoRepository.save(updatedTask);
        }else {
            return (Task) ResponseEntity.notFound();
        }
    }
    /*Optional porque puede ser que no encuentre nada - is present es como si no fuera null - lueggo los cambios y despues el guardado*/
}
