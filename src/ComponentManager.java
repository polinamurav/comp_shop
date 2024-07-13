import java.util.ArrayList;
import java.util.Scanner;

//Абстрактный класс с шаблонным методом - поведенческий паттерн - потом в AdminManager
public abstract class ComponentManager {
    protected ArrayList<ComputerComponent> filterCatalog;
    Scanner scan = new Scanner(System.in);

    public ComponentManager(ArrayList<ComputerComponent> filterCatalog) {

        this.filterCatalog = filterCatalog;
    }

    protected abstract void displayMenu();

    protected abstract void performAction(int choice);

}