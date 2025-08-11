package fr.cs.groupS.myFoodora;

public class MainDish extends Dish {

    private static final long serialVersionUID = -7923827128161826271L;

    public MainDish(String name, double price, String type) {
        super(name, price, type);
        this.dishType = "MainDish";
    }
    
    @Override
    public String toString() {
        return ("MainDish : " + super.toString());
    }
}
