package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class AddItem2OrderCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Customer)) {
            throw new IllegalStateException("Only customers can add items to orders");
        }
        
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String orderName = args[0];
        String itemName = args[1];
        
        Customer customer = (Customer) cli.getCurrentUser();
        Order order = customer.getOrder(orderName);
        
        if (order == null) {
            throw new IllegalArgumentException("Order '" + orderName + "' not found");
        }
        
        Restaurant restaurant = order.getRestaurant();
        Menu menu = restaurant.getMenu();
        
        // Try to find item as a meal first
        Meal meal = menu.findMeal(itemName);
        if (meal != null) {
            order.addMeal(meal);
            System.out.println("Added meal '" + itemName + "' to order");
            return;
        }
        
        // If not a meal, try to find as a dish
        Dish dish = menu.findDish(itemName);
        if (dish != null) {
            order.addDish(dish);
            System.out.println("Added dish '" + itemName + "' to order");
            return;
        }
        
        throw new IllegalArgumentException("Item '" + itemName + "' not found in restaurant's menu");
    }

    @Override
    public String getDescription() {
        return "Add a dish or meal to an existing order (customer only)";
    }

    @Override
    public String getSyntax() {
        return "addItem2Order <orderName> <itemName>";
    }
} 