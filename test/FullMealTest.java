package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class FullMealTest {
    private FullMeal fullMeal;
    private MainDish mainDish;
    private Starter starter;
    private Dessert dessert;
    private Menu menu;
    
    @Before
    public void setUp() {
    	// "standard", "vegetarian" or "glutenFree"
        mainDish = new MainDish("Grilled Steak", 15.0, "standard");
        starter = new Starter("Salad", 5.0, "vegetarian");
        dessert = new Dessert("Ice Cream", 5.0, "vegetarian");
        fullMeal = new FullMeal("Test Full Meal", mainDish, starter, dessert);
        menu = new Menu();
    }
    
    @Test
    public void testFullMealInitialization() {
        assertEquals("Test Full Meal", fullMeal.getName());
        assertEquals(mainDish, fullMeal.getMainDish());
        assertEquals(starter, fullMeal.getStarter());
        assertEquals(dessert, fullMeal.getDessert());
        
        // Test empty constructor
        FullMeal emptyMeal = new FullMeal("Empty Meal");
        assertNotNull(emptyMeal);
        assertNull(emptyMeal.getMainDish());
        assertNull(emptyMeal.getStarter());
        assertNull(emptyMeal.getDessert());
    }
    
    @Test
    public void testGetDishes() {
        List<Dish> dishes = fullMeal.getDishes();
        assertEquals(3, dishes.size());
        assertTrue(dishes.contains(mainDish));
        assertTrue(dishes.contains(starter));
        assertTrue(dishes.contains(dessert));
        
        // Test with empty meal
        FullMeal emptyMeal = new FullMeal("Empty Meal");
        assertTrue(emptyMeal.getDishes().isEmpty());
    }
    
    @Test
    public void testPriceCalculation() {
        double expectedBasePrice = mainDish.getPrice() + starter.getPrice() + dessert.getPrice();


        assertTrue(fullMeal.getPrice() > 0);
        assertTrue(fullMeal.getPrice() <= expectedBasePrice); // Price should not exceed base price
    }
    
    @Test
    public void testUpdate() {
        double initialPrice = fullMeal.getPrice();
        
        // Test generic discount
        menu.setGenericDiscountRate(0.1);
        menu.setMealOfTheWeek(null);
        fullMeal.update(menu);
        assertTrue(fullMeal.getPrice() < initialPrice);
        
        // Test special discount as meal of the week
        menu.setSpecialDiscountRate(0.2);
        menu.setMealOfTheWeek(fullMeal);
        fullMeal.update(menu);
        assertTrue(fullMeal.getPrice() < initialPrice);
    }
    
    @Test
    public void testAddDish() {
        FullMeal emptyMeal = new FullMeal("Empty Meal");
        
        try {
            emptyMeal.addDish(mainDish);
            assertEquals(mainDish, emptyMeal.getMainDish());
            
            emptyMeal.addDish(starter);
            assertEquals(starter, emptyMeal.getStarter());
            
            emptyMeal.addDish(dessert);
            assertEquals(dessert, emptyMeal.getDessert());
        } catch (NoPlaceInMealException e) {
            fail("Should be able to add dishes to empty meal");
        }
        
        // Try to add another dish when meal is full
        try {
            Dish extraDish = new Starter("Extra", 5.0, "standard");
            emptyMeal.addDish(extraDish);
            fail("Should not be able to add fourth dish to full meal");
        } catch (NoPlaceInMealException e) {
            // Expected
        }
    }
    
    @Test
    public void testSetters() {
        Starter newStarter = new Starter("New Starter", 6.0, "standard");
        Dessert newDessert = new Dessert("New Dessert", 6.0, "standard");
        
        fullMeal.setStarter(newStarter);
        fullMeal.setDessert(newDessert);
        
        assertEquals(newStarter, fullMeal.getStarter());
        assertEquals(newDessert, fullMeal.getDessert());
    }
    
    @Test
    public void testMealType() {
        // Test when all dishes are of same type
        MainDish vegMain = new MainDish("Veg Main", 10.0, "standard");
        Starter vegStarter = new Starter("Veg Starter", 5.0, "standard");
        Dessert vegDessert = new Dessert("Veg Dessert", 5.0, "standard");
        
        // Set all dishes to same type (e.g., vegetarian)
        vegMain.setType("vegetarian");
        vegStarter.setType("vegetarian");
        vegDessert.setType("vegetarian");
        
        FullMeal typedMeal = new FullMeal("Typed Meal", vegMain, vegStarter, vegDessert);
        assertEquals("vegetarian", typedMeal.getType());
        
        // Test when dishes are of different types
        FullMeal mixedMeal = new FullMeal("Mixed Meal", mainDish, starter, dessert);
        assertNull(mixedMeal.getType()); // Type should be null when dishes have different types
    }
} 