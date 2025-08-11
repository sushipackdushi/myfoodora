package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class MenuTest {
    private Menu menu;
    private Starter starter;
    private MainDish mainDish;
    private Dessert dessert;
    private Meal meal;
    
    @Before
    public void setUp() {
        menu = new Menu();
        starter = new Starter("Salad", 5.0, "standard");
        mainDish = new MainDish("Steak", 15.0, "standard");
        dessert = new Dessert("Ice Cream", 5.0, "standard");
      
        meal = new FullMeal("Full Meal", mainDish, starter, dessert);
    }
    
    @Test
    public void testAddAndRemoveDish() {
        menu.addDish(starter);
        assertTrue(menu.getDishes().contains(starter));
        assertEquals(1, menu.getDishes().size());
        
        menu.removeDish(starter);
        assertFalse(menu.getDishes().contains(starter));
        assertEquals(0, menu.getDishes().size());
    }
    
    @Test
    public void testGetDishCategories() {
        menu.addDish(starter);
        menu.addDish(mainDish);
        menu.addDish(dessert);
        
        assertEquals(1, menu.getStarters().size());
        assertTrue(menu.getStarters().contains(starter));
        
        assertEquals(1, menu.getMainDishes().size());
        assertTrue(menu.getMainDishes().contains(mainDish));
        
        assertEquals(1, menu.getDesserts().size());
        assertTrue(menu.getDesserts().contains(dessert));
    }
    
    @Test
    public void testAddAndRemoveMeal() {
        menu.addMeal(meal);
        assertTrue(menu.getMeals().contains(meal));
        assertEquals(1, menu.getMeals().size());
        
        menu.removeMeal(meal);
        assertFalse(menu.getMeals().contains(meal));
        assertEquals(0, menu.getMeals().size());
    }
    
    @Test
    public void testDiscountRates() {
        double genericRate = 0.10;
        double specialRate = 0.15;
        
        menu.setGenericDiscountRate(genericRate);
        menu.setSpecialDiscountRate(specialRate);
        
        assertEquals(genericRate, menu.getGenericDiscountRate(), 0.001);
        assertEquals(specialRate, menu.getSpecialDiscountRate(), 0.001);
    }
    
    @Test
    public void testMealOfTheWeek() {
        assertNull(menu.getMealOfTheWeek());
        
        menu.setMealOfTheWeek(meal);
        assertEquals(meal, menu.getMealOfTheWeek());
        assertTrue(menu.isMealOfTheWeek(meal));
        
        menu.removeMealOfTheWeek();
        assertNull(menu.getMealOfTheWeek());
    }
    
    @Test
    public void testFindDish() {
        menu.addDish(starter);
        menu.addDish(mainDish);
        
        assertEquals(starter, menu.findDish("Salad"));
        assertEquals(mainDish, menu.findDish("Steak"));
        assertNull(menu.findDish("NonexistentDish"));
    }
    
    @Test
    public void testFindMeal() {
        menu.addMeal(meal);
        
        assertEquals(meal, menu.findMeal("Full Meal"));
        assertNull(menu.findMeal("NonexistentMeal"));
    }
    
    @Test
    public void testNotifyMeals() {
        menu.addMeal(meal);
        double initialPrice = meal.getPrice();
        
        // Change discount rate which should trigger notification
        menu.setGenericDiscountRate(0.20);
        
        // Price should be different after discount rate change
        assertNotEquals(initialPrice, meal.getPrice(), 0.001);
    }
} 