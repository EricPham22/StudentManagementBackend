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
public class Students {
	
	@Id
	@Column(name="STUDENT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int STUDENT_ID;
	
	@Column(name="Name")
	private String Name;
	
	@Column(name="Age")
	private int Age;
	
	@Column(name="Email")
	private String Email;
	
	@Column(name="Address")
	private String Address;
	
	public Students() {
		
	}
	
	public Students(int studentId, String name, int age, String email, String address) {
		this.STUDENT_ID = studentId;
		this.Name = name;
		this.Age = age;
		this.Email = email;
		this.Address = address;
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public int getStudentId() {
		return this.STUDENT_ID;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public int getAge() {
		return this.Age;
	}
	
	public String getEmail() {
		return this.Email;
	}
	
	public String getAddress() {
		return this.Address;
	}
	
	public void setStudentId(int studentId) {
		this.STUDENT_ID = studentId;
	}
	
	public void setName(String name) {
		this.Name = name;
	}
	
	public void setAge(int age) {
		this.Age = age;
	}
	
	public void setEmail(String email) {
		this.Email = email;
	}
	
	public void setAddress(String address) {
		this.Address = address;
	}
}
