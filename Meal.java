package fr.cs.groupS.myFoodora;

import java.util.List;

public abstract class Meal extends FoodItem {
    
    private static final long serialVersionUID = -7344477643703387669L;
    private String name;
    protected MainDish mainDish;
    protected String type = "standard";
    // Restore default discountRate to 5%
    protected double discountRate = 0.05;
    protected MealVisitor mealVisitor = new MealVisitor();

    /** create meal with name */
    public Meal(String name) {
        this.name = name;
        this.mainDish = null;
        // keep discountRate at default 0.05
    }

    /** create meal with name and mainDish */
    public Meal(String name, MainDish mainDish) {
        this.name = name;
        this.mainDish = mainDish;
        // keep discountRate at default 0.05
    }

    public abstract double calcPrice();
    
    public abstract void update(Menu menu);
   
    public abstract void addDish(Dish item) throws NoPlaceInMealException;

    public abstract List<Dish> getDishes();

    public String getName() { return name; }
    
    public void setName(String name) { this.name = name; }
    
    public MainDish getMainDish() { return mainDish; }
    
    public void setMainDish(MainDish mainDish) { this.mainDish = mainDish; }
    
    public String getType() { return type; }
    
    public void setType(String type) { this.type = type; }
    
    public double getDiscountRate() { return discountRate; }
    
    @Override
    public String toString() {
        return "Meal: " + name + "\nprice: " + getPrice() + " type: " + type + "\n" + mainDish;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Meal) {
            Meal other = (Meal) obj;
            return this.name.equals(other.getName());
        }
        return false;
    }

}
