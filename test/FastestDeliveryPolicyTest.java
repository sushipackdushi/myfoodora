package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FastestDeliveryPolicyTest {
    private FastestDeliveryPolicy policy;
    private MyFoodora myFoodora;
    private Order order;
    private Courier courier1;
    private Courier courier2;
    private Courier courier3;
    
    @Before
    public void setUp() {
        policy = new FastestDeliveryPolicy();
        myFoodora = new MyFoodora(2.5, 0.1, 5.0);
        
        // Create test order with delivery location at (5,5)
        Coordinate customerLocation = new Coordinate(5.0f, 5.0f);
        Customer customer = new Customer("John", "Doe", "CUST1", "johndoe", "password",
            customerLocation, "john@example.com", "1234567890");
            
        Coordinate restaurantLocation = new Coordinate(0.0f, 0.0f);
        Restaurant restaurant = new Restaurant("Test Restaurant", "Name", "REST1", "testrest", "password",
            restaurantLocation, null, null);
            
        order = new Order(customer, restaurant);
        order.setAddressOfDelivery(customerLocation);
        
        // Create couriers at different distances from delivery point
        courier1 = new Courier("John", "Smith", "COUR1", "johnsmith", "password",
            new Coordinate(4.0f, 4.0f), "1234567890"); // Closest
        courier2 = new Courier("Jane", "Doe", "COUR2", "janedoe", "password",
            new Coordinate(7.0f, 7.0f), "0987654321"); // Medium distance
        courier3 = new Courier("Bob", "Wilson", "COUR3", "bobwilson", "password",
            new Coordinate(10.0f, 10.0f), "1122334455"); // Farthest
            
        // Add couriers to MyFoodora
        myFoodora.addUser(courier1);
        myFoodora.addUser(courier2);
        myFoodora.addUser(courier3);
    }
    
    @Test
    public void testAssignClosestCourier() {
        // Set all couriers on duty
        courier1.setOnDuty(true);
        courier2.setOnDuty(true);
        courier3.setOnDuty(true);
        
        policy.assignCourier(myFoodora, order);
        
        assertEquals(courier1, order.getCourier()); // Should assign closest courier
    }
    
    @Test
    public void testAssignWhenClosestNotOnDuty() {
        courier1.setOnDuty(false); // Closest courier not available
        courier2.setOnDuty(true);
        courier3.setOnDuty(true);
        
        policy.assignCourier(myFoodora, order);
        
        assertEquals(courier2, order.getCourier()); // Should assign second closest
    }
    
    @Test
    public void testAssignWhenNoCouriersOnDuty() {
        courier1.setOnDuty(false);
        courier2.setOnDuty(false);
        courier3.setOnDuty(false);
        
        policy.assignCourier(myFoodora, order);
        
        assertNull(order.getCourier()); // Should not assign any courier
    }
    
    @Test
    public void testAssignWithOnlyOneCourierOnDuty() {
        courier1.setOnDuty(false);
        courier2.setOnDuty(true);
        courier3.setOnDuty(false);
        
        policy.assignCourier(myFoodora, order);
        
        assertEquals(courier2, order.getCourier()); // Should assign only available courier
    }
    
    @Test
    public void testDistanceCalculation() {
        // Test with courier at exact same location as delivery
        Courier exactLocationCourier = new Courier("Exact", "Location", "COUR4", "exact", "password",
            new Coordinate(5.0f, 5.0f), "9999999999");
        exactLocationCourier.setOnDuty(true);
        myFoodora.addUser(exactLocationCourier);
        
        policy.assignCourier(myFoodora, order);
        
        assertEquals(exactLocationCourier, order.getCourier()); // Should assign courier at exact location
    }
} 