package com.lpu.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import com.lpu.model.Student;

public class StudentDAO {
	private static final String FILE = "student.json";
	private ArrayList<Student> students = new ArrayList<>();
	public StudentDAO() {
		loadFromFile();
	}
	private void loadFromFile() {
		try(BufferedReader br = new BufferedReader(new FileReader(FILE))){
			String line;
			while((line=br.readLine())!=null) {
				line = line.trim();
				if (line.startsWith("{")) {
                    int roll = Integer.parseInt(line.split("\"rollNo\":")[1].split(",")[0]);
                    String name = line.split("\"name\":\"")[1].split("\"")[0];
                    String course = line.split("\"course\":\"")[1].split("\"")[0];
                    students.add(new Student(roll, name, course));
                }
			}
		} catch (Exception e) {
			students = new ArrayList<>();
		}
	}
	private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            pw.println("[");
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                pw.print("  {\"rollNo\":" + s.getRollNo()
                        + ",\"name\":\"" + s.getName()
                        + "\",\"course\":\"" + s.getCourse() + "\"}");
                if (i < students.size() - 1) pw.println(",");
            }
            pw.println("\n]");
        } catch (Exception e) {
        }
    }
	public void addStudent(Student s) {
		students.add(s);
		saveToFile();
	}
    public Student getStudentByRollNo(int roll) {
        for (Student s : students) {
            if (s.getRollNo() == roll) return s;
        }
        return null;
    }
    
    public ArrayList<Student> getAllStudents(){
    		return students;
    }
    public boolean deleteStudent(int roll) {
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            if (it.next().getRollNo() == roll) {
                it.remove();
                saveToFile();
                return true;
            }
        }
        return false;
    }
}