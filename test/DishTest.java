package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DishTest {
    private Dish dish;
    
    // implementation for testing
    private class TestDish extends Dish {
        /**
		 * 
		 */
		private static final long serialVersionUID = 753271087285809404L;

		public TestDish(String name, double price, String type) {
            super(name, price, type);
            this.dishType = "test";
        }
    }
    
    @Before
    public void setUp() {
        dish = new TestDish("Test Dish", 10.0, "standard");
    }
    
    @Test
    public void testDishInitialization() {
        assertEquals("Test Dish", dish.getName());
        assertEquals(10.0, dish.getPrice(), 0.001);
        assertEquals("standard", dish.getType());
        assertEquals("test", dish.getDishType());
        assertEquals(0, dish.getCounter());
    }
    
    @Test
    public void testSetters() {
        dish.setName("New Name");
        assertEquals("New Name", dish.getName());
        
        dish.setPrice(15.0);
        assertEquals(15.0, dish.getPrice(), 0.001);
        
        dish.setType("vegetarian");
        assertEquals("vegetarian", dish.getType());
        
        dish.setDishType("newType");
        assertEquals("newType", dish.getDishType());
    }
    
    @Test
    public void testCounter() {
        assertEquals(0, dish.getCounter());
        
        dish.increaseCounter();
        assertEquals(1, dish.getCounter());
        
        dish.increaseCounter();
        assertEquals(2, dish.getCounter());
    }
    
    @Test
    public void testToString() {
        String expected = "[name=Test Dish, price=10.0, type=standard, counter=0]\n";
        assertEquals(expected, dish.toString());
        
        dish.increaseCounter();
        expected = "[name=Test Dish, price=10.0, type=standard, counter=1]\n";
        assertEquals(expected, dish.toString());
    }
    
    @Test
    public void testEquals() {
        // Same name should be equal
        Dish sameDish = new TestDish("Test Dish", 15.0, "vegetarian");
        assertTrue(dish.equals(sameDish));
        
        Dish differentDish = new TestDish("Different Dish", 10.0, "standard");
        assertFalse(dish.equals(differentDish));
        assertFalse(dish.equals("not a dish"));
        assertFalse(dish.equals(null));
    }
    
    @Test
    public void testDifferentTypes() {
        Dish standardDish = new TestDish("Standard", 10.0, "standard");
        assertEquals("standard", standardDish.getType());
        
        Dish vegetarianDish = new TestDish("Vegetarian", 10.0, "vegetarian");
        assertEquals("vegetarian", vegetarianDish.getType());
        
        Dish glutenFreeDish = new TestDish("Gluten Free", 10.0, "glutenFree");
        assertEquals("glutenFree", glutenFreeDish.getType());
    }
} 