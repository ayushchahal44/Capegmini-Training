package com.lpu.util;

import java.util.Scanner;

public class MenuUtil {
	public static void showMenu() {
		System.out.println("---------------LPU Student Management System--------------");
		System.out.println("1. Add Student");
		System.out.println("2. View Student");
		System.out.println("3. Delete Student");
		System.out.println("4. Exit");
		System.out.print("Choose option: ");
	}
	public static int getInt(Scanner s) {
		return s.nextInt();
	}
	public static String getString(Scanner s) {
		return s.next();
	}
}
