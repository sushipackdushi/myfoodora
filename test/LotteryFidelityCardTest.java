package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Field;
import java.util.Random;

public class LotteryFidelityCardTest {
    private LotteryFidelityCard card;
    private Order order;
    private Customer customer;
    private Restaurant restaurant;
    
    @Before
    public void setUp() {
        card = new LotteryFidelityCard();
        
        // Setup test order
        Coordinate customerLocation = new Coordinate(1.0f, 1.0f);
        customer = new Customer("John", "Doe", "CUST1", "johndoe", "password",
            customerLocation, "john@example.com", "1234567890");
            
        Coordinate restaurantLocation = new Coordinate(2.0f, 2.0f);
        restaurant = new Restaurant("Test Restaurant", "Name", "REST1", "testrest", "password",
            restaurantLocation, null, null);
            
        order = new Order(customer, restaurant);
        order.setPrice(100.0); // Set a test price
    }
    
    @Test
    public void testInitialization() {
        assertNotNull(card);
        assertEquals("Lottery", card.getCardType());
    }
    
    @Test
    public void testWinningCase() throws Exception {
        // Use reflection to inject a mock Random that always returns 0.005 (winning case)
        injectMockRandom(0.005);
        
        card.applyDiscount(order);
        assertEquals(0.0, order.getPrice(), 0.001); // Should be free
    }
    
    @Test
    public void testLosingCase() throws Exception {
        // Use reflection to inject a mock Random that always returns 0.02 (losing case)
        injectMockRandom(0.02);
        
        double initialPrice = order.getPrice();
        card.applyDiscount(order);
        assertEquals(initialPrice, order.getPrice(), 0.001); // Price should not change
    }
    
    @Test
    public void testMultipleOrders() throws Exception {
        // Test multiple orders with different random values
        
        // First order - losing case
        injectMockRandom(0.02);
        Order order1 = new Order(customer, restaurant);
        order1.setPrice(100.0);
        card.applyDiscount(order1);
        assertEquals(100.0, order1.getPrice(), 0.001);
        
        // Second order - winning case
        injectMockRandom(0.005);
        Order order2 = new Order(customer, restaurant);
        order2.setPrice(150.0);
        card.applyDiscount(order2);
        assertEquals(0.0, order2.getPrice(), 0.001);
        
        // Third order - losing case
        injectMockRandom(0.5);
        Order order3 = new Order(customer, restaurant);
        order3.setPrice(200.0);
        card.applyDiscount(order3);
        assertEquals(200.0, order3.getPrice(), 0.001);
    }
    
    // Helper method to inject a mock Random object that returns a specific value
    private void injectMockRandom(final double returnValue) throws Exception {
        Random mockRandom = new Random() {
            private static final long serialVersionUID = 1L;
            @Override
            public double nextDouble() {
                return returnValue;
            }
        };
        
        Field randomField = LotteryFidelityCard.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(card, mockRandom);
    }
} 