package com.chriserus;

public class Customer {
    String name, surname;
    boolean isVeg;

    public Customer(String n, String sn, boolean Veg){
        this.name = n;
        this.surname = sn;
        this.isVeg = Veg;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public boolean getIsVeg(){
        return isVeg;
    }
}
