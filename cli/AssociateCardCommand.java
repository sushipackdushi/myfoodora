package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class AssociateCardCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Manager)) {
            throw new IllegalStateException("Only managers can associate fidelity cards");
        }
        
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String username = args[0];
        String cardType = args[1].toLowerCase();
        
        // Find the customer
        Customer customer = null;
        for (User user : cli.getMyFoodora().getUsers()) {
            if (user instanceof Customer && user.getUsername().equals(username)) {
                customer = (Customer) user;
                break;
            }
        }
        
        if (customer == null) {
            throw new IllegalArgumentException("Customer '" + username + "' not found");
        }
        
        // Create and associate the appropriate card
        FidelityCard card;
        switch (cardType) {
            case "basic":
                card = new BasicFidelityCard();
                break;
            case "point":
                card = new PointFidelityCard();
                break;
            case "lottery":
                card = new LotteryFidelityCard();
                break;
            default:
                throw new IllegalArgumentException("Unknown card type: " + cardType + 
                    ". Available types: basic, point, lottery");
        }
        
        customer.setFidelityCard(card);
        System.out.println("Successfully associated " + cardType + " fidelity card with customer " + username);
    }

    @Override
    public String getDescription() {
        return "Associate a fidelity card with a customer (manager only)";
    }

    @Override
    public String getSyntax() {
        return "associateCard <userName> <cardType>";
    }
} 