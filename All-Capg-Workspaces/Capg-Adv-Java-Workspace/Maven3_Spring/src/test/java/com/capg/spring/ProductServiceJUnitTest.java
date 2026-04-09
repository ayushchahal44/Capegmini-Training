package com.capg.spring;

 

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProductServiceJUnitTest {

    @Test
    public void testProductServices() {

        // Load Spring Container manually (NO annotations)
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // Get beans
        EmployeeService p1 =
                (EmployeeService) context.getBean("employeeService1");

        EmployeeService p2 =
                (EmployeeService) context.getBean("employeeService2");
        
        assertNotNull(p1);
        assertNotNull(p2);

        // Call methods
        p1.allEmployees();
        p2.allEmployees();       
    }
}