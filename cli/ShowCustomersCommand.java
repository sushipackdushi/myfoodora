package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;
import java.util.*;

public class ShowCustomersCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Manager)) {
            throw new IllegalStateException("Only managers can view customer information");
        }
        
        if (args.length != 0) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        MyFoodora myFoodora = cli.getMyFoodora();
        List<Customer> customers = new ArrayList<>();
        
        // Collect all customers
        for (User user : myFoodora.getUsers()) {
            if (user instanceof Customer) {
                customers.add((Customer) user);
            }
        }
        
        // Sort alphabetically by username
        Collections.sort(customers, (c1, c2) -> c1.getUsername().compareTo(c2.getUsername()));
        
        System.out.println("Customer List:");
        System.out.println("--------------");
        for (Customer customer : customers) {
            System.out.printf("Username: %s%n", customer.getUsername());
            System.out.printf("Name: %s %s%n", customer.getName(), customer.getSurname());
            System.out.printf("Address: %s%n", customer.getAddress());
            System.out.printf("Phone: %s%n", customer.getPhoneNumber());
            System.out.printf("Email: %s%n", customer.getEmail());
            System.out.printf("Fidelity Card: %s%n", 
                customer.getFidelityCard() != null ? 
                customer.getFidelityCard().getClass().getSimpleName() : "None");
            System.out.println();
        }
        
        if (customers.isEmpty()) {
            System.out.println("No customers registered in the system.");
        }
    }

    @Override
    public String getDescription() {
        return "Show list of all registered customers (manager only)";
    }

    @Override
    public String getSyntax() {
        return "showCustomers";
    }
} 