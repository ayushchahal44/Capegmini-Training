import java.util.*;
class loanelig {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter your monthly income: ");
        double income = s.nextDouble();
        System.out.print("Enter your credit score: ");
        double creditScore = s.nextDouble();
        System.out.print("Enter the loan amount: ");
        double loanAmount = s.nextDouble();
        boolean eligible = calc(income, creditScore, loanAmount);
        if (eligible) {
            System.out.println("Congratulations! You are eligible for the loan.");
        } else {
            System.out.println("Sorry! You are not eligible for the loan.");
        }
    }
    public static boolean calc(double income, double creditScore, double loanAmount) {
        if (income >= 25000 && creditScore >= 650 && loanAmount <= income * 10) {
            return true;
        }
        return false;
    }
}
