package fr.cs.groupS.myFoodora.cli;

public interface CLICommand {
    void execute(MyFoodoraCLI cli, String[] args) throws Exception;
    String getDescription();
    String getSyntax();
} 