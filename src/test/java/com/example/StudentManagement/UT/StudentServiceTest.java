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

import com.example.StudentManagement.model.Student;
import com.example.StudentManagement.repository.StudentRepository;
import com.example.StudentManagement.service.StudentService;


@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock
	StudentRepository studentRepo;
	
	@InjectMocks
	private StudentService studentService;
	
	@Test
	@DisplayName("Can add a student")
	void canAddStudent() {
		Student student = new Student();
		when(studentRepo.save(any(Student.class))).thenReturn(student);
		Student studentOutput = studentService.addStudent(new Student());
		verify(studentRepo, times(1)).save(any(Student.class));
		assertEquals(student, studentOutput);
	}
		
	@Test
	@DisplayName("Can add multiple students")
	void canAddMultipleStudents() {
		List<Student> student = new ArrayList<Student>();
		when(studentRepo.saveAll(student)).thenReturn(student);
		List<Student> studentOutput = studentService.addMultipleStudents(new ArrayList<Student>());
		verify(studentRepo, times(1)).saveAll(student);
		assertEquals(student, studentOutput);
	}
	
	@Test
	@DisplayName("Can get all students") 
	void canGetAllStudents() {
		Student student1 = new Student();
		Student student2 = new Student();
		List<Student> student = new ArrayList<Student>();
		student.add(student1);
		student.add(student2);
		when(studentRepo.findAll()).thenReturn(student);
		List<Student> studentOutput = studentService.getAllStudents();
		verify(studentRepo, times(1)).findAll();
		assertEquals(student, studentOutput);
		
	}
	
	@Test
	@DisplayName("Can get a student by ID")
	void canGetStudentById() {
		Student student = new Student();
		student.setStudentId(1);
		Optional<Student> expectedStudent = Optional.of(student);
		when(studentRepo.findById(any(Integer.class))).thenReturn(expectedStudent);
		Student studentOutput = studentService.getStudentById(1);
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
		private Student student;
		@BeforeEach() 
		void beforeEach() {
			student = new Student(1, "Rico", 24, "email@email.com", "123 address ave");
			Optional<Student> targetStudent = Optional.of(student);
			when(studentRepo.findById(any(Integer.class))).thenReturn(targetStudent);
		}
		
		@Test
		@DisplayName("name")
		void canUpdateName() {
			Student updatedStudent = new Student(1, "Pablo", 24, "email@email.com", "123 address ave");
			when(studentRepo.save(any(Student.class))).thenReturn(updatedStudent);
			Student output = studentService.updateStudentDetails(updatedStudent);
			verify(studentRepo, times(1)).save(any(Student.class));
			assertEquals(updatedStudent.getName(), output.getName());
			assertEquals(student.getName(), updatedStudent.getName());
		}
		
		@Test
		@DisplayName("age")
		void canUpdateAge() {
			Student updatedStudent = new Student(1, "Rico", 2, "email@email.com", "123 address ave");
			when(studentRepo.save(any(Student.class))).thenReturn(updatedStudent);
			Student output = studentService.updateStudentDetails(updatedStudent);
			verify(studentRepo, times(1)).save(any(Student.class));
			assertEquals(updatedStudent.getAge(), output.getAge());
			assertEquals(student.getAge(), updatedStudent.getAge());
		}
		
		@Test
		@DisplayName("email")
		void canUpdateEmail() {
			Student updatedStudent = new Student(1, "Rico", 24, "paper@paper.com", "123 address ave");
			when(studentRepo.save(any(Student.class))).thenReturn(updatedStudent);
			Student output = studentService.updateStudentDetails(updatedStudent);
			verify(studentRepo, times(1)).save(any(Student.class));
			assertEquals(updatedStudent.getEmail(), output.getEmail());
			assertEquals(student.getEmail(), updatedStudent.getEmail());
		}
		
		@Test
		@DisplayName("address")
		void canUpdateAddress() {
			Student updatedStudent = new Student(1, "Rico", 24, "email@email.com", "123 Bichon ave");
			when(studentRepo.save(any(Student.class))).thenReturn(updatedStudent);
			Student output = studentService.updateStudentDetails(updatedStudent);
			verify(studentRepo, times(1)).save(any(Student.class));
			assertEquals(updatedStudent.getAddress(), output.getAddress());
			assertEquals(student.getAddress(), updatedStudent.getAddress());
		}
		
		@Test
		@DisplayName("all details")
		void canUpdateAll() {
			Student updatedStudent = new Student(1, "Pablo", 2, "pablo@email.com", "123 Bichon ave");
			when(studentRepo.save(any(Student.class))).thenReturn(updatedStudent);
			Student output = studentService.updateStudentDetails(updatedStudent);
			verify(studentRepo, times(1)).save(any(Student.class));
			assertEquals(updatedStudent, output);
			assertEquals(student.getAddress(), updatedStudent.getAddress());
			assertEquals(student.getEmail(), updatedStudent.getEmail());
			assertEquals(student.getAge(), updatedStudent.getAge());
			assertEquals(student.getName(), updatedStudent.getName());
		}
	}
}
