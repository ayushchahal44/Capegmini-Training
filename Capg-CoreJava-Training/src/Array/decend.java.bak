import java.util.*;
class avgelement {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter array size: ");
        int n = s.nextInt();
        int[] a = new int[n];
        int sum = 0;
        System.out.println("Enter array elements:");
        for (int i = 0; i < n; i++) {
            a[i] = s.nextInt();
            sum += a[i];
        }
        double avg = (double) sum / n;
        int eleavg = a[0];
        double mind = a[0] - avg;
        if (mind < 0) mind = -mind;
        for (int i = 1; i < n; i++) {
            double diff = a[i] - avg;
            if (diff < 0) diff = -diff;
            if (diff < mind) {
                mind = diff;
                eleavg = a[i];
            }
        }
        System.out.println("Average value = " + avg);
        System.out.println("Average element = " + eleavg);
    }
}
