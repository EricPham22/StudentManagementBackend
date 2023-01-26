package com.example.StudentManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Students",uniqueConstraints=@UniqueConstraint(columnNames={"email"}))
public class Student {
	
	@Id
	@Column(name="STUDENT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int studentId;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Age")
	private int age;
	
	@Column(name="Email")
	private String email;
	
	@Column(name="Address")
	private String address;
	
	public Student() {
		
	}
	
	public Student(int studentId, String name, int age, String email, String address) {
		this.studentId = studentId;
		this.name = name;
		this.age = age;
		this.email = email;
		this.address = address;
		
	}
	
	//Get Id
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public int getStudentId() {
		return this.studentId;
	}
	
	//Get Name
	public String getName() {
		return this.name;
	}
	
	//Get Age
	public int getAge() {
		return this.age;
	}
	
	
	//Get Email
	public String getEmail() {
		return this.email;
	}
	
	//Get Address
	public String getAddress() {
		return this.address;
	}
	
	//Set id
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	//set name
	public void setName(String name) {
		this.name = name;
	}
	
	//set age
	public void setAge(int age) {
		this.age = age;
	}
	
	//set email
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	//set address
	public void setAddress(String address) {
		this.address = address;
	}
}
