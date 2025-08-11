package fr.cs.groupS.myFoodora;
import java.io.Serializable ;

public class FoodItemFactory implements Serializable {

	private static final long serialVersionUID = 6574451391643311744L;

	public FoodItemFactory() {
	}
	
	/**
	 * create new dish of type dishType
	 * @param dishType : "starter", "mainDish" or "dessert"
	 * @param name : dish name
	 * @param price : dish price 
	 * @param type : "standard", "vegetarian" or "glutenFree"
	 * @return dish : dish
	 */
	public Dish createDish (String dishType, String name, double price, String type) {
		Dish dish = null;
		switch(dishType){
			case("starter"):
				dish = new Starter(name, price, type);
				break;
			case("mainDish"):
				dish = new MainDish(name, price, type);
				break;
			case("dessert"):
				dish = new Dessert(name, price, type);
				break;
		}
		return (dish);
	}
	
	/**
	 * create new meal : full-meal or half-meal
	 * @param mealType : "full" or "half"
	 * @param name : name of meal
	 * @return meal : meal
	 */
	public Meal createMeal (String mealType, String name) {
		Meal meal = null;
		switch(mealType){
			case("full"):
				meal = new FullMeal(name);
				break;
			case("half"):
				meal = new HalfMeal(name);
				break;
		}
		return (meal);
	}

}
