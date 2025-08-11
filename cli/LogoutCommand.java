package fr.cs.groupS.myFoodora.cli;

public class LogoutCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (cli.getCurrentUser() == null) {
            throw new IllegalStateException("No user is currently logged in");
        }
        
        System.out.println("Logged out user: " + cli.getCurrentUser().getUsername());
        cli.setCurrentUser(null);
    }

    @Override
    public String getDescription() {
        return "Log out the current user";
    }

    @Override
    public String getSyntax() {
        return "logout";
    }
} 