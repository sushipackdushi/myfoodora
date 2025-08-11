package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class LoginCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String username = args[0];
        String password = args[1];
        
        User user = cli.getMyFoodora().login(username, password);
        
        if(user == null) {
        	System.out.println("Could not find user.");
        	return;
        }
        
        cli.setCurrentUser(user);
        System.out.println("Successfully logged in as " + user.getUsername());
    }

    @Override
    public String getDescription() {
        return "Log in to the system with username and password";
    }

    @Override
    public String getSyntax() {
        return "login <username> <password>";
    }
} 