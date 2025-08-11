package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class UserFactoryTest {

    private UserFactory userFactory;

    @Before
    public void setUp() {
        userFactory = new UserFactory();
    }

    @Test
    public void testCreateCourier() {
        Coordinate position = new Coordinate(1.0f, 2.0f);
        String phone = "1234567890";
        
        User courier = userFactory.createUser("courier", "John", "Doe", "COUR001", "johndoe", "password",
                position, phone);

        assertNotNull(courier);
        assertTrue(courier instanceof Courier);
        Courier cour = (Courier) courier;
        assertEquals("John", cour.getName());
        assertEquals("Doe", cour.getSurname());
        assertEquals("COUR001", cour.getUserID());
        assertEquals("johndoe", cour.getUsername());
        assertEquals("password", cour.getPassword());
        assertEquals(phone, cour.getPhoneNumber());
        assertEquals(position, cour.getPosition());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCourierMissingParams() {
        userFactory.createUser("courier", "John", "Doe", "COUR001", "johndoe", "password");
    }

    @Test
    public void testCreateRestaurant() {
        Coordinate location = new Coordinate(1.0f, 2.0f);

        User restaurant = userFactory.createUser("restaurant", "Restaurant", "Name", "REST001", "restname", "password",
                location, null, null);

        assertNotNull(restaurant);
        assertTrue(restaurant instanceof Restaurant);
        Restaurant rest = (Restaurant) restaurant;
        assertEquals("Restaurant", rest.getName());
        assertEquals("Name", rest.getSurname());
        assertEquals("REST001", rest.getUserID());
        assertEquals("restname", rest.getUsername());
        assertEquals("password", rest.getPassword());
        assertEquals(location, rest.getLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRestaurantMissingParams() {
        userFactory.createUser("restaurant", "Restaurant", "Name", "REST001", "restname", "password");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidUserType() {
        userFactory.createUser("invalid", "John", "Doe", "ID001", "johndoe", "password");
    }
}
