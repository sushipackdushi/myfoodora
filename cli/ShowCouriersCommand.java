package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;
import java.util.*;

/**
 * ShowCouriersCommand: lists every courier in the system,
 * printing all of its fields (inherited from User and specific to Courier).
 * Can be invoked by any user (no role restriction).
 */
public class ShowCouriersCommand implements CLICommand {

    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        // 1) Syntax check: no extra arguments allowed
        if (args.length != 0) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }

        // 2) Get reference to the core MyFoodora object
        MyFoodora myFoodora = cli.getMyFoodora();

        // 3) Collect all Courier instances
        List<Courier> couriers = new ArrayList<>();
        for (User user : myFoodora.getUsers()) {
            if (user instanceof Courier) {
                couriers.add((Courier) user);
            }
        }

        // 4) Sort them by username (simple lex order)
        Collections.sort(
            couriers,
            (c1, c2) -> c1.getUsername().compareTo(c2.getUsername())
        );

        // 5) Print header
        System.out.println("Courier List:");
        System.out.println("-------------");

        // 6) If no couriers, show a message and return
        if (couriers.isEmpty()) {
            System.out.println("No couriers registered in the system.");
            return;
        }

        // 7) For each courier, print every field
        for (Courier c : couriers) {
            // Inherited from User:
            System.out.printf("Username: %s%n",   c.getUsername());
            System.out.printf("User ID: %s%n",    c.getUserID());
            System.out.printf("Name: %s %s%n",    c.getName(), c.getSurname());
            // (We do NOT print c.getPassword() for security.)

            // Courier-specific fields:
            Coordinate pos = c.getPosition();
            // Simplest formatting: show raw X and Y
            System.out.printf("Position: (%.2f, %.2f)%n",
                              pos.getX(), pos.getY());

            System.out.printf("Phone Number: %s%n",   c.getPhoneNumber());
            System.out.printf("Delivered Orders: %d%n", c.getDeliveredOrders());
            System.out.printf("On Duty: %b%n",        c.isOnDuty());

            System.out.println(); // blank line between couriers
        }
    }

    @Override
    public String getDescription() {
        return "Show list of all registered couriers (any user)";
    }

    @Override
    public String getSyntax() {
        return "showCouriers";
    }
}
