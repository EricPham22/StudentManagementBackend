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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.StudentManagement.model.Students;
import com.example.StudentManagement.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@GetMapping("/Students")
	public ResponseEntity<List<Students>> findAllStudents() {
			List<Students> students = studentService.getAllStudents();
			return new ResponseEntity<>(students, HttpStatus.OK);
	}
	
	@GetMapping(value="/Students/findById")
	public ResponseEntity<Students> findStudentById(@RequestParam(value="id") int id) {
			Students student = studentService.getStudentById(id);
			return new ResponseEntity<>(student, HttpStatus.OK);
	}
		 
	@PostMapping(value="/Students/AddNewStudent")
	public ResponseEntity<Students> addNewStudent(@RequestBody Students student) {
			Students output = studentService.addStudent(student);
			return new ResponseEntity<Students>(output, HttpStatus.OK);
	}
	
	@PostMapping(value="/Students/AddMultipleNewStudents")
	public ResponseEntity<List<Students>> addMultipleNewStudents(@RequestBody List<Students> students) {
			List<Students> output = studentService.addMultipleStudents(students);
			return new ResponseEntity<List<Students>>(output, HttpStatus.OK);
	}
	
	@PutMapping("Students/Update")
	public ResponseEntity<Students> updateStudent(@RequestBody Students updatedStudent) {
			Students student = studentService.updateStudentDetails(updatedStudent);
			return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/Students/Delete")
	public ResponseEntity<HttpStatus> deleteStudent(@RequestParam(value="id") int id) {
			studentService.removeStudent(id);
			return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<HttpStatus> handleExceptions() {
		return ResponseEntity.noContent().build();
		}
	}
