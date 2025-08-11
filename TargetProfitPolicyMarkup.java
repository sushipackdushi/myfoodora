package fr.cs.groupS.myFoodora;

import java.io.Serializable ;

public class TargetProfitPolicyMarkup implements TargetProfitPolicy, Serializable {
	
	private static final long serialVersionUID = -37289269800490538L;

	/**
	 * Sets the markup percentage
	 * based on last month income
	 * to meet a target profit given the formula : profitForOneOrder = orderPrice * markupPercentage + serviceFee - deliveryCost 
	 * @param myFoodora : MyFoodora system
	 * @param targetProfit : the target profit for 1 month to meet
	 */
	
	@Override
	public double meetTargetProfit (MyFoodora myFoodora, double targetProfit) throws UnreachableTargetProfitException {
		int numberOfOrders = myFoodora.getCompletedOrders().size();
		double totalIncome = myFoodora.totalIncomeLastMonth();
		double serviceFee = myFoodora.getServiceFee();
		double deliveryCost = myFoodora.getDeliveryCost();
		
		double markupPercentage = (targetProfit - numberOfOrders*(serviceFee - deliveryCost))/totalIncome; 
		if (totalIncome==0){
			throw (new UnreachableTargetProfitException("This target profit can not be reached"));
		}
		if (markupPercentage >= 0){
			return(markupPercentage);
		}else{
			throw (new UnreachableTargetProfitException("This target profit can not be reached"));
		}
	}
	
}