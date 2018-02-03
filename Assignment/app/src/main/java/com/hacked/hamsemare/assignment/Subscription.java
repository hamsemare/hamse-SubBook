package com.hacked.hamsemare.assignment;

import java.io.Serializable;


/**
 * Created by Hamsemare on 2018-01-15.
 */


/**
 * Represents a particular, unique subscription.
 * Purpose of the Subscription Class is to make a subscription which has a name, date, monthly charge, and
 * a comment. We also want to be able to access each attribute of the Subscription and want to be
 * able to display the subscription.
 *
 * @author Hamse Mare
 * @see Serializable
 */

public class Subscription implements Serializable{

    private static final long serialVersionUID = -7556743916038416941L;
    // Set Attributes to private because of encapsulation
    private String name;
    private String date;
    private Double charge;
    private String comment;
    private int id;


    /**
     * Constructor
     * Needs the following attributes when Subscription is initialized
     *
     * @param name is the subscriptions name type String
     * @param date is the date of the subscription
     * @param charge is the amount of the subscription, charge of the subscription
     * @param comment is the comment with that subscription
     */
    public Subscription(String name, String date, Double charge, String comment){
        this.name = name;
        this.date = date;
        this.charge= charge;
        this.comment = comment;
        int random = (int )(Math.random() * 500000000 + 1);
        this.id = random;
    }


    /**
     * Convert to string in order to print, correct format to print out to the main Activity's
     * list of Subscription.
     *
     * @return the Correct format to be be displayed to the user
     */
    public String toString(){
        String name= this.name;
        String date= this.date;
        Double scharge= this.charge;
        return ("NAME: "+ name + ",   DATE: " + date + ",   MONTHLY CHARGE: $" + String.format("%.2f", scharge));
    }


    /**
     * Compares the entries, used when equals is called and the argument is type Subscription.
     *
     * @param CompareSub is a particular subscription
     * @return a boolean
     */
    public boolean equals(Subscription CompareSub){
        if (CompareSub != null){
            return false;
        }
        else {
            return get()==CompareSub.get();
        }
    }


    /**
     * Object method.
     */
    public boolean equals(Object Sub){
        if ((Sub!= null) && (Sub.getClass() == this.getClass())){
            //
            return (this.equals((Subscription) Sub));
        }
        else {
            return false;
        }
    }


    /**
     * Get the name of the subscription.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }


    /**
     * Get the date of the subscription.
     *
     * @return the date
     */
    public String getDate() {
        return this.date;
    }


    /**
     * Get the charge of the subscription, type String.
     *
     * @return the charge
     */
    public String getCharge() {
        return this.charge.toString();
    }


    /**
     * Get the charge of the subscription, type Double.
     *
     * @return the charge
     */
    public Double getC(){
        return this.charge;
    }


    /**
     * Get the comment of the subscription.
     *
     * @return the comment
     */
    public String getComment() {
        return this.comment;
    }


    /**
     * Returns the id of the entry.
     *
     * @return the id of the subscription, unique
     */
    public int get(){
        return this.id;
    }



    /**
     * Hash Code Method.
     */
    public int hashCode(){
        return ("Log ID: "+getName()).hashCode();
    }

}
