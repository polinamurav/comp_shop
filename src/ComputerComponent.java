import java.io.Serializable;

//реализация класса комплектующих
class ComputerComponent implements Serializable {
    private String name;
    private double price;
    private double rating;

    public ComputerComponent(String name, double price, double rating) {
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "\nНазвание: " + name + " - $" + price + " - " + rating;
    }

}