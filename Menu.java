package fr.cs.groupS.myFoodora;

import java.io.Serializable;
import java.util.ArrayList;

public class Menu implements Serializable {
	
    private static final long serialVersionUID = 2048174954578697379L;
    private ArrayList<Dish> dishes;
    private ArrayList<Meal> meals;
    private double genericDiscountRate = 0.05;
    private double specialDiscountRate = 0.1;
    private Meal mealOfTheWeek;

    public Menu() {
        dishes = new ArrayList<>();
        meals = new ArrayList<>();
    }

    public Menu(ArrayList<Dish> dishes, ArrayList<Meal> meals) {
        this.dishes = dishes;
        this.meals = meals;
    }

    // update all meals with current rates
    public void notifyMeals() {
        for (Meal meal : meals) {
            meal.update(this);
        }
    }

    public ArrayList<Dish> getDishes() { return dishes; }
    
    public ArrayList<Dish> getStarters() {
        ArrayList<Dish> starters = new ArrayList<>();
        for (Dish dish : dishes) {
            if (dish instanceof Starter) {
                starters.add(dish);
            }
        }
        return starters;
    }
    
    public ArrayList<Dish> getMainDishes() {
        ArrayList<Dish> mains = new ArrayList<>();
        for (Dish dish : dishes) {
            if (dish instanceof MainDish) {
                mains.add(dish);
            }
        }
        return mains;
    }
    
    public ArrayList<Dish> getDesserts() {
        ArrayList<Dish> desserts = new ArrayList<>();
        for (Dish dish : dishes) {
            if (dish instanceof Dessert) {
                desserts.add(dish);
            }
        }
        return desserts;
    }
    
    public void addDish(Dish dish) { dishes.add(dish); }
    
    public void removeDish(Dish dish) { dishes.remove(dish); }

    public ArrayList<Meal> getMeals() { return meals; }
    
    public void addMeal(Meal meal) { meals.add(meal); }
    
    public void removeMeal(Meal meal) { meals.remove(meal); }

    public double getGenericDiscountRate() { return genericDiscountRate; }
    
    public void setGenericDiscountRate(double rate) {
        this.genericDiscountRate = rate;
        notifyMeals();
    }

    public double getSpecialDiscountRate() { return specialDiscountRate; }
    
    public void setSpecialDiscountRate(double rate) {
        this.specialDiscountRate = rate;
        notifyMeals();
    }

    public Meal getMealOfTheWeek() { return mealOfTheWeek; }
    
    public void setMealOfTheWeek(Meal meal) {
        this.mealOfTheWeek = meal;
        notifyMeals();
    }

	@Override
	public String toString() {
		return "Menu \n[dishes=\n" + dishes + "\n\nmeals=\n" + meals + "\n\ngenericDiscountRate : " + genericDiscountRate
				+ ", \nspecialDiscountRate : " + specialDiscountRate + ", \n\nmealOfTheWeek :" + mealOfTheWeek + "]";
	}

    public Dish findDish(String dishName) {
        for (Dish dish : dishes) {
            if (dish.getName().equals(dishName)) {
                return dish;
            }
        }
        return null;
    }

    public Meal findMeal(String mealName) {
        for (Meal meal : meals) {
            if (meal.getName().equals(mealName)) {
                return meal;
            }
        }
        return null;
    }

    public boolean isMealOfTheWeek(Meal meal) {
        return meal.getName().equals(mealOfTheWeek.getName());
    }

    public void removeMealOfTheWeek() {
        mealOfTheWeek = null;
    }
	

}
