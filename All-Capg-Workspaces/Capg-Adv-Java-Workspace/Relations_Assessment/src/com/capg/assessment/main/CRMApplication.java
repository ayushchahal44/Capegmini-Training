package com.capg.assessment.main;

import com.capg.assessment.service.*;
import com.capg.assessment.util.JPAUtil;

import javax.persistence.*;
import java.util.*;

public class CRMApplication {

    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();
        Scanner sc = new Scanner(System.in);

        CustomerService customerService = new CustomerService(em);
        LeadService leadService = new LeadService(em);
        ProductService productService = new ProductService(em);
        OrderService orderService = new OrderService(em);
        TicketService ticketService = new TicketService(em);
        ReportService reportService = new ReportService(em);

        while (true) {

            System.out.println(
                "\n1 Register Customer\n" +
                "2 Add Address\n" +
                "3 Create Lead\n" +
                "4 Assign Lead\n" +
                "5 Convert Lead\n" +
                "6 Add Product\n" +
                "7 Place Order\n" +
                "8 Raise Ticket\n" +
                "9 Employee Performance\n" +
                "10 Exit"
            );

            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    System.out.println("Name:");
                    String name = sc.next();

                    System.out.println("Email:");
                    String email = sc.next();

                    System.out.println("Phone:");
                    String phone = sc.next();

                    customerService.registerCustomer(name, email, phone);
                    break;

                case 2:
                    System.out.println("Customer ID:");
                    Long custId = sc.nextLong();

                    System.out.println("Street:");
                    String street = sc.next();

                    System.out.println("City:");
                    String city = sc.next();

                    System.out.println("State:");
                    String state = sc.next();

                    System.out.println("ZipCode:");
                    String zip = sc.next();

                    customerService.addAddress(custId, street, city, state, zip);
                    break;

                case 3:
                    System.out.println("Lead Name:");
                    String lname = sc.next();

                    System.out.println("Source:");
                    String source = sc.next();

                    System.out.println("Contact:");
                    String contact = sc.next();

                    leadService.createLead(lname, source, contact);
                    break;

                case 4:
                    System.out.println("Lead ID:");
                    Long leadId = sc.nextLong();

                    System.out.println("Employee ID:");
                    Long empId = sc.nextLong();

                    leadService.assignLead(leadId, empId);
                    break;

                case 5:
                    System.out.println("Lead ID:");
                    Long convertId = sc.nextLong();

                    leadService.convertLead(convertId);
                    break;

                case 6:
                    System.out.println("Product Name:");
                    String pname = sc.next();

                    System.out.println("Price:");
                    double price = sc.nextDouble();

                    productService.addProduct(pname, price);
                    break;

                case 7:
                    System.out.println("Customer ID:");
                    Long orderCustId = sc.nextLong();

                    System.out.println("Enter Product IDs (comma separated):");
                    sc.nextLine(); // clear buffer
                    String line = sc.nextLine();

                    String[] parts = line.split(",");
                    List<Long> pids = new ArrayList<>();

                    for (String p : parts) {
                        pids.add(Long.parseLong(p.trim()));
                    }

                    orderService.placeOrder(orderCustId, pids);
                    break;

                case 8:
                    System.out.println("Order ID:");
                    Long orderId = sc.nextLong();

                    System.out.println("Issue Description:");
                    sc.nextLine();
                    String issue = sc.nextLine();

                    ticketService.raiseTicket(orderId, issue);
                    break;

                case 9:
                    System.out.println("Employee ID:");
                    Long reportEmpId = sc.nextLong();

                    reportService.getEmployeePerformance(reportEmpId);
                    break;

                case 10:
                    em.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}