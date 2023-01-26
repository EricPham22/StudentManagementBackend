package com.example.StudentManagement.UT;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.example.StudentManagement.model.Student;

class StudentTests {
		
	@DisplayName("Can get property")
	@Nested
	class GetStudentTests {
		
		private Student student;
		
		@BeforeEach
		void beforeEach() {
			student = new Student(1, "Rico", 24, "email@email.com", "123 address ave");
		}
		
		@Test
		@DisplayName("Student ID")
		void testGetStudentID() {
			int id = student.getStudentId();
			assertEquals(id, 1);
		}
		
		@Test
		@DisplayName("Name")
		void testGetName() {
			String name = student.getName();
			assertEquals(name, "Rico");
		}
		
		@Test
		@DisplayName("Age")
		void testGetAge() {
			int age = student.getAge();
			assertEquals(age, 24);
		}
		
		@Test
		@DisplayName("Email")
		void testGetEmail() {
			String email = student.getEmail();
			assertEquals(email, "email@email.com");
		}
		
		@Test
		@DisplayName("Address")
		void testGetAddress() {
			String address = student.getAddress();
			assertEquals(address, "123 address ave");
		}
	}
	
	@DisplayName("Can set property")
	@Nested
	class SetStudentTests {
		
		private Student student;
		
		@BeforeEach
		void beforeEach() {
			student = new Student();
		}
		
		@Test
		@DisplayName("Student ID")
		void testSetStudentID() {
			student.setStudentId(1);
			assertEquals(student.getStudentId(), 1);
		}
		
		@Test
		@DisplayName("Name")
		void testSetName() {
			student.setName("Rico");
			assertEquals(student.getName(), "Rico");
		}
		
		@Test
		@DisplayName("Age")
		void testSetAge() {
			student.setAge(24);
			assertEquals(student.getAge(), 24);
		}
		
		@Test
		@DisplayName("Email")
		void testSetEmail() {
			student.setEmail("email@email.com");
			assertEquals(student.getEmail(), "email@email.com");
		}
		
		@Test
		@DisplayName("Address")
		void testSetAddress() {
			student.setAddress("123 address ave");
			assertEquals(student.getAddress(), "123 address ave");
		}
	}

}
