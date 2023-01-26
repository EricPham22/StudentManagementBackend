package com.example.StudentManagement.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.example.StudentManagement.model.Student;
import com.example.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;

	/**
	Removes a student from the DB
	@param id - The student id of the student to be removed.
	@throws IllegalArgumentException
	*/
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}
	
	/**
	Add a student to the DB
	@param student - The student to be added 
	@throws IllegalArgumentException
	@throws OptimisticLockingFailureException
	@return Student with matching id 
	*/	
	public Student addStudent(Student student) throws IllegalArgumentException, OptimisticLockingFailureException {
		return studentRepository.save(student);
	}
	
	/**
	Add mulitple students to the DB
	@param students - The list of student to be added.
	@throws IllegalArgumentException
	@throws OptimisticLockingFailureException
	@return Student with matching id 
	*/
	public List<Student> addMultipleStudents(List<Student> students) throws IllegalArgumentException, OptimisticLockingFailureException {
		return studentRepository.saveAll(students); 
	}
		
	/**
	Get a student from the DB based on student id
	@param id - The student id of the student to be removed.
	@throws NoSuchElementException
	@return Student with matching id 
	*/
	public Student getStudentById(int id) throws NoSuchElementException {
		return studentRepository.findById(id).orElseThrow();
	}
	
	/**
	Removes a student from the DB
	@param id - The student id of the student to be removed.
	@throws IllegalArgumentException
	*/
	public void removeStudent(int id) throws IllegalArgumentException {
		studentRepository.deleteById(id);
	}

	/**
	Updates a student details
	@param updatedStudent - The student with updated details 
	@throws IllegalArgumentException
	@return Student with updated fields
	*/
	public Student updateStudentDetails(Student updatedStudent) throws IllegalArgumentException {
		Student student = studentRepository.findById(updatedStudent.getStudentId()).get();		
		if(student.getName() != updatedStudent.getName() && updatedStudent.getName() != null) student.setName(updatedStudent.getName());
		if(student.getAge() != updatedStudent.getAge() && updatedStudent.getAge() != 0) student.setAge(updatedStudent.getAge());
		if(student.getEmail() != updatedStudent.getEmail() && updatedStudent.getEmail() != null) student.setEmail(updatedStudent.getEmail());
		if(student.getAddress() != updatedStudent.getAddress() && updatedStudent.getAddress() != null) student.setAddress(updatedStudent.getAddress());
		return studentRepository.save(student);
	}
}
