package com.example.StudentManagement.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.example.StudentManagement.model.Students;
import com.example.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepo;

	public StudentService(StudentRepository studentRepo) {
		this.studentRepo = studentRepo;
	}
	
	public List<Students> getAllStudents() {
		return this.studentRepo.findAll();
	}
	
	public Students addStudent(Students student) throws IllegalArgumentException, OptimisticLockingFailureException {
		return this.studentRepo.save(student);
	}
	
	public List<Students> addMultipleStudents(List<Students> students) throws IllegalArgumentException, OptimisticLockingFailureException {
		return this.studentRepo.saveAll(students); 
	}
		
	public Students getStudentById(int id) throws NoSuchElementException {
		return this.studentRepo.findById(id).orElseThrow();
	}
	
	public void removeStudent(int id) throws IllegalArgumentException {
		this.studentRepo.deleteById(id);
	}
	
	public Students updateStudentDetails(Students updatedStudent) throws IllegalArgumentException {
		Students student = this.studentRepo.findById(updatedStudent.getStudentId()).get();		
		if(student.getName() != updatedStudent.getName() && updatedStudent.getName() != null) student.setName(updatedStudent.getName());
		if(student.getAge() != updatedStudent.getAge() && updatedStudent.getAge() != 0) student.setAge(updatedStudent.getAge());
		if(student.getEmail() != updatedStudent.getEmail() && updatedStudent.getEmail() != null) student.setEmail(updatedStudent.getEmail());
		if(student.getAddress() != updatedStudent.getAddress() && updatedStudent.getAddress() != null) student.setAddress(updatedStudent.getAddress());
		return this.studentRepo.save(student);
	}
}
