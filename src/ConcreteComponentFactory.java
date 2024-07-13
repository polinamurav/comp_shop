//реализация фабричного метода
class ConcreteComponentFactory implements ComputerComponentFactory {
    @Override
    public ComputerComponent createComponent(String name, double price, double rating) {
        return new ComputerComponent(name, price, rating);
    }

}