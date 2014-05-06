package com.morganjanes.entitlement.core;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(){

    }

    public User(String email, String firstName, String lastName){
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.toLowerCase().equals(user.email.toLowerCase());
    }

    @Override
    public int hashCode() {
        return email.toLowerCase().hashCode();
    }

    public boolean isValidPassword(String password){
        return password.equals(this.password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

