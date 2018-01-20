package com.hacked.hamsemare.hamse_subbook;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Hamsemare on 2018-01-15.
 */

/*Citations-
Used and watched Abrams Hindle's Student picker tutorial on youtube as reference and a guide
https://www.youtube.com/watch?v=k9ZNbsc0Qgo&t=7s
*/


/*
Purpose of the Sub Controller Method is similar to Sub Manager Class, it tells the Sub list what actions to do
and how to do it. The methods in the Sub Controller method are get() which returns the list of subscriptions,
Savesubs() which save the data (the list of subscriptions), addsub() which makes a instance of the Sub class(makes a subscription), and
add() which adds the subscription to the list of subscriptions.
*/


public class Sub_Controller {

    //Private attribute
    private static Sub_list subs = null;


    //Retrieves the list of Subscriptions to be viewed
    static public Sub_list get() {

        if (subs == null) {
            try {
                subs = Sub_Manager.get().load();
                subs.add_listener(new Listener() {
                    @Override
                    //Updates the listener
                    public void update() {
                        saveSubs();
                    }
                });
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException("Error");
            }
            catch (IOException e) {
                throw new RuntimeException("Error");
            }
        }
        return subs;
    }


    //Saves data
    private static void saveSubs() {
        try {
            Sub_Manager.get().save(get());
        }
        catch (IOException e) {
            throw new RuntimeException("Error");
        }
    }


    //Method takes in the name, date, monthly chargeand comments and creates a subscription entry, and gives function to add to the list of subscriptions
    public void Addsub(String name, String date, Double charge, String comment) {
        Sub sub= new Sub(name, date, charge, comment);
        Add(sub);
    }

    //Adds the Subscription to the list of Subscriptions
    public void Add(Sub sub){
        get().add(sub);
    }
}
