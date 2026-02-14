import java.io.File;
class RenameExample {
    public static void main(String[] args) {
        File oldFile = new File("oldName.txt");
        File newFile = new File("newName.txt");
        if (oldFile.renameTo(newFile)) {
            System.out.println("Renamed successfully");
        } else {
            System.out.println("Rename failed");
        }
    }
}
