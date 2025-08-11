package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;
import java.util.List;

public class SaveMealCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Restaurant)) {
            throw new IllegalStateException("Only restaurants can save meals");
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
        
        List<Dish> dishes = meal.getDishes();
        if (dishes.isEmpty()) {
            throw new IllegalStateException("Cannot save empty meal");
        }

        // Verify all dishes are of the same type
        String mealType = dishes.get(0).getType(); // standard, vegetarian, or glutenFree
        for (Dish dish : dishes) {
            if (!dish.getType().equals(mealType)) {
                throw new IllegalStateException("All dishes in a meal must be of the same type (standard, vegetarian, or gluten-free)");
            }
        }
        
        // Count dishes by category
        int starters = 0, mains = 0, desserts = 0;
        for (Dish dish : dishes) {
            if (dish instanceof Starter) starters++;
            else if (dish instanceof MainDish) mains++;
            else if (dish instanceof Dessert) desserts++;
        }

        // Validate and create appropriate meal
        Meal finalMeal;
        if (dishes.size() == 2 && mains == 1) {
            // Half meal must have exactly one main dish and either a starter or dessert
            if (starters == 1 || desserts == 1) {
                finalMeal = new HalfMeal(mealName);
                for (Dish dish : dishes) {
                    finalMeal.addDish(dish);
                }
            } else {
                throw new IllegalStateException("Half meal must have one main dish and either a starter or dessert");
            }
        } else if (dishes.size() == 3 && starters == 1 && mains == 1 && desserts == 1) {
            // Full meal must have exactly one of each
            finalMeal = new FullMeal(mealName);
            // Add in correct order: starter, main, dessert
            for (Dish dish : dishes) {
                if (dish instanceof Starter) finalMeal.addDish(dish);
            }
            for (Dish dish : dishes) {
                if (dish instanceof MainDish) finalMeal.addDish(dish);
            }
            for (Dish dish : dishes) {
                if (dish instanceof Dessert) finalMeal.addDish(dish);
            }
        } else {
            throw new IllegalStateException("Meal must be either:\n" +
                "- Half meal: 1 main dish + 1 starter/dessert\n" +
                "- Full meal: 1 starter + 1 main dish + 1 dessert");
        }

        // Set the meal type based on its dishes
        finalMeal.setType(mealType);
        
        // Replace the temporary meal with the final one
        menu.removeMeal(meal);
        menu.addMeal(finalMeal);
        
        System.out.println("Successfully saved meal '" + mealName + "' as a " + 
            (finalMeal instanceof FullMeal ? "full meal" : "half meal") +
            " of type " + mealType);
    }

    @Override
    public String getDescription() {
        return "Save a meal after adding all dishes (restaurant only)";
    }

    @Override
    public String getSyntax() {
        return "saveMeal <mealName>";
    }
} 