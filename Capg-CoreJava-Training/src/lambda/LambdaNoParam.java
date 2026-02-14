@FunctionalInterface
interface Print {
    void prnt();
}
public class LambdaNoParam {
    public static void main(String[] args) {
        Print p = () -> System.out.println("Hello Lambda");
        p.prnt();
    }
}
