import java.util.Scanner;
class removedup {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter the size of your array: ");
        int arr[] = new int[s.nextInt()];
        for (int i = 0; i < arr.length; i++) {
            System.out.print("Enter the " + (i + 1) + " element of the array: ");
            arr[i] = s.nextInt();
        }
        System.out.println("-------------------------------------");
        System.out.println("Array after removing duplicates:");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == -1)
                continue;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    arr[j] = -1;
                }
            }
            System.out.print(arr[i] + " ");
        }
    }
}
