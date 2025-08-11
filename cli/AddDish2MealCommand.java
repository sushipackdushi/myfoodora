package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class AddDish2MealCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Restaurant)) {
            throw new IllegalStateException("Only restaurants can add dishes to meals");
        }
        
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String dishName = args[0];
        String mealName = args[1];
        
        Restaurant restaurant = (Restaurant) cli.getCurrentUser();
        Menu menu = restaurant.getMenu();
        
        // Find the dish
        Dish dish = menu.findDish(dishName);
        if (dish == null) {
            throw new IllegalArgumentException("Dish '" + dishName + "' not found in menu");
        }
        
        // Find the meal
        Meal meal = menu.findMeal(mealName);
        if (meal == null) {
            throw new IllegalArgumentException("Meal '" + mealName + "' not found");
        }
        
        try {
            meal.addDish(dish);
            System.out.println("Added dish '" + dishName + "' to meal '" + mealName + "'");
        } catch (NoPlaceInMealException e) {
            throw new IllegalStateException("Cannot add more dishes to this meal: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Add a dish to a meal (restaurant only)";
    }

    @Override
    public String getSyntax() {
        return "addDish2Meal <dishName> <mealName>";
    }
} 