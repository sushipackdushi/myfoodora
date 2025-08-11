package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class CreateOrderCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Customer)) {
            throw new IllegalStateException("Only customers can create orders");
        }
        
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String restaurantUsername = args[0];
        String orderName = args[1];
        
        Customer customer = (Customer) cli.getCurrentUser();
        Restaurant restaurant = null;
        
        // Find the restaurant
        for (User user : cli.getMyFoodora().getUsers()) {
            if (user instanceof Restaurant && user.getUsername().equals(restaurantUsername)) {
                restaurant = (Restaurant) user;
                break;
            }
        }
        
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant '" + restaurantUsername + "' not found");
        }
        
        // Create new order
        Order order = new Order(customer, restaurant);
        customer.addOrder(orderName, order);
        
        System.out.println("Created new order '" + orderName + "' from restaurant '" + restaurant.getName() + "'");
        System.out.println("Use addItem2Order to add items to your order");
    }

    @Override
    public String getDescription() {
        return "Create a new order from a restaurant (customer only)";
    }

    @Override
    public String getSyntax() {
        return "createOrder <restaurantUsername> <orderName>";
    }
} 