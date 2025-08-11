package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class SetSpecialOfferCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Restaurant)) {
            throw new IllegalStateException("Only restaurants can set special offers");
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
        
        // Set this meal as the meal of the week
        menu.setMealOfTheWeek(meal);
        
        // Notify all customers who have agreed to receive notifications
        cli.getMyFoodora().notifySpecialOffer(restaurant, meal);
        
        System.out.println("Successfully set '" + mealName + "' as meal of the week");
        System.out.println("Customers have been notified of the special offer");
    }

    @Override
    public String getDescription() {
        return "Set a meal as the special offer of the week (restaurant only)";
    }

    @Override
    public String getSyntax() {
        return "setSpecialOffer <mealName>";
    }
} 