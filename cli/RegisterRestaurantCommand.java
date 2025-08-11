package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;
import java.util.ArrayList;

public class RegisterRestaurantCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Manager)) {
            throw new IllegalStateException("Only managers can register restaurants");
        }
        
        if (args.length != 4) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String name = args[0];
        String[] coords = args[1].split(",");
        if (coords.length != 2) {
            throw new IllegalArgumentException("Address must be in format 'x,y'");
        }
        
        double x = Double.parseDouble(coords[0]);
        double y = Double.parseDouble(coords[1]);
        String username = args[2];
        String password = args[3];
        
        String userID = cli.getMyFoodora().generateNewUserId();
        Restaurant restaurant = new Restaurant(name, userID, username, password, "", new Coordinate(x, y), new Menu(), new ArrayList<Meal>());
        cli.getMyFoodora().addUser(restaurant);
        
        System.out.println("Successfully registered restaurant: " + name);
    }

    @Override
    public String getDescription() {
        return "Register a new restaurant (manager only)";
    }

    @Override
    public String getSyntax() {
        return "registerRestaurant <name> <address> <username> <password>";
    }
} 