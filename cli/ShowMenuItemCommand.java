package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class ShowMenuItemCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String restaurantName = args[0];
        MyFoodora myFoodora = cli.getMyFoodora();
        Restaurant restaurant = null;
        
        // Find the restaurant
        for (User user : myFoodora.getUsers()) {
            if (user instanceof Restaurant && user.getName().equals(restaurantName)) {
                restaurant = (Restaurant) user;
                break;
            }
        }
        
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant '" + restaurantName + "' not found");
        }
        
        // Display menu items
        System.out.println("Menu for " + restaurantName + ":");
        System.out.println("------------------------");
        
        System.out.println("\nStarters:");
        System.out.println("---------");
        for (Dish dish : restaurant.getMenu().getStarters()) {
            printDish(dish);
        }
        
        System.out.println("\nMain Dishes:");
        System.out.println("------------");
        for (Dish dish : restaurant.getMenu().getMainDishes()) {
            printDish(dish);
        }
        
        System.out.println("\nDesserts:");
        System.out.println("---------");
        for (Dish dish : restaurant.getMenu().getDesserts()) {
            printDish(dish);
        }
        
        System.out.println("\nMeal Deals:");
        System.out.println("-----------");
        for (Meal meal : restaurant.getMenu().getMeals()) {
            System.out.printf("%s (%.2f€)%n", meal.getName(), meal.getPrice());
            if (restaurant.getMenu().isMealOfTheWeek(meal)) {
                System.out.println("*** SPECIAL OFFER ***");
            }
            System.out.println("Contents:");
            for (Dish dish : meal.getDishes()) {
                System.out.printf("  - %s%n", dish.getName());
            }
            System.out.println();
        }
    }
    
    private void printDish(Dish dish) {
        System.out.printf("%s (%.2f€) - %s %s%n", 
            dish.getName(), 
            dish.getPrice(),
            dish.getClass().getSimpleName(),
            dish.getType());
    }

    @Override
    public String getDescription() {
        return "Show menu items for a specific restaurant";
    }

    @Override
    public String getSyntax() {
        return "showMenuItems <restaurantName>";
    }
} 