package com.hacked.hamsemare.hamse_subbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Hamsemare on 2018-01-15.
 */

/*Citations-
Used and watched Abrams Hindle's Student picker tutorial on youtube as reference and a guide
https://www.youtube.com/watch?v=k9ZNbsc0Qgo&t=7s
*/


/*
Purpose of the Sub Manager class is to manage my list of subscriptions. It manages the list of subscription to save and load the subscriptions when
Serialized, used to save data. We also have a few methods, init(), which makes a instance of the Sub Manager if not already initialed. We also have
get, which returns the Sub manager. And we have our load and save methods and  our type conversion methods (Sub to String method and String to Sub method).
*/


public class Sub_Manager {
    Context context;
    static private Sub_Manager sub_manager = null;
    static final String prefFile =  "Subs";
    static final String slKey = "Subs";


    //Constructor method (Initialize)
    public Sub_Manager(Context context){
        this.context=context;
    }
    //Setup the list of subscriptions
    public static void init(Context context) {
        //Check if my sub_manager is set to null, if so initialize it
        if (sub_manager==null){
            if(context==null){
                throw new RuntimeException("Error Occurred");
            }
            else{
                sub_manager=new Sub_Manager(context);
            }
        }
    }


    //Get Method returns the sub manager if not null
    public static Sub_Manager get() {
        if (sub_manager==null){
            throw new RuntimeException("Error Occurred");
        }
        else{
            return sub_manager;
        }
    }




    // Save the list of Subscriptions
    public void save(Sub_list sl) throws IOException {
        SharedPreferences setting = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(slKey, SubToString(sl));
        editor.commit();
    }
    //Load the list of Subscriptions
    public Sub_list load() throws  ClassNotFoundException, IOException {
        SharedPreferences setting = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        String sub = setting.getString(slKey, "");
        //Check if sub is empty
        if (sub.equals("")) {
            return (new Sub_list());
        }
        else {
            return SubFromString(sub);
        }

    }


    //Return from Serialized
    private Sub_list SubFromString(String sub) throws ClassNotFoundException, IOException {
        ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(sub, Base64.DEFAULT));
        ObjectInputStream oi = new ObjectInputStream(bi);
        return ((Sub_list) oi.readObject());
    }
    //To be Serialized
    private String SubToString(Sub_list sl) throws IOException  {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(sl);
        oo.close();
        byte bytes[] = bo.toByteArray();
        return (Base64.encodeToString(bytes,Base64.DEFAULT));
    }

}
