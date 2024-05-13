package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoResource {
	@Autowired
	private TodoService todoService;

	@GetMapping("/users/{username}/todos")
	public List<Todo> retriveTodos(@PathVariable String username) {
		return todoService.findByUsername(username);

	}
	
	@GetMapping("/users/{username}/todos/{id}")
	@PreAuthorize("hasRole('USER' and #username == authentication.name")
	public Todo retriveTodos(@PathVariable String username,
			@PathVariable int id) {
		return todoService.findById(id);
	}
	
	
	@DeleteMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Void> deletTodo(@PathVariable String username, @PathVariable int id){
		todoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/users/{username}/todos/{id}")
	public Todo updateTodo(@PathVariable String username, @PathVariable int id, @RequestBody Todo todo){
		todoService.updateTodo(todo);
		return todo;
		
	}
	
	@PostMapping("/users/{username}/todos")
	public Todo careateTodo(@PathVariable String username, @RequestBody Todo todo){
		Todo cratedTodo = todoService.addTodo(username, 
				todo.getDescription(), todo.getTargetDate(), todo.isDone());
		return cratedTodo;
	}


}
