package com.ladexample.app.data.service;

import com.ladexample.app.data.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer>{}