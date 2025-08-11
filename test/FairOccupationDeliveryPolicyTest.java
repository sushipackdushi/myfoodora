package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FairOccupationDeliveryPolicyTest {
    private FairOccupationDeliveryPolicy policy;
    private MyFoodora myFoodora;
    private Order order;
    private Courier courier1;
    private Courier courier2;
    private Courier courier3;
    
    @Before
    public void setUp() {
        policy = new FairOccupationDeliveryPolicy();
        myFoodora = new MyFoodora(2.5, 0.1, 5.0);
        
        // Create test order
        Coordinate customerLocation = new Coordinate(5.0f, 5.0f);
        Customer customer = new Customer("John", "Doe", "CUST1", "johndoe", "password",
            customerLocation, "john@example.com", "1234567890");
            
        Coordinate restaurantLocation = new Coordinate(0.0f, 0.0f);
        Restaurant restaurant = new Restaurant("Test Restaurant", "Name", "REST1", "testrest", "password",
            restaurantLocation, null, null);
            
        order = new Order(customer, restaurant);
        
        // Create couriers with different delivery counts
        courier1 = new Courier("John", "Smith", "COUR1", "johnsmith", "password",
            new Coordinate(1.0f, 1.0f), "1234567890");
        courier2 = new Courier("Jane", "Doe", "COUR2", "janedoe", "password",
            new Coordinate(2.0f, 2.0f), "0987654321");
        courier3 = new Courier("Bob", "Wilson", "COUR3", "bobwilson", "password",
            new Coordinate(3.0f, 3.0f), "1122334455");
            
        // Add couriers to MyFoodora
        myFoodora.addUser(courier1);
        myFoodora.addUser(courier2);
        myFoodora.addUser(courier3);
    }
    
    @Test
    public void testAssignLeastOccupiedCourier() {
        // Set all couriers on duty with different delivery counts
        courier1.setOnDuty(true);
        courier2.setOnDuty(true);
        courier3.setOnDuty(true);
        
        // Simulate some completed deliveries
        for(int i = 0; i < 5; i++) courier1.incrementDeliveredOrders();
        for(int i = 0; i < 3; i++) courier2.incrementDeliveredOrders();
        for(int i = 0; i < 4; i++) courier3.incrementDeliveredOrders();
        
        policy.assignCourier(myFoodora, order);
        
        assertEquals(courier2, order.getCourier()); // Should assign courier with least deliveries
    }
    
    @Test
    public void testAssignWhenLeastOccupiedNotOnDuty() {
        // Set delivery counts
        for(int i = 0; i < 5; i++) courier1.incrementDeliveredOrders();
        for(int i = 0; i < 3; i++) courier2.incrementDeliveredOrders();
        for(int i = 0; i < 4; i++) courier3.incrementDeliveredOrders();
        
        courier2.setOnDuty(false); // Least occupied courier not available
        courier1.setOnDuty(true);
        courier3.setOnDuty(true);
        
        policy.assignCourier(myFoodora, order);
        
        assertEquals(courier3, order.getCourier()); // Should assign next least occupied
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
    public void testAssignWithEqualDeliveries() {
        // Set all couriers on duty with equal delivery counts
        courier1.setOnDuty(true);
        courier2.setOnDuty(true);
        courier3.setOnDuty(true);
        
        // Give all couriers same number of deliveries
        for(int i = 0; i < 3; i++) {
            courier1.incrementDeliveredOrders();
            courier2.incrementDeliveredOrders();
            courier3.incrementDeliveredOrders();
        }
        
        policy.assignCourier(myFoodora, order);
        
        // Should assign first courier in the list with minimum deliveries
        assertEquals(courier1, order.getCourier());
    }
} 