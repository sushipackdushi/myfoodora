package fr.cs.groupS.myFoodora;

public abstract class Dish extends FoodItem {
	
	private static final long serialVersionUID = 3609381464846191570L;
	protected String dishType;
	private String name;
	private String type; 
	
	/**
	 * creates a dish object of a given price and type
	 * @param name : name of dish
	 * @param price : price of dish
	 * @param type : type "standard", "vegetarian" or "glutenFree"
	 */
	public Dish(String name, double price, String type) {
		this.name = name;
		this.price = price;
		this.type = type;
	}

	
	public String getDishType() { return dishType; }

	public String getName() { return name; }

	public String getType() { return type; }

	public void setType(String type) { this.type = type; }
	
	public void setName(String name) { this.name = name; }
	
	public void setDishType(String dishType) { this.dishType = dishType; }
	

	@Override
	public String toString() {
		String result = "none";
		if (this != null){
			result = "[name=" + name + ", price=" + price + ", type=" + type + ", counter=" + counter + "]\n";
		}
		return result;
	}
	
	@Override
	public boolean equals (Object o){
		boolean isequal = false;
		if (o instanceof Dish){
			Dish dish = (Dish)o;
			isequal = this.name.equals(dish.getName());
		}
		return isequal;
	}
}
