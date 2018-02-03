package com.hacked.hamsemare.assignment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by Hamsemare on 2018-01-15.
 */


/**
 * Represents a array containing elements of type Subscription.
 * Purpose of the Subscription list is to handle adding, removing and updating the list of subscriptions
 * using listeners. Methods created include add (Which adds a subscription to the list of
 * subscriptions), remove (Which removes the subscription from the list of subscription),
 * and we have a getter to retrieve the list of subscriptions.
 *
 * @author Hamse Mare
 * @see Subscription
 * @see Listener
 */

public class SubscriptionList implements Serializable {
    private static final long serialVersionUID = 6673446047991058932L;

    //Allows the following attribute to be accessed outside the class, sub_list and listeners_list
    protected ArrayList<Subscription> sublist= null;
    //We do not want the listeners list to be Serialized
    protected transient ArrayList<Listener> listenerlist = null;


    /**
     * Constructor for the list of subscriptions.
     * Creates a new list of Subscriptions array
     */
    public SubscriptionList(){
        sublist = new ArrayList<Subscription>();
        listenerlist = new ArrayList<Listener>();
    }

    /**
     * Add a subscription to the list of Subscriptions.
     *
     * @param sub the subscription object itself
     */
    public void add(Subscription sub) {
        sublist.add(sub);
        notifyListeners();
    }


    /**
     * Remove from the list of Subscriptions, used when deleting a subscription.
     *
     * @param num is the index of the subscription being deleted in the list of Subscriptions
     */
    public void remove(int num){
        sublist.remove(num);
        notifyListeners();
    }


    /**
     * Getter.
     *
     * @return the whole Subscription list
     */
    public Collection<Subscription> get(){
        return sublist;
    }



    //*************Listeners methods***************

    /**
     * Get listener from list
     * @return the listener arraylist
     */
    private ArrayList<Listener> getListeners() {
        if (listenerlist == null ) {
            listenerlist = new ArrayList<Listener>();
        }
        return listenerlist;
    }

    /**
     * Add Listener
     * @param listener is the listener being added to the arraylist listeners
     */
    public void addListener(Listener listener) {
        getListeners().add(listener);
    }

    /**
     * Notify listeners
     */
    private void notifyListeners() {
        for (Listener  listeners : listenerlist) {
            listeners.update();
        }
    }
}
