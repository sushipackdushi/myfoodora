package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;
import java.util.*;

/**
 * ShowRestaurantsCommand: lists every restaurant in the system,
 * printing all of its fields (including password) and specific Restaurant fields.
 * Can be invoked by any logged-in user; no manager-only restriction.
 */
public class ShowRestaurantsCommand implements CLICommand {

    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        // 1) Syntax check: no extra arguments allowed
        if (args.length != 0) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }

        // 2) Get reference to the core MyFoodora object
        MyFoodora myFoodora = cli.getMyFoodora();

        // 3) Build a list of all Restaurant instances
        List<Restaurant> restaurants = new ArrayList<>();
        for (User user : myFoodora.getUsers()) {
            if (user instanceof Restaurant) {
                restaurants.add((Restaurant) user);
            }
        }

        // 4) Sort them by username (lexicographically)
        Collections.sort(
            restaurants,
            (r1, r2) -> r1.getUsername().compareTo(r2.getUsername())
        );

        // 5) Print header
        System.out.println("Restaurant List:");
        System.out.println("----------------");

        // 6) If there are no restaurants, notify and return
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants registered in the system.");
            return;
        }

        // 7) For each restaurant, print every field
        for (Restaurant r : restaurants) {
            // Inherited from User:
            System.out.printf("Username: %s%n",   r.getUsername());
            System.out.printf("User ID: %s%n",    r.getUserID());
            System.out.printf("Name: %s %s%n",    r.getName(), r.getSurname());
            System.out.printf("Password: %s%n",   r.getPassword());

            // Restaurant‐specific:
            Coordinate loc = r.getLocation();
            System.out.printf("Location: (%.2f, %.2f)%n",
                              loc.getX(), loc.getY());

            System.out.printf("Generic Discount: %.2f%n", r.getGenericDiscount());
            System.out.printf("Special Discount: %.2f%n", r.getSpecialDiscount());
            System.out.printf("Times Ordered (counter): %d%n", r.getCounter());

            // Menu summary:
            Menu menu = r.getMenu();
            System.out.printf("Number of Dishes: %d%n",    menu.getDishes().size());
            System.out.printf("Number of Full Meals: %d%n", menu.getMeals().size());

            System.out.println(); // Blank line between restaurants
        }
    }

    @Override
    public String getDescription() {
        return "Show list of all registered restaurants (any user)";
    }

    @Override
    public String getSyntax() {
        return "showRestaurants";
    }
}
