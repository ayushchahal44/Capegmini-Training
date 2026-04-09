public class swapnumbers {
    public static void main(String[] args){
        int a = 10;
        int b = 20;
        a = a+b;
        b=a-b;
        a=a-b;
        System.out.println("After swapping a: "+a);
        System.out.println("After swapping b: "+b);
    }
}
