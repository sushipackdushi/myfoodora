package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class RestaurantTest {
    private Restaurant restaurant;
    private Coordinate location;
    private Menu menu;
    private List<Meal> meals;
    
    @Before
    public void setUp() {
        location = new Coordinate(1.0f, 2.0f);
        menu = new Menu();
        meals = new ArrayList<>();
        
        restaurant = new Restaurant("Test Restaurant", "Name", "REST001", 
            "testrest", "password", location, menu, meals);
    }
    
    @Test
    public void testRestaurantInitialization() {
        assertEquals("Test Restaurant", restaurant.getName());
        assertEquals("Name", restaurant.getSurname());
        assertEquals("REST001", restaurant.getUserID());
        assertEquals("testrest", restaurant.getUsername());
        assertEquals("password", restaurant.getPassword());
        assertEquals(location, restaurant.getLocation());
        assertEquals(menu, restaurant.getMenu());
        assertEquals(meals, restaurant.getMeals());
        assertEquals("Restaurant", restaurant.getUserType());
    }
    
    @Test
    public void testDiscountRates() {
        assertEquals(0.05, restaurant.getGenericDiscount(), 0.001);
        assertEquals(0.10, restaurant.getSpecialDiscount(), 0.001);
        
        restaurant.setGenericDiscount(0.15);
        restaurant.setSpecialDiscount(0.20);
        
        assertEquals(0.15, restaurant.getGenericDiscount(), 0.001);
        assertEquals(0.20, restaurant.getSpecialDiscount(), 0.001);
    }
    
    @Test
    public void testLocation() {
        Coordinate newLocation = new Coordinate(3.0f, 4.0f);
        restaurant.setLocation(newLocation);
        assertEquals(newLocation, restaurant.getLocation());
    }
    
    @Test
    public void testMenuOperations() {
        Menu newMenu = new Menu();
        restaurant.setMenu(newMenu);
        assertEquals(newMenu, restaurant.getMenu());
    }
    

    @Test
    public void testCounter() {
        assertEquals(0, restaurant.getCounter());
        
        restaurant.increaseCounter();
        assertEquals(1, restaurant.getCounter());
        
        restaurant.increaseCounter();
        assertEquals(2, restaurant.getCounter());
    }
    
    @Test
    public void testInheritance() {
        assertTrue(restaurant instanceof User);
    }
} 