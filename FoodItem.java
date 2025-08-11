package fr.cs.groupS.myFoodora;

public abstract class FoodItem implements java.io.Serializable {

	private static final long serialVersionUID = -4013771930891098924L;
	protected double price = 0;
	protected int counter = 0 ;
	
	public int getCounter(){ return counter ; }

	public void setPrice(double price) { this.price = price; }

	public double getPrice() { return price; }
	
	public void increaseCounter() { this.counter++ ; }
}
