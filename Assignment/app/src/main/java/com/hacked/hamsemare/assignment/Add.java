package com.hacked.hamsemare.assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Purpose of the Add Class is to be able to add or edit a subscription depending on what the extra
 * items in intent are. Initially, we have the submit button showing and the replace button gone,
 * however, if there are extra items passed through the intent then we show the replace button and
 * set the submit button as gone. In order to add and replace stuff, we need to validate what the
 * user enters to make sure its in the correct format we want. If the name, date, and charge fields
 * are entered correctly go back to the MainActivity class.
 *
 *
 * @author Hamse Mare
 * @see MainActivity
 * @see SubManager
 * @see SubController
 * @see Subscription
 */

public class Add extends AppCompatActivity {

    public static int location;
    private static String pname;
    private static String pdate;
    private static String pcharge;
    private static String pcomment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Grab the extras passed with the intent
        Intent intent= getIntent();
        Subscription subscription= (Subscription) intent.getSerializableExtra("subscription");
        location = intent.getIntExtra("location", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        SubManager.init(this.getApplicationContext());

        //Initially the replace button is hidden and the submit button is visible
        Button replaceBtn= findViewById(R.id.replace);
        Button submitBtn=findViewById(R.id.add_btn);

        //Set the fields to the correct input if subscription is not null
        // (If something is passed by the intent)
        if (subscription!= null){

            pname= subscription.getName();
            pdate=subscription.getDate();
            pcharge=subscription.getCharge();
            pcomment= subscription.getComment();

            //Set the submit button to be hidden and the replace button to be visible
            submitBtn.setVisibility(View.GONE);
            replaceBtn.setVisibility(View.VISIBLE);
            //Set the fields
            set();
        }
    }


    /**
     * Set the following fields to the subscriptions attributes, so name, date, charge, comment
     */
    public void set(){
        //Set the fields to the previous data passed by the intent
        //Name
        EditText name= findViewById(R.id.name);
        name.setText(pname);

        //Date
        EditText date=findViewById(R.id.date);
        date.setText(pdate);

        //Monthly charge
        EditText charge= findViewById(R.id.total_monthly_charge);
        charge.setText(pcharge);

        //Comments
        EditText comment=findViewById(R.id.comment);
        comment.setText(pcomment);
    }


    /**
     * Validate the user inputs, make sure it meets all standards
     *
     * @return true for the data the user entered is valid
     * and false if the data the user entered is not in the correct format
     */
    public boolean isvalid(){
        boolean valid=true;
        //Name
        EditText name= findViewById(R.id.name);
        String sname= name.getText().toString();

        //date
        EditText date= findViewById(R.id.date);
        String sdate= date.getText().toString();

        //Monthly charge
        EditText charge= findViewById(R.id.total_monthly_charge);
        String scharge= charge.getText().toString();


        //**********************CHECKS*****************************************
        //Check name
        if (sname.isEmpty()){
            name.setError("Enter name");
            valid=false;
        }
        //Check monthly charge
        if(scharge.isEmpty()){
            charge.setError("Enter Monthly Charge");
            valid=false;
        }

        //Check to see if date is same as the format required
        DateFormat dateFormat= new SimpleDateFormat("yyyy-mm-dd");
        try{
            Date datesInput= dateFormat.parse(sdate);
        }
        catch(ParseException e){
            date.setError("DATE format is yyyy-mm-dd");
            valid=false;
            return valid;
        }
        //If checks are all good it will return true
        return valid;
    }



    /**
     * If the data entered is correct, then a new subscription of type Subscription will be added
     * to the list of subscriptions
     *
     * @param view is the next activity, and if the user entered correct data, then we'll go back
     *             to the main Activity
     */
    public void add(View view){
        //Name
        EditText name= findViewById(R.id.name);
        String sname= name.getText().toString();

        //Date
        EditText date=findViewById(R.id.date);
        String sdate= date.getText().toString();

        //Monthly charge
        EditText charge= findViewById(R.id.total_monthly_charge);
        String scharge= charge.getText().toString();

        //Convert the string to a Double if the string is not empty else set it to 0
        Double dcharge;
        if(scharge.length()>0){
            dcharge= Double.parseDouble(scharge);
        }
        else {
            dcharge=0.0;
        }

        //Comments
        EditText comment=findViewById(R.id.comment);
        String scomment= comment.getText().toString();

        if( isvalid() && dcharge>=0 ){

            //Pass the data needed to add as a entry to the subscriptions list, and add them to the controller
            SubController subscriptions = new SubController();
            subscriptions.addSub(sname, sdate, dcharge, scomment);

            //Call to back to the main activity
            done();
        }

        //Display a error message
        else if(dcharge<0){
            charge.setError("Enter a non negative currency");
        }

        else{
            return;
        }
    }


    /**
     * Adds a new subscription that the user modified and removes the old subscription
     *
     * @param view is the next activity, and if the user entered correct data, then we'll go back
     *             to the main Activity
     */
    public void replace(View view) {
        //Name
        EditText name= findViewById(R.id.name);
        String sname= name.getText().toString();

        //Date
        EditText date=findViewById(R.id.date);
        String sdate= date.getText().toString();

        //Monthly charge
        EditText charge= findViewById(R.id.total_monthly_charge);
        String scharge= charge.getText().toString();

        //Convert the string to a Double if the string is not empty else set it to 0
        Double dcharge;
        if(scharge.length()>0){
            dcharge= Double.parseDouble(scharge);
        }
        else {
            dcharge=0.0;
        }

        //Comments
        EditText comment=findViewById(R.id.comment);
        String scomment= comment.getText().toString();

        if(isvalid() && dcharge>=0){

            //Pass the data needed to add as a entry to the subscriptions list, and add them to the controller
            //Remove the old entry
            SubController subscriptions = new SubController();
            SubController.get().remove(location);
            subscriptions.addSub(sname, sdate, dcharge, scomment);

            //Call to back to the main activity
            done();
        }

        //Display a error message
        else if(dcharge<0){
            charge.setError("Enter a non negative currency");
        }
        else{
            return;
        }
    }


    /**
     * Go back to the mainActivity
     */
    public void done(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
