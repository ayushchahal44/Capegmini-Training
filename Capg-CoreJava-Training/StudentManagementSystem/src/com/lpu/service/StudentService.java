package com.lpu.service;

import com.lpu.dao.StudentDAO;
import com.lpu.exception.LPUException;
import com.lpu.model.Student;
public class StudentService {
	private StudentDAO dao = new StudentDAO();
	public void addStudent(int roll,String name,String course) throws LPUException{
		if(dao.getStudentByRollNo(roll) != null) {
			throw new LPUException("Student already exist!");
		}
		dao.addStudent(new Student(roll,name,course));
	}
	public void showAllStudents() {
		for(Student s: dao.getAllStudents()) {
			System.out.println(s);
		}
	}
	public void removeStudent(int roll) throws LPUException {
		if(!dao.deleteStudent(roll)) {
			throw new LPUException("Student not found!");
		}
	}
}
