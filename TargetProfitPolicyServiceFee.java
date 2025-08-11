package fr.cs.groupS.myFoodora;

import java.io.Serializable ;

public class TargetProfitPolicyServiceFee implements TargetProfitPolicy,Serializable {

	private static final long serialVersionUID = -326916755426029061L;

	/**
	 * Sets the service fee
	 * based on last month income
	 * to meet a target profit given the formula : profitForOneOrder = orderPrice * markupPercentage + serviceFee - deliveryCost 
	 * @param myFoodora : MyFoodora system
	 * @param targetProfit : the target profit to meet
	 */
	
	@Override
	public double meetTargetProfit (MyFoodora myFoodora, double targetProfit) throws UnreachableTargetProfitException {
		int numberOfOrders = myFoodora.getCompletedOrders().size();
		double totalIncome = myFoodora.totalIncomeLastMonth();
		double markupPercentage = myFoodora.getMarkupPercentage();
		double deliveryCost = myFoodora.getDeliveryCost();
		
		double serviceFee = (targetProfit - totalIncome*markupPercentage)/numberOfOrders + deliveryCost; 
		if (totalIncome==0){
			throw (new UnreachableTargetProfitException("This target profit can not be reached"));
		}
		if (markupPercentage >= 0){
			return(serviceFee);
		}else{
			throw (new UnreachableTargetProfitException("This target profit can not be reached"));
		}
	}
}