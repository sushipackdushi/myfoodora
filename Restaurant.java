package fr.cs.groupS.myFoodora;

import java.util.List;

public class Restaurant extends User {
    private Coordinate location;
    private double genericDiscount = 0.05;
    private double specialDiscount = 0.10;
    private int counter = 0;
    
    private Menu menu;
    private List<Meal> meals;

    public Restaurant(String name, String surname, String userID, String username, String password,
                      Coordinate location, Menu menu, List<Meal> meals) {
        super(name, surname, userID, username, password);
        this.location = location;
        this.menu = menu;
        this.meals = meals;
    }
    
    @Override
    public String getUserType() {
        return "Restaurant";
    }

    public void setGenericDiscount(double discount) {
        this.genericDiscount = discount;
    }

    public void setSpecialDiscount(double discount) {
        this.specialDiscount = discount;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public double getGenericDiscount() {
        return genericDiscount;
    }

    public double getSpecialDiscount() {
        return specialDiscount;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

	public void increaseCounter() {
		this.counter++;
	}
	
	public int getCounter() {
		return this.counter;
	}

    public String getName() {
        return name;
    }
}