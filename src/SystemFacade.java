import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SystemFacade {
    private UserManager userManager;
    private AdminManager adminManager;
    Scanner scanner = new Scanner(System.in);
    Scanner scan = new Scanner(System.in);
    private String filename;

    public SystemFacade(ArrayList<User> accountList, ArrayList<ComputerComponent> filterCatalog, String filename, String filecomputer) {
        userManager = new UserManager(accountList);
        adminManager = new AdminManager(filterCatalog, accountList, filename, filecomputer);
    }

    // Регистрация нового пользователя
    public void registerUser() {
        System.out.print("Введите желаемое имя пользователя: ");
        String newUsername = scanner.nextLine();

        System.out.print("Введите пароль: ");
        String newPassword = scanner.nextLine();

        boolean registrationSuccessful = userManager.register(newUsername, newPassword, 1);
    }

    // Вход пользователя в систему
    public User loginUser() {
        User currentUser = null;
        while (currentUser == null) {
            System.out.print("Введите имя пользователя: ");
            String username = scanner.nextLine();

            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();

            currentUser = userManager.login(username, password);

            if (currentUser == null) {
                System.out.println("Ошибка входа. Пожалуйста, повторите попытку.");
            }
        }
        return currentUser;
    }

    // Операции администратора
    public void performAdminOperations(ArrayList<User> accountList, String filename) {
        try {
            System.out.println("1. Просмотреть зарегистрированных пользователей");
            System.out.println("2. Изменить пользователя");
            System.out.println("3. Удалить пользователя");
            System.out.println("4. Заблокировать пользователя");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    FileManager.readFromFile(filename);
                    break;
                case 2:
                    userManager.editUser();
                    FileManager.writeToFile(filename, accountList);
                    break;
                case 3:
                    userManager.deleteUser();
                    FileManager.writeToFile(filename, accountList);
                    break;
                case 4:
                    userManager.blockUser();
                    FileManager.writeToFile(filename, accountList);
                    break;
            }
        } catch (InputMismatchException c) {
            System.out.println("Ошибка! Введите число!");
            scanner.next();
        } catch (NumberFormatException ex) {
            System.out.println("Ошибка! Неправильный ввод");
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }

    public void viewRegisteredUsers() {
        FileManager.readFromFile(filename);
    }
}
