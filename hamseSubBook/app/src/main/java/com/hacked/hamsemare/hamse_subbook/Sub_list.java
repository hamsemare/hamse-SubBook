package com.hacked.hamsemare.hamse_subbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Hamsemare on 2018-01-15.
 */


/*Citations-
Used Abrams Hindle's Student picker tutorial on youtube as reference and a guide
https://github.com/abramhindle/student-picker
https://www.youtube.com/watch?v=k9ZNbsc0Qgo&t=7s
*/


/*
Purpose of the Sub list is to handle adding, removing and updating the list of subscriptions using listeners. Our class implements the interface
Serializable so we can store data. Methods created for the Sub_list class include add (Which adds a subscription to the list of subscriptions),
remove (Which removes the subscription from the list of subscription), we have a getter to retrieve the list of subscriptions. We also have
listener methods which include adding a listener, removing a listener, updating the listeners.
*/


public class Sub_list implements Serializable {
    private static final long serialVersionUID = 6673446047991058932L;

    //Allows the following attribute to be accessed outside the class, sub_list and listeners_list
    protected ArrayList<Sub> sub_list= null;
    //We do not want the listeners list to be Serialized
    protected transient ArrayList<Listener> listeners_list = null;


    // Constructor for the Sub list
    public Sub_list(){
        sub_list = new ArrayList<Sub>();
        listeners_list = new ArrayList<Listener>();
    }

    //Add to the list of Subscriptions, used when adding a subscription
    public void add(Sub sub) {
        sub_list.add(sub);
        notify_listeners();
    }

    // Remove from the list of Subscriptions, used when deleting a subscription
    public void remove(int num){
        sub_list.remove(num);
        notify_listeners();
    }
    //Getter
    // Returns the whole Subscription list
    public Collection<Sub> get(){
        return sub_list;
    }



    //*************Listeners methods***************

    //Get listener from list
    private ArrayList<Listener> get_listeners() {
        if (listeners_list == null ) {
            listeners_list = new ArrayList<Listener>();
        }
        return listeners_list;
    }
    // Add Listener
    public void add_listener(Listener listener) {
        get_listeners().add(listener);
    }

    //Remove listener
    public void remove_listener(Listener listener) {
        get_listeners().remove(listener);
    }

    //Notify listener
    private void notify_listeners() {
        for (Listener  listeners : listeners_list) {
            listeners.update();
        }
    }
}
