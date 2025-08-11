package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CourierTest {
    private Courier courier;
    private Coordinate initialPosition;
    
    @Before
    public void setUp() {
        initialPosition = new Coordinate(1.0f, 2.0f);
        courier = new Courier("John", "Doe", "COUR001", "johndoe", "password",
            initialPosition, "1234567890");
    }
    
    @Test
    public void testCourierInitialization() {
        assertEquals("John", courier.getName());
        assertEquals("Doe", courier.getSurname());
        assertEquals("COUR001", courier.getUserID());
        assertEquals("johndoe", courier.getUsername());
        assertEquals("password", courier.getPassword());
        assertEquals(initialPosition, courier.getPosition());
        assertEquals("1234567890", courier.getPhoneNumber());
        assertEquals("Courier", courier.getUserType());
        assertEquals(0, courier.getDeliveredOrders());
        assertFalse(courier.isOnDuty());
    }
    
    @Test
    public void testDutyStatus() {
        assertFalse(courier.isOnDuty());
        
        courier.setOnDuty(true);
        assertTrue(courier.isOnDuty());
        
        courier.setOnDuty(false);
        assertFalse(courier.isOnDuty());
    }
    
    @Test
    public void testUpdatePosition() {
        Coordinate newPosition = new Coordinate(3.0f, 4.0f);
        courier.updatePosition(newPosition);
        assertEquals(newPosition, courier.getPosition());
        assertNotEquals(initialPosition, courier.getPosition());
    }
    
    @Test
    public void testDeliveredOrders() {
        assertEquals(0, courier.getDeliveredOrders());
        assertEquals(0, courier.getCounter()); // Counter is alias for deliveredOrders
        
        courier.incrementDeliveredOrders();
        assertEquals(1, courier.getDeliveredOrders());
        assertEquals(1, courier.getCounter());
        
        courier.incrementDeliveredOrders();
        assertEquals(2, courier.getDeliveredOrders());
        assertEquals(2, courier.getCounter());
    }
    
    @Test
    public void testInheritance() {
        assertTrue(courier instanceof User);
    }
} 