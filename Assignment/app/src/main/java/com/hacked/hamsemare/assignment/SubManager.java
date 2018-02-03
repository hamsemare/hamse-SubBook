package com.hacked.hamsemare.assignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/*Citations-
Used and watched Abrams Hindle's Student picker tutorial on youtube as reference and a guide
https://www.youtube.com/watch?v=k9ZNbsc0Qgo&t=7s
DATE - January 15,2018
*/

/**
 * Created by Hamsemare on 2018-01-15.
 */

/**
 * Purpose of the Sub Manager class is to manage my list of subscriptions. It manages the list of
 * subscription to save and load the subscriptions when Serialized, used to save data.
 * We also have a few methods, init(), which makes a instance of the Sub Manager if not already
 * initialed. We also have get(), which returns the Sub manager. Also, we have our load and save
 * methods and our type conversion methods (Subscription to String method and String to Subscription method).
 */

public class SubManager {

    Context context;
    static private SubManager subManager = null;
    static final String prefFile =  "Subs";
    static final String slKey = "Subs";


    /**
     * Constructor method (Initialize)
     *
     * @param context is the environment
     */
    public SubManager(Context context){
        this.context=context;
    }


    /**
     * Setup the list of subscriptions
     *
     * @param context is the environment
     */
    public static void init(Context context) {
        //Check if my sub_manager is set to null, if so initialize it
        if (subManager==null){
            if(context==null){
                throw new RuntimeException("Error Occurred");
            }
            else{
                subManager=new SubManager(context);
            }
        }
    }


    /**
     * Get the sub manager
     *
     * @return the sub manager if its not null
     */
    public static SubManager get() {
        if (subManager==null){
            throw new RuntimeException("Error Occurred");
        }
        else{
            return subManager;
        }
    }


    /**
     * Save the list of Subscriptions
     *
     * @param sl is the list of subscriptions
     * @throws IOException if any error occurs when saving the list of subscriptions.
     */
    public void save(SubscriptionList sl) throws IOException {
        SharedPreferences setting = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(slKey, SubToString(sl));
        editor.commit();
    }


    /**
     * Load the list of Subscriptions
     *
     * @return the list of subscriptions
     * @throws ClassNotFoundException
     * @throws IOException if any error occurs when loading the list of subscriptions.
     */
    public SubscriptionList load() throws  ClassNotFoundException, IOException {
        SharedPreferences setting = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        String sub = setting.getString(slKey, "");
        //Check if sub is empty
        if (sub.equals("")) {
            return (new SubscriptionList());
        }
        else {
            return SubFromString(sub);
        }

    }


    /**
     * Return from Serialized
     *
     * @param sub is the string going containg the subscriptions data
     * @return the list of subscriptions
     * @throws ClassNotFoundException
     * @throws IOException if any error occurs when returning from Serialized.
     */
    private SubscriptionList SubFromString(String sub) throws ClassNotFoundException, IOException {
        ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(sub, Base64.DEFAULT));
        ObjectInputStream oi = new ObjectInputStream(bi);
        return ((SubscriptionList) oi.readObject());
    }


    /**
     * To be Serialized
     *
     * @param sl is the list of subscriptions
     * @return the string Serialized
     * @throws IOException if any error when trying to be Serialized
     */
    private String SubToString(SubscriptionList sl) throws IOException  {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(sl);
        oo.close();
        byte bytes[] = bo.toByteArray();
        return (Base64.encodeToString(bytes,Base64.DEFAULT));
    }
}
