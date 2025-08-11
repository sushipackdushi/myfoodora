package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class ShowTotalProfitCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Manager)) {
            throw new IllegalStateException("Only managers can view total profit");
        }
        
        double totalIncome = cli.getMyFoodora().totalIncomeLastMonth();
        System.out.printf("Total profit from last month: %.2f€%n", totalIncome);
    }

    @Override
    public String getDescription() {
        return "Show total profit from last month (manager only)";
    }

    @Override
    public String getSyntax() {
        return "showTotalProfit";
    }
} 