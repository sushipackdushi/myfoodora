package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class RemoveFromSpecialOfferCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Restaurant)) {
            throw new IllegalStateException("Only restaurants can manage special offers");
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
        
        if (!menu.isMealOfTheWeek(meal)) {
            throw new IllegalStateException("'" + mealName + "' is not currently a special offer");
        }
        
        menu.removeMealOfTheWeek();
        System.out.println("Successfully removed '" + mealName + "' from meal of the week special offer");
    }

    @Override
    public String getDescription() {
        return "Remove a meal from being the special offer of the week (restaurant only)";
    }

    @Override
    public String getSyntax() {
        return "removeFromSpecialOffer <mealName>";
    }
} 