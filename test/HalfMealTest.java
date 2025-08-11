package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class HalfMealTest {
    private HalfMeal halfMeal;
    private MainDish mainDish;
    private Dish sideDish;
    private Menu menu;
    
    @Before
    public void setUp() {
        mainDish = new MainDish("Grilled Steak", 15.0, "standard");
        sideDish = new Starter("Salad", 5.0, "vegetarian");
        halfMeal = new HalfMeal("Test Half Meal", mainDish, sideDish);
        menu = new Menu();
    }
    
    @Test
    public void testHalfMealInitialization() {
        assertEquals("Test Half Meal", halfMeal.getName());
        assertEquals(mainDish, halfMeal.getMainDish());
        assertEquals(sideDish, halfMeal.getSideDish());
        
        // Test empty constructor
        HalfMeal emptyMeal = new HalfMeal("Empty Meal");
        assertNotNull(emptyMeal);
        assertNull(emptyMeal.getMainDish());
        assertNull(emptyMeal.getSideDish());
    }
    
    @Test
    public void testGetDishes() {
        List<Dish> dishes = halfMeal.getDishes();
        assertEquals(2, dishes.size());
        assertTrue(dishes.contains(mainDish));
        assertTrue(dishes.contains(sideDish));
        
        // Test with empty meal
        HalfMeal emptyMeal = new HalfMeal("Empty Meal");
        assertTrue(emptyMeal.getDishes().isEmpty());
    }
    
    @Test
    public void testPriceCalculation() {
        double expectedBasePrice = mainDish.getPrice() + sideDish.getPrice();
        
        assertTrue(halfMeal.getPrice() > 0);
        assertTrue(halfMeal.getPrice() <= expectedBasePrice); // Price should not exceed base price
    }
    
    @Test
    public void testUpdate() {
        double initialPrice = halfMeal.getPrice();
        
        // Test generic discount
        menu.setGenericDiscountRate(0.1);
        menu.setMealOfTheWeek(null);
        halfMeal.update(menu);
        assertTrue(halfMeal.getPrice() < initialPrice);
        
        // Test special discount as meal of the week
        menu.setSpecialDiscountRate(0.2);
        menu.setMealOfTheWeek(halfMeal);
        halfMeal.update(menu);
        assertTrue(halfMeal.getPrice() < initialPrice);
    }
    
    @Test
    public void testAddDish() {
        HalfMeal emptyMeal = new HalfMeal("Empty Meal");
        
        try {
            emptyMeal.addDish(mainDish);
            assertEquals(mainDish, emptyMeal.getMainDish());
            
            emptyMeal.addDish(sideDish);
            assertEquals(sideDish, emptyMeal.getSideDish());
        } catch (NoPlaceInMealException e) {
            fail("Should be able to add dishes to empty meal");
        }
        
        try {
            Dish extraDish = new Starter("Extra", 5.0, "standard");
            emptyMeal.addDish(extraDish);
            fail("Should not be able to add third dish to half meal");
        } catch (NoPlaceInMealException e) {
            // Expected
        }
    }
    
    @Test
    public void testSetSideDish() {
        Dish newSideDish = new Starter("NewSide", 5.0, "standard");
        halfMeal.setSideDish(newSideDish);
        assertEquals(newSideDish, halfMeal.getSideDish());
    }
    
    @Test
    public void testMealType() {
        HalfMeal mixedMeal = new HalfMeal("Mixed Meal", mainDish, sideDish);
        assertNotEquals(mixedMeal.getMainDish().getType(), mixedMeal.getSideDish().getType());
    }
} 