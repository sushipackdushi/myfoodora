package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class AddDishRestaurantMenuCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Restaurant)) {
            throw new IllegalStateException("Only restaurants can add dishes to their menu");
        }
        
        if (args.length != 4) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String dishName = args[0];
        String category = args[1].toLowerCase();
        String foodType = args[2].toLowerCase();
        double price = Double.parseDouble(args[3]);
        
        Restaurant restaurant = (Restaurant) cli.getCurrentUser();
        Dish dish;
        
        // Create appropriate dish type based on category
        switch (category) {
            case "starter":
                dish = new Starter(dishName, price, foodType);
                break;
            case "main":
                dish = new MainDish(dishName, price, foodType);
                break;
            case "dessert":
                dish = new Dessert(dishName, price, foodType);
                break;
            default:
                throw new IllegalArgumentException("Invalid dish category. Must be starter, main, or dessert");
        }
        
        restaurant.getMenu().addDish(dish);
        System.out.println("Successfully added dish '" + dishName + "' to menu");
    }

    @Override
    public String getDescription() {
        return "Add a dish to the restaurant's menu (restaurant only)";
    }

    @Override
    public String getSyntax() {
        return "addDishRestaurantMenu <dishName> <category> <foodType> <unitPrice>";
    }
} 