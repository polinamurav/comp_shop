import java.io.*;
import java.util.ArrayList;

public class FileManager {

    public static void writeToFile(String filename, ArrayList<?> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.out.println("\nОшибка записи данных в файл: " + e.getMessage());
        }
    }

    public static ArrayList<?> readFromFile(String filename) {
        ArrayList<?> list = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            list = (ArrayList<?>) ois.readObject();
            System.out.println("\nДанные из файла: " + list + "\n");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nОшибка чтения данных из файла: " + e.getMessage());
        }
        return list;
    }

    //нужно в самом начале, чтобы он считал файл
    public static ArrayList<?> readFromFileInvisible(String filename) {
        ArrayList<?> list = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            list = (ArrayList<?>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nОшибка чтения данных из файла: " + e.getMessage());
        }
        return list;
    }

}





//    //без него:
//    FileManager fileManager = new FileManager();
//fileManager.saveToFile("users.txt", userList);

//с ним
//    FileManager.saveToFile("users.txt", userList);