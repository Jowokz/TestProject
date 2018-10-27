package com.in28minutes.springboot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.springboot.model.Course;
import com.in28minutes.springboot.model.Student;
import com.in28minutes.springboot.service.StudentService;

@RestController
public class StudentController {

	@Autowired//autowired
	private StudentService studentService;
	
	//private StudentService stuServ = new StudentService();

	//For given studentid retrive courses.
	@GetMapping("/students/{studentId}/courses")
	public List<Course> retrieveCoursesForStudent(@PathVariable String studentId) {
		return studentService.retrieveCourses(studentId);
	}
	
	//For Given studentid and courseid, retrieve course details.
	@GetMapping("/students/{studentId}/courses/{courseId}")
	public Course retrieveDetailsForCourse(@PathVariable String studentId,
			@PathVariable String courseId) {
		return studentService.retrieveCourse(studentId, courseId);
	}
	
	//adding course
	@PostMapping("/students/{studentId}/courses")
	public ResponseEntity<Void> registerStudentForCourse(
			@PathVariable String studentId, @RequestBody Course newCourse) {

		Course course = studentService.addCourse(studentId, newCourse);

		if (course == null)
			return ResponseEntity.noContent().build();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/{id}").buildAndExpand(course.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	//update student
	@PutMapping("/students/{studentId}")
	public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable String studentId) {

		Student studentOutput = studentService.UpdateStudent(student, studentId);
		if (studentOutput==null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.status(200).build();
	}
	
	//  alll services from postman
	//delete student
	
	
	@DeleteMapping("/delteStudentForCourse/{studentId}/{courseId}")
	public Student deleteStudentForCourse(
			@PathVariable String studentId, @PathVariable String courseId) {

		Student student = studentService.deleteStudentForCourse(studentId, courseId);

		return student;
	}
	//update student

}
