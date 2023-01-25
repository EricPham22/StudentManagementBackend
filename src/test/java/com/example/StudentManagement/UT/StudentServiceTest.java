package com.example.StudentManagement.UT;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.StudentManagement.model.Students;
import com.example.StudentManagement.repository.StudentRepository;
import com.example.StudentManagement.service.StudentService;


@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock
	StudentRepository studentRepo;
	
	@InjectMocks
	private StudentService studentService;
	
	
	@BeforeEach
	void beforeEach() {
		studentService = new StudentService(studentRepo);
	}
	
	@Test
	@DisplayName("Can add a student")
	void canAddStudent() {
		Students student = new Students();
		when(studentRepo.save(any(Students.class))).thenReturn(student);
		Students studentOutput = studentService.addStudent(new Students());
		verify(studentRepo, times(1)).save(any(Students.class));
		assertEquals(student, studentOutput);
	}
		
	@Test
	@DisplayName("Can add multiple students")
	void canAddMultipleStudents() {
		List<Students> students = new ArrayList<Students>();
		when(studentRepo.saveAll(students)).thenReturn(students);
		List<Students> studentOutput = studentService.addMultipleStudents(new ArrayList<Students>());
		verify(studentRepo, times(1)).saveAll(students);
		assertEquals(students, studentOutput);
	}
	
	@Test
	@DisplayName("Can get all students") 
	void canGetAllStudents() {
		Students student1 = new Students();
		Students student2 = new Students();
		List<Students> students = new ArrayList<Students>();
		students.add(student1);
		students.add(student2);
		when(studentRepo.findAll()).thenReturn(students);
		List<Students> studentOutput = studentService.getAllStudents();
		verify(studentRepo, times(1)).findAll();
		assertEquals(students, studentOutput);
		
	}
	
	@Test
	@DisplayName("Can get a student by ID")
	void canGetStudentById() {
		Students student = new Students();
		student.setStudentId(1);
		Optional<Students> expectedStudent = Optional.of(student);
		when(studentRepo.findById(any(Integer.class))).thenReturn(expectedStudent);
		Students studentOutput = studentService.getStudentById(1);
		verify(studentRepo, times(1)).findById(any(Integer.class));
		assertEquals(expectedStudent.get(), studentOutput);
		assertEquals(studentOutput, student);
		assertEquals(studentOutput.getStudentId(), 1);
	}	
	
	@Test
	@DisplayName("canGetStudentById throws NoSuchElementException")
	void throwsNoSuchElementException() {
		Throwable error = assertThrows(NoSuchElementException.class, () -> {
			studentService.getStudentById(0);
		});
		assertEquals(NoSuchElementException.class, error.getClass());
	}
	
	@Test
	@DisplayName("Can remove a student")
	void canRemoveStudent() {
		studentService.removeStudent(1);
		verify(studentRepo, times(1)).deleteById(1);
	}
		
	@Nested
	@DisplayName("Can update")
	class CanUpdate {
		private Students student;
		@BeforeEach() 
		void beforeEach() {
			student = new Students(1, "Rico", 24, "email@email.com", "123 address ave");
			Optional<Students> targetStudent = Optional.of(student);
			when(studentRepo.findById(any(Integer.class))).thenReturn(targetStudent);
		}
		
		@Test
		@DisplayName("name")
		void canUpdateName() {
			Students updatedStudent = new Students(1, "Pablo", 24, "email@email.com", "123 address ave");
			when(studentRepo.save(any(Students.class))).thenReturn(updatedStudent);
			Students output = studentService.updateStudentDetails(updatedStudent);
			verify(studentRepo, times(1)).save(any(Students.class));
			assertEquals(updatedStudent.getName(), output.getName());
			assertEquals(student.getName(), updatedStudent.getName());
		}
		
		@Test
		@DisplayName("age")
		void canUpdateAge() {
			Students updatedStudent = new Students(1, "Rico", 2, "email@email.com", "123 address ave");
			when(studentRepo.save(any(Students.class))).thenReturn(updatedStudent);
			Students output = studentService.updateStudentDetails(updatedStudent);
			verify(studentRepo, times(1)).save(any(Students.class));
			assertEquals(updatedStudent.getAge(), output.getAge());
			assertEquals(student.getAge(), updatedStudent.getAge());
		}
		
		@Test
		@DisplayName("email")
		void canUpdateEmail() {
			Students updatedStudent = new Students(1, "Rico", 24, "paper@paper.com", "123 address ave");
			when(studentRepo.save(any(Students.class))).thenReturn(updatedStudent);
			Students output = studentService.updateStudentDetails(updatedStudent);
			verify(studentRepo, times(1)).save(any(Students.class));
			assertEquals(updatedStudent.getEmail(), output.getEmail());
			assertEquals(student.getEmail(), updatedStudent.getEmail());
		}
		
		@Test
		@DisplayName("address")
		void canUpdateAddress() {
			Students updatedStudent = new Students(1, "Rico", 24, "email@email.com", "123 Bichon ave");
			when(studentRepo.save(any(Students.class))).thenReturn(updatedStudent);
			Students output = studentService.updateStudentDetails(updatedStudent);
			verify(studentRepo, times(1)).save(any(Students.class));
			assertEquals(updatedStudent.getAddress(), output.getAddress());
			assertEquals(student.getAddress(), updatedStudent.getAddress());
		}
		
		@Test
		@DisplayName("all details")
		void canUpdateAll() {
			Students updatedStudent = new Students(1, "Pablo", 2, "pablo@email.com", "123 Bichon ave");
			when(studentRepo.save(any(Students.class))).thenReturn(updatedStudent);
			Students output = studentService.updateStudentDetails(updatedStudent);
			verify(studentRepo, times(1)).save(any(Students.class));
			assertEquals(updatedStudent, output);
			assertEquals(student.getAddress(), updatedStudent.getAddress());
			assertEquals(student.getEmail(), updatedStudent.getEmail());
			assertEquals(student.getAge(), updatedStudent.getAge());
			assertEquals(student.getName(), updatedStudent.getName());
		}
	}
}
