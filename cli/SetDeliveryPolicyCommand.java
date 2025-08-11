package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;

public class SetDeliveryPolicyCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Manager)) {
            throw new IllegalStateException("Only managers can set delivery policies");
        }
        
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }
        
        String policyName = args[0].toLowerCase();
        MyFoodora myFoodora = cli.getMyFoodora();
        DeliveryPolicy policy;
        
        switch (policyName) {
            case "fastest":
                policy = new FastestDeliveryPolicy();
                break;
            case "fair-occupation":
                policy = new FairOccupationDeliveryPolicy();
                break;
            default:
                throw new IllegalArgumentException("Unknown delivery policy: " + policyName + 
                    ". Available policies: fastest, fair-occupation");
        }
        
        myFoodora.setDeliveryPolicy(policy);
        System.out.println("Successfully set delivery policy to: " + policyName);
    }

    @Override
    public String getDescription() {
        return "Set the system's delivery policy (manager only)";
    }

    @Override
    public String getSyntax() {
        return "setDeliveryPolicy <policyName>";
    }
} 