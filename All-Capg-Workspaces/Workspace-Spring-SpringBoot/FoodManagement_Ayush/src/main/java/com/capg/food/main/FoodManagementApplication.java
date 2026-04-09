package com.capg.food.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.*;
import com.capg.food.config.*;
import com.capg.food.service.*;

public class FoodManagementApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        CategoryService categoryService =
                context.getBean(CategoryService.class);

        FoodService foodService =
                context.getBean(FoodService.class);

        OrderService orderService =
                context.getBean(OrderService.class);

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== FOOD MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Category");
            System.out.println("2. Add Food Item");
            System.out.println("3. View Items By Category");
            System.out.println("4. Place Order");
            System.out.println("5. Calculate Total");
            System.out.println("6. Exit");

            int ch = readInt(sc, "Enter choice:");

            switch (ch) {

                case 1:
                    System.out.println("Enter Category Name:");
                    String cname = sc.nextLine();

                    System.out.println("Enter Description:");
                    String cdesc = sc.nextLine();

                    categoryService.addCategory(cname, cdesc);
                    System.out.println("✅ Category Added Successfully!");
                    break;

                case 2:
                    Long catId = readLong(sc, "Enter Category ID:");

                    System.out.println("Enter Food Name:");
                    String fname = sc.nextLine();

                    double price = readDouble(sc, "Enter Price:");

                    foodService.addFoodItem(catId, fname, price);
                    System.out.println("✅ Food Item Added Successfully!");
                    break;

                case 3:
                    Long cid = readLong(sc, "Enter Category ID:");

                    foodService.getItemsByCategory(cid)
                            .forEach(f ->
                                    System.out.println(
                                            f.getId() + " | "
                                                    + f.getItemName() + " | "
                                                    + f.getPrice()));
                    break;

                case 4:
                    System.out.println("Enter Customer Name:");
                    String cust = sc.nextLine();

                    System.out.println("Enter Food Item IDs (comma separated):");
                    String input = sc.nextLine();

                    List<Long> ids = new ArrayList<>();

                    try {
                        for (String s : input.split(",")) {
                            ids.add(Long.parseLong(s.trim()));
                        }
                        orderService.placeOrder(ids, cust);
                        System.out.println("✅ Order Placed Successfully!");
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid Food Item ID format.");
                    }
                    break;

                case 5:
                    Long orderId = readLong(sc, "Enter Order ID:");

                    double total = orderService.calculateTotal(orderId);
                    if (total != 0)
                        System.out.println("💰 Total Bill: " + total);
                    break;

                case 6:
                    context.close();
                    System.out.println("Application Closed.");
                    System.exit(0);

                default:
                    System.out.println("❌ Invalid Choice!");
            }
        }
    }

    // ---------- Helper Methods ----------

    private static int readInt(Scanner sc, String message) {
        while (true) {
            try {
                System.out.println(message);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid number!");
            }
        }
    }

    private static Long readLong(Scanner sc, String message) {
        while (true) {
            try {
                System.out.println(message);
                return Long.parseLong(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numeric value!");
            }
        }
    }

    private static double readDouble(Scanner sc, String message) {
        while (true) {
            try {
                System.out.println(message);
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid decimal value!");
            }
        }
    }
}