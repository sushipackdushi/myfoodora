package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class OffDutyCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Courier)) {
            throw new IllegalStateException("Only couriers can change their duty status");
        }
        
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String username = args[0];
        Courier courier = (Courier) cli.getCurrentUser();
        
        if (!courier.getUsername().equals(username)) {
            throw new IllegalStateException("You can only change your own duty status");
        }
        
        courier.setOnDuty(false);
        System.out.println("Courier " + username + " is now off duty");
    }

    @Override
    public String getDescription() {
        return "Set a courier's status to off-duty (courier only)";
    }

    @Override
    public String getSyntax() {
        return "offDuty <username>";
    }
} 