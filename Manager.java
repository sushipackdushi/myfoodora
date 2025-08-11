package fr.cs.groupS.myFoodora;

public class Manager extends User {

    public Manager(String name, String surname, String userID, String username, String password) {
        super(name, surname, userID, username, password);
    }

    @Override
    public String getUserType() {
        return "Manager";
    }

}