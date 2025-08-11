package fr.cs.groupS.myFoodora;

public class Dessert extends Dish {

    private static final long serialVersionUID = -7080867195338962007L;

    public Dessert(String name, double price, String type) {
        super(name, price, type);
        this.dishType = "Dessert";
    }

    @Override
    public String toString() {
        return ("Dessert : " + super.toString());
    }
}
