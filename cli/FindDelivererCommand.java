package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class FindDelivererCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        
        Order dummyOrder = new Order(null, null);
        dummyOrder.setAddressOfDelivery(new Coordinate(x, y));
        
        cli.getMyFoodora().getDeliveryPolicy().assignCourier(cli.getMyFoodora(), dummyOrder);
        Courier courier = dummyOrder.getCourier();
        
        if (courier != null) {
            System.out.printf("Found courier: %s %s (%.2f km away)%n", 
                courier.getName(), 
                courier.getSurname(),
                calculateDistance(courier.getPosition(), new Coordinate(x, y)));
        } else {
            System.out.println("No available couriers found");
        }
    }
    
    private double calculateDistance(Coordinate c1, Coordinate c2) {
        double dx = c1.getX() - c2.getX();
        double dy = c1.getY() - c2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String getDescription() {
        return "Find the nearest available courier for a delivery location";
    }

    @Override
    public String getSyntax() {
        return "findDeliverer <x> <y>";
    }
} 