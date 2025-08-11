package fr.cs.groupS.myFoodora;

import java.io.Serializable;

public class TargetProfitPolicyDeliveryCost implements TargetProfitPolicy, Serializable {

    private static final long serialVersionUID = -5256868421517703292L;

    @Override
    public double meetTargetProfit(MyFoodora myFoodora, double targetProfit) throws UnreachableTargetProfitException {
        int numberOfOrders = myFoodora.getCompletedOrders().size();

        if (numberOfOrders == 0) {
            throw new UnreachableTargetProfitException("This target profit can not be reached");
        }

        // Sum all completed‐order prices
        double totalIncome = 0.0;
        for (Order order : myFoodora.getCompletedOrders()) {
            totalIncome += order.getPrice();
        }

        double markupPercentage = myFoodora.getMarkupPercentage();
        if (markupPercentage < 0) {
            throw new UnreachableTargetProfitException("This target profit can not be reached");
        }

        double serviceFee = myFoodora.getServiceFee();

        // Formula: (targetProfit - totalIncome*markupPercentage)/numberOfOrders + serviceFee
        double deliveryCost = 
            (targetProfit - totalIncome * markupPercentage) / numberOfOrders
            + serviceFee;

        return deliveryCost;
    }
}
