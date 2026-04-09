import java.io.File;
import java.io.IOException;
class CreateNewFile {
    public static void main(String[] args) {
        File file = new File("sample.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("Error creating file");
        }
    }
}
