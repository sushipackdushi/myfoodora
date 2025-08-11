package fr.cs.groupS.myFoodora;

public abstract class User {
    protected String name;
    protected String surname;
    protected String userID;
    protected String username;
    protected String password;
    protected boolean isActive;

    public User(String name, String surname, String userID, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.isActive = true;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getUsername() {
        return username;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public abstract String getUserType(); 

    public String getPassword() {
        return password;
    }
}
