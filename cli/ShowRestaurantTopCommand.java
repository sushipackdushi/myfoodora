package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;
import java.util.*;

public class ShowRestaurantTopCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Manager)) {
            throw new IllegalStateException("Only managers can view restaurant statistics");
        }
        
        if (args.length != 0) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        MyFoodora myFoodora = cli.getMyFoodora();
        Map<Restaurant, Integer> orderCounts = new HashMap<>();
        
        // Count orders for each restaurant
        for (Order order : myFoodora.getCompletedOrders()) {
            Restaurant restaurant = order.getRestaurant();
            orderCounts.merge(restaurant, 1, Integer::sum);
        }
        
        // Convert to list and sort by order count
        List<Map.Entry<Restaurant, Integer>> sortedRestaurants = 
            new ArrayList<>(orderCounts.entrySet());
        Collections.sort(sortedRestaurants, 
            (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        
        System.out.println("Restaurants sorted by number of orders:");
        System.out.println("-------------------------------------");
        for (Map.Entry<Restaurant, Integer> entry : sortedRestaurants) {
            Restaurant restaurant = entry.getKey();
            int orders = entry.getValue();
            System.out.printf("%s: %d orders%n", restaurant.getName(), orders);
        }
        
        if (sortedRestaurants.isEmpty()) {
            System.out.println("No orders have been completed yet.");
        } else {
            System.out.println("\nMost selling restaurant: " + 
                sortedRestaurants.get(0).getKey().getName());
            System.out.println("Least selling restaurant: " + 
                sortedRestaurants.get(sortedRestaurants.size() - 1).getKey().getName());
        }
    }

    @Override
    public String getDescription() {
        return "Show list of restaurants sorted by number of orders (manager only)";
    }

    @Override
    public String getSyntax() {
        return "showRestaurantTop";
    }
} 