package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MealVisitorTest {
    private MealVisitor visitor;
    private FullMeal fullMeal;
    private HalfMeal halfMeal;
    private MainDish mainDish;
    private Starter starter;
    private Dessert dessert;

    @Before
    public void setUp() {
        visitor  = new MealVisitor();

        mainDish = new MainDish("Steak", 25.0, "standard");
        starter  = new Starter("Salad", 8.0, "vegetarian");
        dessert  = new Dessert("Ice Cream", 6.0, "standard");

        fullMeal = new FullMeal("Full Meal");
        halfMeal = new HalfMeal("Half Meal");
    }

    @Test
    public void testCalcPriceFullMealEmpty() {
        double price = visitor.calcPriceMeal(fullMeal);
        assertEquals(0.0, price, 0.001);
    }

    @Test
    public void testCalcPriceFullMealComplete() {
        try {
            fullMeal.addDish(mainDish);
            fullMeal.addDish(starter);
            fullMeal.addDish(dessert);

            double expectedPrice = mainDish.getPrice() + starter.getPrice() + dessert.getPrice();
            double actualPrice   = visitor.calcPriceMeal(fullMeal);
            assertEquals(expectedPrice, actualPrice, 0.001);

            // Test with discount using Menu
            Menu menu = new Menu();
            menu.addMeal(fullMeal);
            menu.setGenericDiscountRate(0.1); // 10% discount
            fullMeal.update(menu);

            double discountedPrice = expectedPrice * 0.9;
            actualPrice = visitor.calcPriceMeal(fullMeal);
            assertEquals(discountedPrice, actualPrice, 0.001);
        } catch (NoPlaceInMealException e) {
            fail("Should be able to add dishes to empty meal");
        }
    }

    @Test
    public void testCalcPriceHalfMealEmpty() {
        double price = visitor.calcPriceMeal(halfMeal);
        assertEquals(0.0, price, 0.001);
    }

    @Test
    public void testCalcPriceHalfMealComplete() {
        try {
            halfMeal.addDish(mainDish);
            halfMeal.addDish(starter);

            double expectedPrice = mainDish.getPrice() + starter.getPrice();
            double actualPrice   = visitor.calcPriceMeal(halfMeal);
            assertEquals(expectedPrice, actualPrice, 0.001);

            // Test with discount using Menu
            Menu menu = new Menu();
            menu.addMeal(halfMeal);
            menu.setGenericDiscountRate(0.1);
            halfMeal.update(menu);

            double discountedPrice = expectedPrice * 0.9;
            actualPrice = visitor.calcPriceMeal(halfMeal);
            assertEquals(discountedPrice, actualPrice, 0.001);
        } catch (NoPlaceInMealException e) {
            fail("Should be able to add dishes to empty meal");
        }
    }

    @Test
    public void testAddDishToFullMeal() {
        try {
            // Add starter
            visitor.addDishToFullMeal(starter, fullMeal);
            assertEquals(starter, fullMeal.getStarter());

            // Add main dish
            visitor.addDishToFullMeal(mainDish, fullMeal);
            assertEquals(mainDish, fullMeal.getMainDish());

            // Add dessert
            visitor.addDishToFullMeal(dessert, fullMeal);
            assertEquals(dessert, fullMeal.getDessert());

        } catch (NoPlaceInMealException e) {
            fail("Should be able to add dishes to empty meal");
        }

        // Try to add duplicate dish types
        try {
            Starter extraStarter = new Starter("Extra", 5.0, "standard");
            visitor.addDishToFullMeal(extraStarter, fullMeal);
            fail("Should not be able to add second starter");
        } catch (NoPlaceInMealException e) {
            // Expected
        }
    }

    @Test
    public void testAddDishToHalfMeal() {
        try {
            // Add main dish
            visitor.addDishToFullMeal(mainDish, halfMeal);
            assertEquals(mainDish, halfMeal.getMainDish());

            // Add side dish (starter)
            visitor.addDishToFullMeal(starter, halfMeal);
            assertEquals(starter, halfMeal.getSideDish());

        } catch (NoPlaceInMealException e) {
            fail("Should be able to add dishes to empty meal");
        }

        // Try to add duplicate dish types
        try {
            MainDish extraMain = new MainDish("Extra", 10.0, "standard");
            visitor.addDishToFullMeal(extraMain, halfMeal);
            fail("Should not be able to add second main dish");
        } catch (NoPlaceInMealException e) {
            // Expected
        }
    }

    @Test
    public void testMealTypeHandling() {
        try {
            // Set all dishes to same type
            mainDish.setType("vegetarian");
            starter.setType("vegetarian");
            dessert.setType("vegetarian");

            // Add to full meal
            visitor.addDishToFullMeal(mainDish, fullMeal);
            visitor.addDishToFullMeal(starter, fullMeal);
            visitor.addDishToFullMeal(dessert, fullMeal);

            assertEquals("vegetarian", fullMeal.getType());

            // Test half meal
            HalfMeal vegHalfMeal = new HalfMeal("Veg Half");
            MainDish vegMain = new MainDish("Veg Main", 10.0, "standard");
            Starter vegSide  = new Starter("Veg Side", 5.0, "standard");
            vegMain.setType("vegetarian");
            vegSide.setType("vegetarian");

            visitor.addDishToFullMeal(vegMain, vegHalfMeal);
            visitor.addDishToFullMeal(vegSide, vegHalfMeal);

            assertEquals("vegetarian", vegHalfMeal.getType());

        } catch (NoPlaceInMealException e) {
            fail("Should be able to add dishes to empty meal");
        }
    }
}
