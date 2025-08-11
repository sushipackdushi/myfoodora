// File: FullMeal.java
package fr.cs.groupS.myFoodora;

import java.util.ArrayList;
import java.util.List;

public class FullMeal extends Meal {
    private static final long serialVersionUID = 4781773416405710381L;
    private Starter starter;
    private Dessert dessert;

    /**
     * creates FullMeal object of given main, starter and dessert
     * @param name : name of meal
     */
    public FullMeal(String name) {
        super(name);
        this.starter = null;
        this.dessert = null;
        this.discountRate = 0.0;  // no discount by default when adding later
    }    

    /**
     * creates FullMeal object of given main, starter and dessert
     * @param name   : meal name 
     * @param main   : meal main 
     * @param starter: meal starter 
     * @param dessert: meal dessert 
     */
    public FullMeal(String name, MainDish main, Starter starter, Dessert dessert) {
        super(name, main);
        this.starter = starter;
        this.dessert = dessert;
        this.discountRate = 0.05;  // default 5% discount for full constructor

        if ((main.getType().equals(starter.getType())) && (main.getType().equals(dessert.getType()))) {
            this.type = main.getType();
        } else {
            this.type = null;
        }

        this.price = this.calcPrice();
    }

    @Override
    public double calcPrice() {
        double sum = 0.0;
        if (this.mainDish != null) sum += this.mainDish.getPrice();
        if (this.starter  != null) sum += this.starter.getPrice();
        if (this.dessert  != null) sum += this.dessert.getPrice();
        return sum * (1 - this.discountRate);
    }

    @Override
    public void update(Menu menu) {
        double genericDiscountRate = menu.getGenericDiscountRate();
        double specialDiscountRate = menu.getSpecialDiscountRate();
        Meal mealOfTheWeek = menu.getMealOfTheWeek();

        if (this == mealOfTheWeek) {
            this.discountRate = specialDiscountRate;
        } else {
            this.discountRate = genericDiscountRate;
        }
        this.price = this.calcPrice();
    }

    /**
     * adds dish to meal if possible
     * @param dish : dish we want to add to meal
     * @throws NoPlaceInMealException : if meal is complete
     */
    @Override
    public void addDish(Dish dish) throws NoPlaceInMealException {
        this.mealVisitor.addDishToFullMeal(dish, this);
        this.price = this.calcPrice();
    }

    public Starter getStarter() { return starter; }

    public void setStarter(Starter starter) { 
        this.starter = starter; 
    }

    public Dessert getDessert() { return dessert; }

    public void setDessert(Dessert dessert) { 
        this.dessert = dessert; 
    }

    public static long getSerialversionuid() { return serialVersionUID; }

    @Override
    public String toString() {
        return "FullMeal [starter=" + starter + ", dessert=" + dessert + "]";
    }

    @Override
    public List<Dish> getDishes() {
        List<Dish> dishes = new ArrayList<>();
        if (starter != null)    dishes.add(starter);
        if (mainDish != null)   dishes.add(mainDish);
        if (dessert != null)    dishes.add(dessert);
        return dishes;
    }
}
