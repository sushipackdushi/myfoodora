package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;
import java.util.List;

public class ShowMealCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Restaurant)) {
            throw new IllegalStateException("Only restaurants can view their meals");
        }
        
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String mealName = args[0];
        Restaurant restaurant = (Restaurant) cli.getCurrentUser();
        Menu menu = restaurant.getMenu();
        
        Meal meal = menu.findMeal(mealName);
        if (meal == null) {
            throw new IllegalArgumentException("Meal '" + mealName + "' not found");
        }
        
        System.out.println("Meal: " + mealName);
        System.out.println("Type: " + (meal instanceof FullMeal ? "Full Meal" : "Half Meal"));
        System.out.println("Dishes:");
        
        List<Dish> dishes = meal.getDishes();
        for (Dish dish : dishes) {
            System.out.printf("  - %s (%s, %s) %.2f€%n", 
                dish.getName(),
                dish.getClass().getSimpleName(),
                dish.getType(),
                dish.getPrice());
        }
        
        System.out.printf("Total Price: %.2f€%n", meal.getPrice());
    }

    @Override
    public String getDescription() {
        return "Show the details of a meal (restaurant only)";
    }

    @Override
    public String getSyntax() {
        return "showMeal <mealName>";
    }
} 