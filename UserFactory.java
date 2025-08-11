package fr.cs.groupS.myFoodora;

import java.io.Serializable;
import java.util.List;

public class UserFactory implements Serializable {
	private static final long serialVersionUID = 1L;

	public UserFactory() {
	}

	public User createUser(String type, String name, String surname, String userID, String username, String password, Object... additionalParams) {
		switch (type.toLowerCase()) {
			case "manager":
				return new Manager(name, surname, userID, username, password);
				
			case "customer":
				if (additionalParams.length >= 3) {
					String address = (String) additionalParams[0];
					String email = (String) additionalParams[1];
					String phoneNumber = (String) additionalParams[2];
					
					String[] addy = address.split(",");
					Coordinate coords = new Coordinate(Float.parseFloat(addy[0]), Float.parseFloat(addy[1]));
					
					return new Customer(name, surname, userID, username, password, coords, email, phoneNumber);
				}
				throw new IllegalArgumentException("Customer requires address, email, and phone number");
				
			case "courier":
				if (additionalParams.length >= 2) {
					Coordinate position = (Coordinate) additionalParams[0];
					String phoneNumber = (String) additionalParams[1];
					return new Courier(name, surname, userID, username, password, position, phoneNumber);
				}
				throw new IllegalArgumentException("Courier requires position and phone number");
				
			case "restaurant":
				if (additionalParams.length >= 3) {
					Coordinate location = (Coordinate) additionalParams[0];
					return new Restaurant(name, surname, userID, username, password, location, null, null);
				}
				throw new IllegalArgumentException("Restaurant requires location, menu, and meals list");
				
			default:
				throw new IllegalArgumentException("Invalid user type: " + type);
		}
	}
}
