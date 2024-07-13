import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminManager extends ComponentManager {
    private String filename;
    private String filecomputer;
    private ArrayList<User> accountList;
    //private SystemFacade systemFacade;
    Scanner scanner = new Scanner(System.in);
    Scanner scan = new Scanner(System.in);

    //соединила свою коллекцию с main
    public AdminManager(ArrayList<ComputerComponent> filterCatalog, ArrayList<User> accountList, String filename, String filecomputer) {
        super(filterCatalog);
        this.accountList = accountList;
        this.filename = filename;
        this.filecomputer = filecomputer;
    }

    @Override
    protected void displayMenu() {
        System.out.println("Меню:");
        System.out.println("1. Все компоненты");
        System.out.println("2. Добавить компонент");
        System.out.println("3. Удалить компонент");
        System.out.println("4. Редактировать компонент");
        System.out.println("5. Работа с пользователями");
        System.out.println("6. Выход");
    }

    @Override
    protected void performAction(int choice) {
        switch (choice) {
            case 1:
                if (filterCatalog.isEmpty()) {
                    System.out.println("Комплектующих нет");
                } else {
                    FileManager.readFromFile(filecomputer);
                }
                break;
            case 2:
                addComponent(filterCatalog, scan);
                break;
            case 3:
                System.out.println("Удаление компонента:");
                deleteComponent(filterCatalog, scan);
                break;
            case 4:
                editComponent(filterCatalog, scan);
                break;
            default:
                break;
        }
    }



    // Метод для удаления компонента
    public void deleteComponent(ArrayList<ComputerComponent> filterCatalog, Scanner scan) {
        System.out.println("Выберите название комплектующего, которое хотите удалить:");
        String componentDelete = scan.nextLine();
        // Находим комплектующее по названию
        ComputerComponent component = null;
        for (ComputerComponent i : filterCatalog) {
            if (i.getName().equals(componentDelete)) {
                component = i;
                break;
            }
        }
        if (component != null) {
            //if (componentIndex > 0 && componentIndex <= FilterCatalog.size()) {
            //ComputerComponent removedComponent = FilterCatalog.remove(componentIndex - 1);
            //Computerlist.remove(removedComponent); // Удаляем из общего списка компонентов
            filterCatalog.remove(component);
            System.out.println("Компонент '" + componentDelete + "' удален.");
        } else {
            System.out.println("Название такого компонента не найдено.");
        }
    }

    public void addComponent(ArrayList<ComputerComponent> filterCatalog, Scanner scan) {
        try {
            System.out.print("Введите название компонента: ");
            String componentName = scan.nextLine();
            System.out.print("Введите цену компонента: ");
            double componentPrice = scanner.nextDouble();
            if (componentPrice < 0) {
                throw new InvalidPriceException("Цена не может быть отрицательной!");
            }
            System.out.println("Введите рейтинг: ");
            double componentRating = scanner.nextDouble();
            ComputerComponent newComponent = new ComputerComponent(componentName, componentPrice, componentRating);
            //Computerlist.add(newComponent);
            filterCatalog.add(newComponent);
            System.out.println("Компонент '" + componentName + "' добавлен.");
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }

    }

    public void editComponent(ArrayList<ComputerComponent> filterCatalog, Scanner scan) {
        try {
            System.out.println("Введите название комплектующего, которое хотите отредактировать:");
            String editComponent = scan.nextLine();
            ComputerComponent component = null;
            for (ComputerComponent i : filterCatalog) {
                if (i.getName().equals(editComponent)) {
                    component = i;
                    break;
                }
            }
            if (component != null) {
                System.out.println("Введите новое название компонента: ");
                String newName = scan.nextLine();
                System.out.println("Введите новую цену компонента: ");
                double newPrice = scan.nextDouble();
                if (newPrice < 0) {
                    throw new InvalidPriceException("Цена не может быть отрицательной!");
                }

                component.setName(newName); //устанавливает новое значение для имени компонента
                component.setPrice(newPrice);

                System.out.println("Компонент '" + editComponent + "' отредактирован.");
            } else {
                System.out.println("Название такого компонента не найдено.");
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }

    }


}