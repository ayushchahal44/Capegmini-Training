package com.lpu.main;

import java.util.Scanner;

import com.lpu.exception.LPUException;
import com.lpu.service.StudentService;
import com.lpu.util.MenuUtil;
public class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		StudentService service = new StudentService();
		while(true) {
			MenuUtil.showMenu();
			int choice = MenuUtil.getInt(s);
			try {
				switch(choice) {
				case 1:
					System.out.print("Roll no: ");
					int roll=MenuUtil.getInt(s);
					System.out.print("Name: ");
					String name = MenuUtil.getString(s);
					System.out.print("Course: ");
					String course = MenuUtil.getString(s);
					
					service.addStudent(roll, name, course);
					System.out.println("Student added Successfully.");
					break;
				case 2:
					service.showAllStudents();
					break;
				case 3:
					System.out.print("Enter roll number to delete: ");
					int r = MenuUtil.getInt(s);
					service.removeStudent(r);
					System.out.println("Student Deleted Successfully.");
					break;
				case 4:
					System.exit(0);
				default:
					System.out.println("Invalid choice!");
				}
			} catch (LPUException e) {
				System.out.println("Error: "+e.getMessage());
			}
		}
	}
}
