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
import com.example.StudentManagement.model.Student;
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
		List<Student> expectedStudents = new ArrayList<Student>();
		Student student1 = new Student(1, "Rico", 24, "Rico@email.com", "123 bichon ave");
		Student student2 = new Student(2, "Gaston", 36, "Gaston@email.com", "456 Pablo St");
		expectedStudents.add(student1);
		expectedStudents.add(student2);
		when(studentService.getAllStudents()).thenReturn(expectedStudents);
		ResponseEntity<?> output = studentController.findAllStudents();
		verify(studentService, times(1)).getAllStudents();
		assertEquals(expectedStudents, output.getBody());
		assertEquals(HttpStatus.OK, output.getStatusCode());
	}
		
	@Test
	@DisplayName("can get a student by ID")
	void canGetStudentById() {
		List<Student> expectedStudents = new ArrayList<Student>();
		Student student1 = new Student(1, "Rico", 24, "Rico@email.com", "123 bichon ave");
		Student student2 = new Student(2, "Gaston", 36, "Gaston@email.com", "456 Pablo St");
		expectedStudents.add(student1);
		expectedStudents.add(student2);
		when(studentService.getStudentById(any(Integer.class))).thenReturn(student1);
		ResponseEntity<?> output = studentController.findStudentById(1);
		verify(studentService, times(1)).getStudentById(any(Integer.class));
		assertEquals(student1, output.getBody());
		assertEquals(HttpStatus.OK, output.getStatusCode());
	}
	
	
	@Test
	@DisplayName("can add a student")
	void canAddAStudent() {
		Student student = new Student(1, "Rico", 24, "Rico@email.com", "123 bichon ave");
		when(studentService.addStudent(any(Student.class))).thenReturn(student);
		ResponseEntity<?> output = studentController.addNewStudent(student);
		verify(studentService, times(1)).addStudent(any(Student.class));
		assertEquals(student, output.getBody());
		assertEquals(HttpStatus.OK, output.getStatusCode());
	}
	
	@Test
	@DisplayName("can add multiple students")
	void canAddMultipleStudents() {
		List<Student> expectedStudents = new ArrayList<Student>();
		Student student1 = new Student(1, "Rico", 24, "Rico@email.com", "123 bichon ave");
		Student student2 = new Student(2, "Gaston", 36, "Gaston@email.com", "456 Pablo St");
		expectedStudents.add(student1);
		expectedStudents.add(student2);
		when(studentService.addMultipleStudents(new ArrayList<Student>())).thenReturn(expectedStudents);
		ResponseEntity<?> output = studentController.addMultipleNewStudents(new ArrayList<Student>());
		verify(studentService, times(1)).addMultipleStudents(new ArrayList<Student>());
		assertEquals(expectedStudents, output.getBody());
		assertEquals(HttpStatus.OK, output.getStatusCode());
	}
	
	@Test
	@DisplayName("can update property")
	void canUpdateStudentName() {
		Student expectedStudent = new Student(1, "Gaston", 24, "Rico@email.com", "123 bicon ave");
		when(studentService.updateStudentDetails(any(Student.class))).thenReturn(expectedStudent);
		ResponseEntity<?> output = studentController.updateStudent(new Student());
		verify(studentService, times(1)).updateStudentDetails(any(Student.class));
		assertEquals(expectedStudent, output.getBody());
		assertEquals(HttpStatus.OK, output.getStatusCode());
	}
	
	@Test
	@DisplayName("can remove a student")
	void canRemoveStudent() {
		ResponseEntity<?> output = studentController.deleteStudent(1);
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
