package com.hacked.hamsemare.assignment;

import java.io.IOException;


/*Citations-
Used and watched Abrams Hindle's Student picker tutorial on youtube as reference and a guide

DATE - January 15,2018
From Whom - Abram Hindle
URL- https://www.youtube.com/watch?v=k9ZNbsc0Qgo&t=7s
*/

/**
 * Created by Hamsemare on 2018-01-15.
 */


/**
 * Purpose of the Sub Controller Method is similar to Sub Manager Class, it tells the Subscription
 * list what actions to do and how to do it. The methods in the Sub Controller method are get()
 * which returns the list of subscriptions, saveSubs() which save the data (the list of
 * subscriptions), and addSub() which adds the subscription to the list of subscriptions.
 *
 * @author Hamse Mare
 * @see SubscriptionList
 * @see SubManager
 * @see Subscription
 */

public class SubController {

    //Private attribute
    private static SubscriptionList subs = null;


    /**
     * Retrieves the list of Subscriptions to be viewed
     *
     * @return the list of subscriptions
     */
    static public SubscriptionList get() {

        if (subs == null) {
            try {
                subs = SubManager.get().load();
                subs.addListener(new Listener() {
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

    /**
     * Saves data
     */
    private static void saveSubs() {
        try {
            SubManager.get().save(get());
        }
        catch (IOException e) {
            throw new RuntimeException("Error");
        }
    }

    /**
     * Adds the Subscription to the list of Subscriptions.
     *
     * @param name is the name of the subscription being added
     * @param date is the date of the subscription being added
     * @param charge is the charge of the subscription being added
     * @param comment is the charge of the subscription being added
     */
    public void addSub(String name, String date, Double charge, String comment) {
        Subscription sub= new Subscription(name, date, charge, comment);
        get().add(sub);
    }

}
