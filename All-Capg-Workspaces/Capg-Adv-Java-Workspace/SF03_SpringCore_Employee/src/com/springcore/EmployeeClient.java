package com.springcore;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmployeeClient {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext factory =
            new ClassPathXmlApplicationContext("applicationContext.xml");

        EmployeeService e1 =
            (EmployeeService) factory.getBean("employeeService1");
        e1.allEmployees();

        EmployeeService e2 =
            (EmployeeService) factory.getBean("employeeService2");
        e2.allEmployees();

        factory.close();
    }
}
