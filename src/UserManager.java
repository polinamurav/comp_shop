import java.util.ArrayList;
import java.util.Scanner;

public class UserManager {
    private ArrayList<User> accountList;
    Scanner scanner = new Scanner(System.in);
    Scanner scan = new Scanner(System.in);

    //соединила свою коллекцию с main
    public UserManager(ArrayList<User> accountList) {
        this.accountList = accountList;
    }

    public User login(String username, String password) {
        for (User user : accountList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                if (user.isBlocked() == true) return null;
                return user;
            }
        }
        return null; // Если пользователя не найдено
    }

    public boolean register(String username, String password, int role) {
        // Проверка на существующего пользователя с таким именем
        boolean usernameExists = false;

        for (User user : accountList) {
            if (user.getUsername().equals(username)) {
                usernameExists = true;
                break; // Нашли совпадение, выходим из цикла for
            }
        }

        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getUsername().equals(username)) {
                usernameExists = true;
                break; // Нашли совпадение, выходим из цикла for
            }
        }

        if (usernameExists) {
            System.out.println("Пользователь с таким именем уже существует.");
            System.out.println("Введите новое имя пользователя: ");
            String newUsername = scanner.nextLine();
            username = newUsername; // Обновляем имя пользователя
            System.out.print("Введите пароль: ");
            String newPassword = scanner.nextLine();
            password = newPassword;
            accountList.add(new User(username, password, 1));
        } else {
            accountList.add(new User(username, password, 1));
            System.out.println("Пользователь зарегистрирован успешно.");
        }
        return true; // Успешная регистрация нового пользователя
    }


    // Метод для редактирования пользователя
    public boolean editUser() {
        System.out.println("Введите имя пользователя, которого хотите изменить");
        String usernameEdit = scan.nextLine();
        // Находим пользователя по имени
        User userToEdit = null;
        for (User i : accountList) {
            if (i.getUsername().equals(usernameEdit)) {
                userToEdit = i;
                break;
            }
        }

        if (userToEdit != null) {
            System.out.print("Введите новое имя пользователя: ");
            String newUsername = scanner.next();
            System.out.print("Введите новый пароль пользователя: ");
            String newPassword = scanner.next();

            // Применяем новые данные к пользователю
            //userToEdit = new User(newUsername, newPassword, userToEdit.getRole());
            // Обновление данных существующего пользователя
            userToEdit.setUsername(newUsername);
            userToEdit.setPassword(newPassword);
            System.out.println("Пользователь " + usernameEdit + " успешно изменен.");
        } else {
            System.out.println("Пользователь с именем " + usernameEdit + " не найден.");
        }
        return true;
    }

    // Метод для удаления пользователя
    public boolean deleteUser() {
        System.out.println("Введите имя пользователя, которого хотите удалить");
        String usernameDelete = scan.nextLine();
        // Находим пользователя по имени
        User userToEdit = null;
        for (User i : accountList) {
            if (i.getUsername().equals(usernameDelete)) {
                userToEdit = i;
                break;
            }
        }
        if (userToEdit != null) {
            accountList.remove(userToEdit); // Удаляем пользователя из списка

            System.out.println("Пользователь " + usernameDelete + " успешно удален.");
        } else {
            System.out.println("Пользователь с именем " + usernameDelete + " не найден.");
        }

        return true;
    }

    // Метод для блокировки пользователя
    public void blockUser() {

        System.out.println("Введите имя пользователя, которого вы хотите заблокировать:");
        String usernameBlock = scan.nextLine();

        // Находим пользователя в списке
        User userToBlock = null;
        for (User user : accountList) {
            if (user.getUsername().equals(usernameBlock)) {
                userToBlock = user;
                break;
            }
        }

        if (userToBlock != null) {
            userToBlock.setBlocked(true); // Устанавливаем блокировку для пользователя
            System.out.println("Пользователь " + usernameBlock + " заблокирован администратором.");
        } else {
            System.out.println("Пользователь с именем " + usernameBlock + " не найден.");
        }
    }

}