import java.util.Scanner;

class mergearr {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter size of first array: ");
        int arr1[] = new int[s.nextInt()];
        for (int i = 0; i < arr1.length; i++) {
            System.out.print("Enter element " + (i + 1) + " of first array: ");
            arr1[i] = s.nextInt();
        }
        System.out.print("Enter size of second array: ");
        int arr2[] = new int[s.nextInt()];
        for (int i = 0; i < arr2.length; i++) {
            System.out.print("Enter element " + (i + 1) + " of second array: ");
            arr2[i] = s.nextInt();
        }
        int s_array[] = new int[arr1.length + arr2.length];
        int k = 0;
        for (int i = 0; i < arr1.length; i++) {
            s_array[k++] = arr1[i];
        }
        for (int i = 0; i < arr2.length; i++) {
            s_array[k++] = arr2[i];
        }
        System.out.println("-------------------------------------");
        System.out.println("Merged Array:");
        for (int i = 0; i < s_array.length; i++) {
            System.out.print(s_array[i] + " ");
        }
    }
}
