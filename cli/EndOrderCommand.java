package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EndOrderCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Customer)) {
            throw new IllegalStateException("Only customers can finalize orders");
        }
        
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String orderName = args[0];
        String dateStr = args[1];
        
        Customer customer = (Customer) cli.getCurrentUser();
        Order order = customer.getOrder(orderName);
        
        if (order == null) {
            throw new IllegalArgumentException("Order '" + orderName + "' not found");
        }
        
        // Parse date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate = dateFormat.parse(dateStr);
        order.setDate(orderDate);
        
        // Apply fidelity card discount if applicable
        FidelityCard card = customer.getFidelityCard();
        if (card != null) {
            card.applyDiscount(order);
        }
        
        // Add service fee and delivery cost
        MyFoodora myFoodora = cli.getMyFoodora();
        double finalPrice = order.getPrice() * (1 + myFoodora.getMarkupPercentage()) 
                          + myFoodora.getServiceFee() + myFoodora.getDeliveryCost();
        order.setPrice(finalPrice);
        
        // Add to completed orders
        myFoodora.addCompletedOrder(order);
        customer.removeOrder(order);
        
        System.out.printf("Order completed. Final price: %.2f€%n", finalPrice);
        System.out.println("A courier will be assigned for delivery");
    }

    @Override
    public String getDescription() {
        return "Finalize and pay for an order (customer only)";
    }

    @Override
    public String getSyntax() {
        return "endOrder <orderName> <date>";
    }
} 