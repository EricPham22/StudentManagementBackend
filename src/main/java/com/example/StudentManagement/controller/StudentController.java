package com.example.StudentManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.StudentManagement.model.Student;
import com.example.StudentManagement.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	
	//GET endpoint to get all students
	@GetMapping("/get-all-students")
	public ResponseEntity<?> findAllStudents() {
			List<Student> student = studentService.getAllStudents();
			return ResponseEntity.ok(student);
	}
	
	//GET endpoint to get a student by an id
	@GetMapping(value="/find-by-id")
	public ResponseEntity<?> findStudentById(@RequestParam(value="id") int id) {
			Student student = studentService.getStudentById(id);
			return ResponseEntity.ok(student);
	}
		 
	//POST end point to add a new student
	@PostMapping(value="/add-new-student")
	public ResponseEntity<?> addNewStudent(@RequestBody Student student) {
			Student output = studentService.addStudent(student);
			return ResponseEntity.ok(output);
	}
	
	//POST end point to add multiple students
	@PostMapping(value="/add-multiple-new-students")
	public ResponseEntity<?> addMultipleNewStudents(@RequestBody List<Student> student) {
			List<Student> output = studentService.addMultipleStudents(student);
			return ResponseEntity.ok(output);
	}
	
	//PUT end point to update a current students details
	@PutMapping("/update")
	public ResponseEntity<?> updateStudent(@RequestBody Student updatedStudent) {
			Student student = studentService.updateStudentDetails(updatedStudent);
			return ResponseEntity.ok(student);
	}
	
	//DELETE end point to remove a student 
	@DeleteMapping(value = "/delete")
	public ResponseEntity<?> deleteStudent(@RequestParam(value="id") int id) {
			studentService.removeStudent(id);
			return ResponseEntity.ok().build();
	}
	
	//Exception Handler that will return a 204 No Content Status when an exception is thrown from the service
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<HttpStatus> handleExceptions() {
		return ResponseEntity.noContent().build();
		}
	}
