import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.stream.Stream;

//Operation sum = (a) -> {
//        return a;
//        };
//
//        double a = sum.cost(3.0);
//        System.out.println(a);

public class Main {

    public static void main(String[] args) {

        ArrayList<Integer> number = new ArrayList<>();
        number.add(1);
        number.add(2);
        number.add(3);
        number.add(4);
        number.add(5);
        number.stream().filter(i -> i>3).forEach(System.out::println);

//        for (User user : accountList) {
//            if (user.getUsername().equals(username)) {
//                usernameExists = true;
//                break; // Нашли совпадение, выходим из цикла for
//            }
//        }
//
//        for (int i = 0; i < accountList.size(); i++) {
//            if (accountList.get(i).getUsername().equals(username)) {
//                usernameExists = true;
//                break; // Нашли совпадение, выходим из цикла for
//            }
//        }

        Scanner scanner = new Scanner(System.in);
        Scanner scan = new Scanner(System.in);
        ArrayList<ComputerComponent> Computerlist = new ArrayList<>();

        ArrayList<ComputerComponent> Catalog = new ArrayList<>();

        ComputerComponentFactory componentFactory = new ConcreteComponentFactory();
        Computerlist.add(componentFactory.createComponent("Блок питания", 500, 8));
        Computerlist.add(componentFactory.createComponent("Видео карты", 1000, 7.3));
        Computerlist.add(componentFactory.createComponent("Корпус", 600, 9));//!!!!!!!!!!!
        Computerlist.add(componentFactory.createComponent("Процессор", 453, 8.8));
        Computerlist.add(componentFactory.createComponent("Оперативная память", 400, 9.6));

        //копия каталога
        ArrayList<ComputerComponent> FilterCatalog = new ArrayList<>();
        FilterCatalog.addAll(Computerlist); // Копируем оригинальный каталог в фильтрованный


        ArrayList<ComputerComponent> Filter_Price_Max = new ArrayList<>();
        Filter_Price_Max.addAll(Computerlist);
        ArrayList<ComputerComponent> Filer_Price_Min = new ArrayList<>();
        Filer_Price_Min.addAll(Computerlist);

        String filecomputer = "Computerlist.txt";
        // Загрузка существующих данных из файла, если файл существует
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filecomputer))) {
            Computerlist = (ArrayList<ComputerComponent>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Файл не найден или возникла ошибка при чтении: " + e.getMessage());
        }

        FileManager.writeToFile(filecomputer, FilterCatalog);

        boolean maxFilter = true; // - для фильтрации и сортировки
        boolean minFilter = true;

        boolean sortIncrease = false; //для сортировки по возрастанию при фильтрации отмены
        boolean sortDecrease = false;


        String filename = "users.txt";
        ArrayList<User> accountList = new ArrayList<>();
        accountList.add(new User("admin", "1111", 0));
        accountList.add(new User("polinamurav", "12345", 1));
        accountList.add(new User("1", "1", 0));

        // Загрузка существующих данных из файла, если файл существует
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            accountList = (ArrayList<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Файл не найден или возникла ошибка при чтении: " + e.getMessage());
        }

        SystemFacade systemFacade = new SystemFacade(accountList, FilterCatalog, filename, filecomputer);
        AdminManager adminManager = new AdminManager(FilterCatalog, accountList, filename, filecomputer);

        boolean check_welcome = true, check_admin = true, check_user = true;


        int choice, num, choiceAdmin, choice_about_user;
        //0 - админ, 1 - пользователь
        //////////////////////////////////////////
        User currentUser = null;
        do {
            while (check_welcome) {
                System.out.println("Добро пожаловать! Выберите:");
                System.out.println("1. Зарегистрироваться");
                System.out.println("2. Авторизироваться");
                FileManager.readFromFile(filename);
                int system = scan.nextInt();
                switch (system) {
                    case 1:
                        systemFacade.registerUser();
                        //запись данных с регистрацией в файл
                        FileManager.writeToFile(filename, accountList);
                        break;
                    case 2:
                        currentUser = systemFacade.loginUser();
                        check_welcome = false; // выход из цикла
                        break;
                    default:
                        System.out.println("Выбрана неверная цифра");
                }
            }
            ///////////////////////////////////////////

            do {
                if (currentUser != null) {
                    try {
                            if (currentUser.getRole() == 0) {
                                adminManager.displayMenu();
                                System.out.print("Выберите действие: ");
                                  choiceAdmin = scanner.nextInt();
                                adminManager.performAction(choiceAdmin);
                                if (choiceAdmin == 5) {
                                    systemFacade.performAdminOperations(accountList, filename);
                                }
                                if(choiceAdmin == 6) {
                                    check_admin = false;
                                    System.out.println("Успешно вышли");
                                    check_welcome = true;
                                    break; // выход из внутреннего цикла
                                }
                                FileManager.writeToFile(filecomputer, FilterCatalog);
                                FileManager.writeToFile(filename, accountList);
                            } else if (currentUser.getRole() == 1) {

                                System.out.println("Меню:");
                                System.out.println("1. Добавить компонент в корзину");
                                System.out.println("2. Вывести все добавленные комплектующие в корзине");
                                System.out.println("3. Подсчитать стоимость компьютера");
                                System.out.println("4. Применить фильтры");
                                System.out.println("5. Доступные комплектующие");
                                System.out.println("6. Сортировка потоками");
                                System.out.println("7. Выход");
                                System.out.print("Выберите действие: ");

                                choice = scanner.nextInt();
                                switch (choice) {
                                    case 1:
                                        System.out.println("Нажмите 1 - добавить существующий компонент. Иначе - добавить с клавиатуры");
                                        num = scanner.nextInt();
                                        if (num == 1) {
                                            System.out.println("Доступные компоненты:");
                                            int index = 1;
                                            for (int i = 0; i < FilterCatalog.size(); i++) {
                                                ComputerComponent component = FilterCatalog.get(i);
                                                System.out.println((index) + ". " + component.getName() + " - $" + component.getPrice());
                                                index++;
                                            }

                                            System.out.print("Выберите компонент (по номеру из списка): ");
                                            int selectedComponentIndex = scanner.nextInt();
                                            if (selectedComponentIndex >= 1 && selectedComponentIndex <= Computerlist.size()) {
                                                ComputerComponent selectedComponent = FilterCatalog.get(selectedComponentIndex - 1);
                                                Catalog.add(selectedComponent);
                                                System.out.println("Компонент '" + selectedComponent.getName() + "' добавлен.");
                                            } else {
                                                System.out.println("Неверный выбор компонента.");
                                            }
                                        } else {
                                            System.out.print("Введите название компонента: ");
                                            String componentName = scanner.next();
                                            System.out.print("Введите цену компонента: ");
                                            double componentPrice = scanner.nextDouble();
                                            System.out.println("Введите рейтинг: ");
                                            double componentRating = scanner.nextDouble();
                                            if (componentPrice < 0) {
                                                throw new InvalidPriceException("Цена не может быть отрицательной!");
                                            }

                                            ComputerComponent component = new ComputerComponent(componentName, componentPrice, componentRating);
                                            Catalog.add(component);
                                            System.out.println("Компонент '" + componentName + "' добавлен.");
                                        }
                                        break;
                                    case 2:
                                        if (FilterCatalog.equals(Catalog)) {
                                            System.out.println("Комплектующих нет");
                                        } else {
                                            System.out.println("Добавленные комплектующие:");
                                            for (int i = 0; i < Catalog.size(); i++) {
                                                ComputerComponent c = Catalog.get(i);
                                                System.out.println((i + 1) + ". " + c.getName() + " - $" + c.getPrice());
                                            }
                                        }
                                        break;
                                    case 3:
                                        ArrayList<ComputerComponent> finalFilterCatalog = Catalog;
                                        Operation calculateTotalPrice = (a) -> {
                                            double totalPrice = 0.0; // Инициализируем общую стоимость нулем
                                            for (ComputerComponent i : finalFilterCatalog) { // Проходим по каждой комплектующей
                                                totalPrice += i.getPrice(); // Добавляем цену текущей комплектующей к общей стоимости
                                            }

                                            return totalPrice;
                                        };

                                        // Вызываем лямбда-функцию
                                        double totalPrice = calculateTotalPrice.cost(0.0);
                                        System.out.println("Общая стоимость компьютера: $" + totalPrice);
                                        break;
                                    case 4: // Применить фильтры
                                        System.out.println("Фильтрация комплектующих:");
                                        System.out.println("1. Фильтр по цене меньше заданной");
                                        System.out.println("2. Фильтр по цене больше заданной");
                                        System.out.println("3. Очистить фильтры");
                                        int filterChoice = scanner.nextInt();

                                        switch (filterChoice) {
                                            case 1:
                                                if (Computerlist.isEmpty()) {
                                                    System.out.println("Нет компонентов");
                                                } else {
                                                    System.out.println("Введите максимальную цену: ");
                                                    double max_price = scanner.nextDouble();

                                                    //ArrayList<ComputerComponent> maxPriceFilter = FilterByMax(Computerlist, max_price);
                                                    ArrayList<ComputerComponent> maxPriceFilter = FilterByMax(FilterCatalog, max_price);
                                                    FilterCatalog = combineFilters(FilterCatalog, maxPriceFilter); //типо комбинируем в общем списке
                                                    Filter_Price_Max = combineFilters(Filter_Price_Max, maxPriceFilter);
                                                    maxFilter = false;
                                                }
                                                break;
                                            case 2:
                                                if (Computerlist.isEmpty()) {
                                                    System.out.println("Нет компонентов");
                                                } else {
                                                    System.out.println("Введите минимальную цену: ");
                                                    double min_price = scanner.nextDouble();

                                                    //ArrayList<ComputerComponent> minPriceFilter = FilterByMin(Computerlist, min_price);
                                                    ArrayList<ComputerComponent> minPriceFilter = FilterByMin(FilterCatalog, min_price);
                                                    FilterCatalog = combineFilters(FilterCatalog, minPriceFilter);
                                                    Filer_Price_Min = combineFilters(Filer_Price_Min, minPriceFilter);
                                                    minFilter = false;
                                                }
                                                break;
                                            case 3:
                                                System.out.println("1. Очистить фильтр по цене меньше заданной");
                                                System.out.println("2. Очистить фильтр по цене больше заданной");
                                                System.out.println("3. Очистить все фильтры");
                                                int clearChoice = scanner.nextInt();

                                                switch (clearChoice) {
                                                    case 1:
                                                        Filter_Price_Max.clear(); // Очищаем фильтр по цене больше заданной
                                                        FilterCatalog.clear(); // Очищаем общий фильтр
                                                        FilterCatalog.addAll(Filer_Price_Min); // Восстанавливаем фильтр по цене больше заданной из временного списка

                                                        break;
                                                    case 2:
                                                        Filer_Price_Min.clear();
                                                        FilterCatalog.clear();
                                                        FilterCatalog.addAll(Filter_Price_Max);
                                                        break;
                                                    case 3:
                                                        Filter_Price_Max.clear();
                                                        Filer_Price_Min.clear();
                                                        FilterCatalog.clear();
                                                        FilterCatalog.addAll(Computerlist); //обновляется копией
                                                        if (sortIncrease) //если использовали сортировку по возрастанию
                                                        {
                                                            SortRunnable sortRunnable = new SortRunnable(FilterCatalog);
                                                            sortRunnable.run();  // Запуск сортировки в текущем потоке
                                                        } else if (sortDecrease) {
                                                            SortThread sortThread = new SortThread(FilterCatalog);
                                                            Thread threadSort = new Thread(sortThread);
                                                            threadSort.start();     // Запуск сортировки по убыванию в новом потоке
                                                        }
                                                        //FilterCatalog = new ArrayList<>(Computerlist); //обновляется копией Catalog
                                                        System.out.println("Фильтры очищены.");
                                                        minFilter = true;
                                                        maxFilter = true;
                                                        break;
                                                    default:
                                                        throw new InvalidException("Выбрана неверная цифра");
                                                }
                                                break;
                                            default:
                                                throw new InvalidException("Выбрана неверная цифра");
                                        }
                                        break;
                                    case 5:
                                        if (FilterCatalog.isEmpty()) {
                                            System.out.println("Комплектующих нет");
                                        } else {
                                            System.out.println("Доступные комплектующие:");
                                            for (int i = 0; i < FilterCatalog.size(); i++) {
                                                ComputerComponent c = FilterCatalog.get(i);
                                                System.out.println((i + 1) + ". " + c.getName() + " - $" + c.getPrice());
                                            }
                                        }

                                        break;
                                    case 6:
                                        System.out.println("1 - сортировка по возрастанию");
                                        System.out.println("2 - сортировка по убыванию");
                                        System.out.println("3 - отменить сортировку");
                                        int sortchoice = scanner.nextInt();
                                        switch (sortchoice) {
                                            case 1:
                                                SortRunnable sortRunnable = new SortRunnable(FilterCatalog);
                                                sortRunnable.run();  // Запуск сортировки по возрастанию в текущем потоке

                                                sortIncrease = true;
                                                sortDecrease = false; //при нажатии по возрастанию без отмены
                                                break;
                                            case 2:
                                                SortThread sortThread = new SortThread(FilterCatalog);
                                                Thread threadSort = new Thread(sortThread);
                                                threadSort.start();     // Запуск сортировки по убыванию в новом потоке
                                                sortDecrease = true;
                                                sortIncrease = false;
                                                break;
                                            case 3:
                                                if (!maxFilter) {
                                                    FilterCatalog.clear();
                                                    FilterCatalog.addAll(Filter_Price_Max); // Восстанавливаем данные из сохраненного списка
                                                } else if (!minFilter) {
                                                    FilterCatalog.clear();
                                                    FilterCatalog.addAll(Filer_Price_Min); // Восстанавливаем данные из сохраненного списка
                                                } else {
                                                    FilterCatalog.clear();
                                                    FilterCatalog.addAll(Computerlist);
                                                }

                                                System.out.println("Сортировка отменена");

                                                sortIncrease = false;
                                                sortDecrease = false;
                                                break;
                                            default:
                                                throw new InvalidException("Выбрана неверная цифра");
                                        }
                                        break;
                                    case 7:
                                        check_user = false;
                                        System.out.println("Успешно вышли");
                                        check_welcome = true;
                                        break; // выход из внутреннего цикла
                                    default:
                                        throw new InvalidException("Выбрана неверная цифра");
                                }
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

            } while (check_admin && check_user);
        } while (true);

    }

    public static ArrayList<ComputerComponent> FilterByMax(ArrayList<ComputerComponent> FilterComponent, double max_price) {
        return (ArrayList<ComputerComponent>) FilterComponent.stream().filter(p -> p.getPrice() <= max_price)
                .collect(Collectors.toList());

    }

    public static ArrayList<ComputerComponent> FilterByMin(ArrayList<ComputerComponent> FilterComponent, double min_price) {
        return (ArrayList<ComputerComponent>) FilterComponent.stream().filter(p -> p.getPrice() >= min_price)
                .collect(Collectors.toList());
    }

    // Функция для объединения двух списков
    private static ArrayList<ComputerComponent> combineFilters(ArrayList<ComputerComponent> list1, ArrayList<ComputerComponent> list2) {
        ArrayList<ComputerComponent> combinedList = new ArrayList<>(list1);
        combinedList.retainAll(list2);
        return combinedList;
    }


}


class InvalidException extends Exception{
    public InvalidException (String message)
    {
        super(message);
    }
}

class InvalidPriceException extends Exception
{
    public InvalidPriceException (String message)
    {
        super(message);
    }
}

@FunctionalInterface
interface Operation {
    double cost(double a);
}


class SortRunnable implements Runnable {
    private ArrayList<ComputerComponent> sortCatalog;

    public SortRunnable(ArrayList<ComputerComponent> catalog) {
        this.sortCatalog = catalog;
    }

    @Override
    public void run() {
        synchronized (sortCatalog) {
            Collections.sort(sortCatalog, new Comparator<ComputerComponent>() {
                @Override
                public int compare(ComputerComponent o1, ComputerComponent o2) {
                    return Double.compare(o1.getPrice(), o2.getPrice());
                }
            });
            System.out.println("Сортировка по возрастанию в потоке Runnable");
        }
    }
}

class SortThread extends Thread {
    private ArrayList<ComputerComponent> sortCatalog;

    public SortThread(ArrayList<ComputerComponent> catalog) {
        this.sortCatalog = catalog;
    }

    @Override
    public void run() {
        synchronized (sortCatalog) {
            Collections.sort(sortCatalog, new Comparator<ComputerComponent>() {
                @Override
                public int compare(ComputerComponent o1, ComputerComponent o2) {
                    return Double.compare(o2.getPrice(), o1.getPrice());
                }
            });
            System.out.println("Сортировка по убыванию в потоке Thread");
        }
    }
}