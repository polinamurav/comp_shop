
//Фабричный метод - порождающий паттерн - ConcreteComponentFactory
public interface ComputerComponentFactory {
    ComputerComponent createComponent(String name, double price, double rating);
}