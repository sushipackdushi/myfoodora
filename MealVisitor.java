package fr.cs.groupS.myFoodora;

import java.io.Serializable;

public class MealVisitor implements Serializable {

    private static final long serialVersionUID = -4337754995360580468L;

    /** calc price of full meal */
    public double calcPriceMeal(FullMeal fullMeal) {
        Starter   starter   = fullMeal.getStarter();
        MainDish  mainDish  = fullMeal.getMainDish();
        Dessert   dessert   = fullMeal.getDessert();
        double    discount  = fullMeal.getDiscountRate();

        double starterPrice  = (starter == null)  ? 0 : starter.getPrice();
        double mainDishPrice = (mainDish == null) ? 0 : mainDish.getPrice();
        double dessertPrice  = (dessert == null)  ? 0 : dessert.getPrice();

        double subtotal = starterPrice + mainDishPrice + dessertPrice;
        return subtotal * (1 - discount);
    }

    /** calc price of half meal */
    public double calcPriceMeal(HalfMeal halfMeal) {
        Dish     sideDish  = halfMeal.getSideDish();
        MainDish mainDish  = halfMeal.getMainDish();
        double   discount  = halfMeal.getDiscountRate();

        double sidePrice  = (sideDish == null) ? 0 : sideDish.getPrice();
        double mainPrice  = (mainDish == null) ? 0 : mainDish.getPrice();

        double subtotal = sidePrice + mainPrice;
        return subtotal * (1 - discount);
    }

    /** add dish to full meal */
    public void addDishToFullMeal(Dish dish, FullMeal fullMeal) throws NoPlaceInMealException {
        String dishType = dish.getDishType();

        switch (dishType) {
            case "Starter":
                if (fullMeal.getStarter() == null) {
                    fullMeal.setStarter((Starter) dish);
                } else {
                    throw new NoPlaceInMealException("The meal already contains a starter");
                }
                break;

            case "MainDish":
                if (fullMeal.getMainDish() == null) {
                    fullMeal.setMainDish((MainDish) dish);
                } else {
                    throw new NoPlaceInMealException("The meal already contains a main dish");
                }
                break;

            case "Dessert":
                if (fullMeal.getDessert() == null) {
                    fullMeal.setDessert((Dessert) dish);
                } else {
                    throw new NoPlaceInMealException("The meal already contains a dessert");
                }
                break;

            default:
                return;
        }

        fullMeal.setPrice(fullMeal.calcPrice());

        Starter  newStarter = fullMeal.getStarter();
        MainDish newMain    = fullMeal.getMainDish();
        Dessert  newDessert = fullMeal.getDessert();

        if (newStarter != null && newMain != null && newDessert != null) {
            if (newMain.getType().equals(newStarter.getType())
                    && newMain.getType().equals(newDessert.getType())) {
                fullMeal.setType(newMain.getType());
            }
        }
    }

    /** add dish to half meal */
    public void addDishToFullMeal(Dish dish, HalfMeal halfMeal) throws NoPlaceInMealException {
        String dishType = dish.getDishType();

        switch (dishType) {
            case "Starter":
            case "Dessert":
                if (halfMeal.getSideDish() == null) {
                    halfMeal.setSideDish((Starter) dish);
                } else {
                    throw new NoPlaceInMealException("The meal already contains a sideDish");
                }
                break;

            case "MainDish":
                if (halfMeal.getMainDish() == null) {
                    halfMeal.setMainDish((MainDish) dish);
                } else {
                    throw new NoPlaceInMealException("The meal already contains a mainDish");
                }
                break;

            default:
                return;
        }

        halfMeal.setPrice(halfMeal.calcPrice());

        Dish     newSide = halfMeal.getSideDish();
        MainDish newMain = halfMeal.getMainDish();

        if (newSide != null && newMain != null && newMain.getType().equals(newSide.getType())) {
            halfMeal.setType(newMain.getType());
        }
    }
}
