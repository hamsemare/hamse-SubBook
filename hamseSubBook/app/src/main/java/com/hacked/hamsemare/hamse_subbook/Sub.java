package com.hacked.hamsemare.hamse_subbook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hamsemare on 2018-01-15.
 */


/*
Purpose of the Sub Class is to make a subscription which has a name, date, monthly charge, and a comment. We also want to be able to
access each attribute of the Subscription, We also want to be able to display the subscription, so we have a toString method to display it and
return type String
*/


public class Sub implements Serializable {
    private static final long serialVersionUID = -7556743916038416941L;

    // Set Attributes to private because of encapsulation
    private String name;
    private String date;
    private Double charge;
    private String comment;
    private int id;


    //Constructor
    //Needs the following attributes when Sub is initialized
    public Sub(String name, String date, Double charge, String comment){
        this.name = name;
        this.date = date;
        this.charge= charge;
        this.comment = comment;
        int random = (int )(Math.random() * 500000000 + 1);
        this.id = random;
    }

    //Convert to string in order to print, correct format to print out to main
    public String toString(){
        String name= this.name;
        String date= this.date;
        Double scharge= this.charge;
        return ("NAME: "+ name + ",   DATE: " + date + ",   MONTHLY CHARGE: $" + String.format("%.2f", scharge));
    }


    //Compares the entries, used when equals is called and the argument is type Sub
    public boolean equals(Sub CompareSub){
        if (CompareSub != null){
            return false;
        }
        else {
            return get()==CompareSub.get();
        }
    }
    // Object method
    public boolean equals(Object Sub){
        if ((Sub!= null) && (Sub.getClass() == this.getClass())){
            //
            return (this.equals((Sub)Sub));
        }
        else {
            return false;
        }
    }


    //Getters
    public String getname() {
        return this.name;
    }
    public String getdate() {
        return this.date;
    }
    public String getcharge() {
        return this.charge.toString();
    }
    public Double getc(){
        return this.charge;
    }
    public String getcomment() {
        return this.comment;
    }
    // Returns the id of the entry
    public int get(){
        return this.id;
    }
    //Hash Code Method
    public int hashCode(){
        return ("Log ID: "+getname()).hashCode();
    }


}
