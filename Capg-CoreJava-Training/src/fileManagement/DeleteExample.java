import java.io.File;
class DeleteExample {
    public static void main(String[] args) {
        File f = new File("MyFolder");
        if (f.delete()) {
            System.out.println("Deleted successfully");
        } else {
            System.out.println("Delete failed (not empty or not found)");
        }
    }
}
