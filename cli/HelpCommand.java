package fr.cs.groupS.myFoodora.cli;

import java.util.*;

public class HelpCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) {
        System.out.println("Available commands:");
        System.out.println("------------------");

        // 1) Grab all syntaxes (the map’s keys)
        List<String> syntaxes = new ArrayList<>(cli.getCommands().keySet());

        // 2) Sort them alphabetically
        Collections.sort(syntaxes);

        // 3) Print each "syntax – description"
        for (String syntax : syntaxes) {
            CLICommand cmd = cli.getCommands().get(syntax);
            System.out.printf("%-30s - %s%n", 
                              cmd.getSyntax(), 
                              cmd.getDescription());
        }
    }

    @Override
    public String getDescription() {
        return "Display list of available commands";
    }

    @Override
    public String getSyntax() {
        return "help";
    }
}
