// File: HalfMeal.java
package fr.cs.groupS.myFoodora;

import java.util.ArrayList;
import java.util.List;

public class HalfMeal extends Meal {
    private static final long serialVersionUID = 6145055154250167805L;
    private Dish sideDish;

    /**
     * creates HalfMeal object of given mainDish, sideDish
     * @param name : name of meal
     */
    public HalfMeal(String name) {
        super(name);
        this.sideDish = null;
        this.discountRate = 0.0;  // no discount by default when adding later
    }

    /**
     * creates HalfMeal object of given mainDish and sideDish
     * @param name     : name of meal
     * @param mainDish : mainDish of meal
     * @param sideDish : sideDish of meal
     */
    public HalfMeal(String name, MainDish mainDish, Dish sideDish) {
        super(name, mainDish);
        this.sideDish = sideDish;
        this.discountRate = 0.05;  // default 5% discount for full constructor

        if (mainDish.getType().equals(sideDish.getType())) {
            this.type = mainDish.getType();
        } else {
            this.type = null;
        }

        this.price = this.calcPrice();
    }

    @Override
    public double calcPrice() {
        double sum = 0.0;
        if (this.mainDish != null) sum += this.mainDish.getPrice();
        if (this.sideDish != null) sum += this.sideDish.getPrice();
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
     * adds dish to meal if still possible
     * @param dish : dish to be added to meal
     * @throws NoPlaceInMealException : if meal is already full
     */
    @Override
    public void addDish(Dish dish) throws NoPlaceInMealException {
        this.mealVisitor.addDishToFullMeal(dish, this);
        this.price = this.calcPrice();
    }

    public Dish getSideDish() { return sideDish; }

    public void setSideDish(Dish sideDish) { this.sideDish = sideDish; }

    @Override
    public String toString() { return ("\nHalf" + super.toString() + sideDish); }

    @Override
    public List<Dish> getDishes() {
        List<Dish> dishes = new ArrayList<>();
        if (mainDish != null) dishes.add(mainDish);
        if (sideDish != null) dishes.add(sideDish);
        return dishes;
    }
}
