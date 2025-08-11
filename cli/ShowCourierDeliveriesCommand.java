package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;
import java.util.*;

public class ShowCourierDeliveriesCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Manager)) {
            throw new IllegalStateException("Only managers can view courier statistics");
        }
        
        if (args.length != 0) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        MyFoodora myFoodora = cli.getMyFoodora();
        List<Courier> couriers = new ArrayList<>();
        
        // Collect all couriers
        for (User user : myFoodora.getUsers()) {
            if (user instanceof Courier) {
                couriers.add((Courier) user);
            }
        }
        
        // Sort by number of deliveries (descending)
        Collections.sort(couriers, (c1, c2) -> 
            Integer.compare(c2.getDeliveredOrders(), c1.getDeliveredOrders()));
        
        System.out.println("Couriers sorted by number of deliveries:");
        System.out.println("---------------------------------------");
        for (Courier courier : couriers) {
            System.out.printf("%s: %d deliveries (Currently %s)%n",
                courier.getUsername(),
                courier.getDeliveredOrders(),
                courier.isOnDuty() ? "On duty" : "Off duty");
        }
    }

    @Override
    public String getDescription() {
        return "Show list of couriers sorted by number of deliveries (manager only)";
    }

    @Override
    public String getSyntax() {
        return "showCourierDeliveries";
    }
} 