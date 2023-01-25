package com.example.StudentManagement.UT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.StudentManagement.controller.StudentController;
import com.example.StudentManagement.model.Students;
import com.example.StudentManagement.service.StudentService;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTests {

	@Mock
	private StudentService studentService;
	
	@InjectMocks
	private StudentController studentController;
	
	@Test
	@DisplayName("can get all students")
	void canGetAllStudents() {
		List<Students> expectedStudents = new ArrayList<Students>();
		Students student1 = new Students(1, "Rico", 24, "Rico@email.com", "123 bichon ave");
		Students student2 = new Students(2, "Gaston", 36, "Gaston@email.com", "456 Pablo St");
		expectedStudents.add(student1);
		expectedStudents.add(student2);
		when(studentService.getAllStudents()).thenReturn(expectedStudents);
		ResponseEntity<List<Students>> output = studentController.findAllStudents();
		verify(studentService, times(1)).getAllStudents();
		assertEquals(expectedStudents, output.getBody());
		assertEquals(HttpStatus.OK, output.getStatusCode());
	}
		
	@Test
	@DisplayName("can get a student by ID")
	void canGetStudentById() {
		List<Students> expectedStudents = new ArrayList<Students>();
		Students student1 = new Students(1, "Rico", 24, "Rico@email.com", "123 bichon ave");
		Students student2 = new Students(2, "Gaston", 36, "Gaston@email.com", "456 Pablo St");
		expectedStudents.add(student1);
		expectedStudents.add(student2);
		when(studentService.getStudentById(any(Integer.class))).thenReturn(student1);
		ResponseEntity<Students> output = studentController.findStudentById(1);
		verify(studentService, times(1)).getStudentById(any(Integer.class));
		assertEquals(student1, output.getBody());
		assertEquals(HttpStatus.OK, output.getStatusCode());
	}
	
	
	@Test
	@DisplayName("can add a student")
	void canAddAStudent() {
		Students student = new Students(1, "Rico", 24, "Rico@email.com", "123 bichon ave");
		when(studentService.addStudent(any(Students.class))).thenReturn(student);
		ResponseEntity<Students> output = studentController.addNewStudent(student);
		verify(studentService, times(1)).addStudent(any(Students.class));
		assertEquals(student, output.getBody());
		assertEquals(HttpStatus.OK, output.getStatusCode());
	}
	
	@Test
	@DisplayName("can add multiple students")
	void canAddMultipleStudents() {
		List<Students> expectedStudents = new ArrayList<Students>();
		Students student1 = new Students(1, "Rico", 24, "Rico@email.com", "123 bichon ave");
		Students student2 = new Students(2, "Gaston", 36, "Gaston@email.com", "456 Pablo St");
		expectedStudents.add(student1);
		expectedStudents.add(student2);
		when(studentService.addMultipleStudents(new ArrayList<Students>())).thenReturn(expectedStudents);
		ResponseEntity<List<Students>> output = studentController.addMultipleNewStudents(new ArrayList<Students>());
		verify(studentService, times(1)).addMultipleStudents(new ArrayList<Students>());
		assertEquals(expectedStudents, output.getBody());
		assertEquals(HttpStatus.OK, output.getStatusCode());
	}
	
	@Test
	@DisplayName("can update property")
	void canUpdateStudentName() {
		Students expectedStudent = new Students(1, "Gaston", 24, "Rico@email.com", "123 bicon ave");
		when(studentService.updateStudentDetails(any(Students.class))).thenReturn(expectedStudent);
		ResponseEntity<Students> output = studentController.updateStudent(new Students());
		verify(studentService, times(1)).updateStudentDetails(any(Students.class));
		assertEquals(expectedStudent, output.getBody());
		assertEquals(HttpStatus.OK, output.getStatusCode());
	}
	
	@Test
	@DisplayName("can remove a student")
	void canRemoveStudent() {
		ResponseEntity<HttpStatus> output = studentController.deleteStudent(1);
		verify(studentService, times(1)).removeStudent(1);
		assertEquals(HttpStatus.OK, output.getStatusCode());
	}	
	
	@Test
	@DisplayName("returns status code 204 No Content")
	void canHandleExceptions() {
		ResponseEntity<HttpStatus> output = studentController.handleExceptions();
		assertEquals(HttpStatus.NO_CONTENT, output.getStatusCode());
	}
	
}
