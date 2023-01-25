package com.example.StudentManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StudentManagement.model.Students;

public interface StudentRepository extends JpaRepository<Students, Integer> {
}
