package com.employee.app.main;



import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.employee.entity.Address;
import com.employee.entity.Employee;
import com.employee.service.EmployeeService;

public class EmployeeApp {

    public static void main(String[] args) {

        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("spring.xml");

        EmployeeService service = ctx.getBean(EmployeeService.class);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1.Add  2.View  3.Delete  4.Exit");
            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    Employee e = new Employee();
                    Address a = new Address();

                    System.out.print("Emp ID: ");
                    e.setEmpId(sc.nextInt());

                    System.out.print("Emp Name: ");
                    e.setEmpName(sc.next());

                    System.out.print("Salary: ");
                    e.setSalary(sc.nextDouble());

                    System.out.print("Address ID: ");
                    a.setAddressId(sc.nextInt());

                    System.out.print("City: ");
                    a.setCity(sc.next());

                    System.out.print("State: ");
                    a.setState(sc.next());

                    // 🔑 Link both sides
                    e.setAddress(a);
                    a.setEmployee(e);

                    service.add(e);
                    System.out.println("Employee Added");
                    break;

                case 2:
                    System.out.print("Emp ID: ");
                    Employee emp = service.get(sc.nextInt());
                    if (emp != null) {
                        System.out.println(
                                emp.getEmpId() + " " +
                                emp.getEmpName() + " " +
                                emp.getSalary() + " " +
                                emp.getAddress().getCity());
                    } else {
                        System.out.println("Not Found");
                    }
                    break;

                case 3:
                    System.out.print("Emp ID: ");
                    service.delete(sc.nextInt());
                    System.out.println("Deleted");
                    break;

                case 4:
                    System.exit(0);
            }
        }
    }
}
