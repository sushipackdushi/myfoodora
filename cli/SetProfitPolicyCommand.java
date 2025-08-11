package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class SetProfitPolicyCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Manager)) {
            throw new IllegalStateException("Only managers can set profit policies");
        }
        
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String policyName = args[0].toLowerCase();
        MyFoodora myFoodora = cli.getMyFoodora();
        TargetProfitPolicy policy;
        
        switch (policyName) {
            case "targetprofit_deliverycost":
                policy = new TargetProfitPolicyDeliveryCost();
                break;
            case "targetprofit_servicefee":
                policy = new TargetProfitPolicyServiceFee();
                break;
            case "targetprofit_markup":
                policy = new TargetProfitPolicyMarkup()	;
                break;
            default:
                throw new IllegalArgumentException("Unknown profit policy: " + policyName + 
                    ". Available policies: targetProfit_DeliveryCost, targetProfit_ServiceFee, targetProfit_Markup");
        }
        
        myFoodora.setProfitPolicy(policy);
        System.out.println("Successfully set profit policy to: " + policyName);
    }

    @Override
    public String getDescription() {
        return "Set the system's target profit policy (manager only)";
    }

    @Override
    public String getSyntax() {
        return "setProfitPolicy <policyName>";
    }
} 