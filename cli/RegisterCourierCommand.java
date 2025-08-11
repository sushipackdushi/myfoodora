package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class RegisterCourierCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (args.length != 8) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String name = args[0];
        String surname = args[1];
        String userID = args[2];
        String username = args[3];
        String password = args[4];
        double x = Double.parseDouble(args[5]);
        double y = Double.parseDouble(args[6]);
        String phone = args[7];
        
        Courier courier = new Courier(name, surname, userID, username, password, 
                                    new Coordinate(x, y), phone);
        cli.getMyFoodora().addUser(courier);
        System.out.println("Successfully registered courier: " + name + " " + surname);
    }

    @Override
    public String getDescription() {
        return "Register a new courier";
    }

    @Override
    public String getSyntax() {
        return "registerCourier <name> <surname> <userID> <username> <password> <x> <y> <phone>";
    }
} 