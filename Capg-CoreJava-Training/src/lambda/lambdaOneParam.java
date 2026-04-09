@FunctionalInterface
interface Print {
    String prnt(String mess);
}
public class lambdaOneParam {
    public static void main(String[] args) {
        Print p = (mess) -> {
            System.out.println(mess);
            return mess;
        };
        p.prnt("Hello Lambda");
    }
}