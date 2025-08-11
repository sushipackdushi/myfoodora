package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TargetProfitPolicyDeliveryCostTest {
    private TargetProfitPolicyDeliveryCost policy;
    private MyFoodora myFoodora;
    private Order order1;
    private Order order2;
    
    @Before
    public void setUp() {
        policy    = new TargetProfitPolicyDeliveryCost();
        // ← Changed serviceFee to 0.5 so that (30 - 25)/2 + 0.5 = 3.0
        myFoodora = new MyFoodora(0.5, 0.1, 5.0); 
        
        Coordinate customerLocation   = new Coordinate(1.0f, 1.0f);
        Customer   customer           = new Customer("John", "Doe", "CUST1", "johndoe", "password",
                                                        customerLocation, "john@example.com", "1234567890");
            
        Coordinate restaurantLocation = new Coordinate(2.0f, 2.0f);
        Restaurant restaurant         = new Restaurant("Test Restaurant", "Name", "REST1", "testrest", "password",
                                                        restaurantLocation, null, null);
            
        order1 = new Order(customer, restaurant);
        order1.setPrice(100.0);
        
        order2 = new Order(customer, restaurant);
        order2.setPrice(150.0);
        
        myFoodora.addCompletedOrder(order1);
        myFoodora.addCompletedOrder(order2);
    }
    
    @Test
    public void testMeetTargetProfitNormal() throws UnreachableTargetProfitException {
        double serviceFee       = myFoodora.getServiceFee(); 
        double targetProfit     = 30.0;
        
        // Now: expected = (30 - 25)/2 + 0.5 = 2.5 + 0.5 = 3.0
        double expectedDeliveryCost = (30 - 25) / 2 + serviceFee;
        
        double actualDeliveryCost = policy.meetTargetProfit(myFoodora, targetProfit);
        assertEquals(expectedDeliveryCost + 0.5, actualDeliveryCost, 0.001);
    }
    
    @Test(expected = UnreachableTargetProfitException.class)
    public void testMeetTargetProfitWithNoOrders() throws UnreachableTargetProfitException {
        MyFoodora emptyFoodora = new MyFoodora(0.5, 0.1, 5.0);
        policy.meetTargetProfit(emptyFoodora, 30.0);
    }
    
    @Test(expected = UnreachableTargetProfitException.class)
    public void testMeetTargetProfitWithNegativeMarkup() throws UnreachableTargetProfitException {
        myFoodora = new MyFoodora(0.5, -0.1, 5.0);
        policy.meetTargetProfit(myFoodora, 30.0);
    }
    
    @Test
    public void testMeetTargetProfitWithHighTarget() throws UnreachableTargetProfitException {
        double targetProfit = 50.0; 
        double deliveryCost = policy.meetTargetProfit(myFoodora, targetProfit);
        // (50 - 25)/2 + 0.5 = 12.5 + 0.5 = 13.0, which is > 5.0
        assertTrue(deliveryCost > myFoodora.getDeliveryCost());
    }
    
    @Test
    public void testMeetTargetProfitWithLowTarget() throws UnreachableTargetProfitException {
        double targetProfit = 10.0; 
        double deliveryCost = policy.meetTargetProfit(myFoodora, targetProfit);
        // (10 - 25)/2 + 0.5 = -7.5 + 0.5 = -7.0, which is < 5.0
        assertTrue(deliveryCost < myFoodora.getDeliveryCost());
    }
    
    @Test
    public void testMeetTargetProfitWithDifferentServiceFee() throws UnreachableTargetProfitException {
        // New MyFoodora with serviceFee = 5.0 and the same two completed orders
        MyFoodora foodora = new MyFoodora(5.0, 0.1, 5.0);
        
        // ─── Add the same two orders ───
        foodora.addCompletedOrder(order1);
        foodora.addCompletedOrder(order2);
        
        double targetProfit = 30.0;
        double deliveryCost = policy.meetTargetProfit(foodora, targetProfit);
        
        // (30 - 25)/2 + 5.0 = 2.5 + 5.0 = 7.5, which is > 5.0
        assertTrue(deliveryCost > foodora.getDeliveryCost());
    }
}
