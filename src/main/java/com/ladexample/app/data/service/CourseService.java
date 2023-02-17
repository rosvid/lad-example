package com.ladexample.app.data.service;

import com.ladexample.app.data.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService{
   private final CourseRepository courseRepository;

   @Autowired
   public CourseService(CourseRepository courseRepository){
      this.courseRepository = courseRepository;
   }

   public List<Course> findAll(){
      return courseRepository.findAll();
   }

   public List<Course> saveAll(List<Course> courses){
      return courseRepository.saveAll(courses);
   }
}