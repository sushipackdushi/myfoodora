package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class CreateMealCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Restaurant)) {
            throw new IllegalStateException("Only restaurants can create meals");
        }
        
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String mealName = args[0];
        Restaurant restaurant = (Restaurant) cli.getCurrentUser();
        
        // Create a temporary meal - it will be finalized as half or full meal when saved
        Meal meal = new FullMeal(mealName); // Using FullMeal as temporary container
        restaurant.getMenu().addMeal(meal);
        
        System.out.println("Created new meal '" + mealName + "'. Use addDish2Meal to add dishes.");
    }

    @Override
    public String getDescription() {
        return "Create a new meal (restaurant only)";
    }

    @Override
    public String getSyntax() {
        return "createMeal <mealName>";
    }
} 