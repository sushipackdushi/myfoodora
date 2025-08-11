package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class RegisterCustomerCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Manager)) {
            throw new IllegalStateException("Only managers can register customers");
        }
        
        if (args.length != 5) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String firstName = args[0];
        String lastName = args[1];
        String username = args[2];
        
        String[] coords = args[3].split(",");
        if (coords.length != 2) {
            throw new IllegalArgumentException("Address must be in format 'x,y'");
        }
        
        double x = Double.parseDouble(coords[0]);
        double y = Double.parseDouble(coords[1]);
        String password = args[4];
        
        String userID = cli.getMyFoodora().generateNewUserId();
        Customer customer = new Customer(firstName, lastName, userID, username, password, new Coordinate(x, y), "", "");
        cli.getMyFoodora().addUser(customer);
        
        System.out.println("Successfully registered customer: " + firstName + " " + lastName);
    }

    @Override
    public String getDescription() {
        return "Register a new customer (manager only)";
    }

    @Override
    public String getSyntax() {
        return "registerCustomer <firstName> <lastName> <username> <address> <password>";
    }
} 