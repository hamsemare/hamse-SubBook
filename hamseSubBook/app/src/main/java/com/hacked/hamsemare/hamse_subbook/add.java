package com.hacked.hamsemare.hamse_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/*Citations-
Used Abrams Hindle's Student picker tutorial on youtube as reference and a guide
https://github.com/abramhindle/student-picker
https://www.youtube.com/watch?v=k9ZNbsc0Qgo&t=7s
*/


/*
Purpose of the add Class is to be able to add or edit a subscription depending on what the extra items in intent are. Initially we have the submit button
Showing and the replace button gone, however if there are extra items passed through the intent then we show the replace button and set the submit button
as gone. to the add In order to add and replace stuff, we need to validate what the user enters to make sure its in the correct format we want.
If the name, date, and charge fields are entered correctly go back to the MainActivity class. The class also includes a few extra methods to tell
the user what just happened for example the add() and edit() methods display a popup telling the user if something was edited or deleted
*/


public class add extends AppCompatActivity {

    public static int location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Grab the extras passed with the intent
        Intent intent= getIntent();
        location = intent.getIntExtra("location", 0);
        Sub subscription= (Sub) intent.getSerializableExtra("subscription");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Sub_Manager.init(this.getApplicationContext());


        //Initially the replace button is hidden and the submit button is visible
        Button replace_btn= findViewById(R.id.replace);
        Button submit_btn=findViewById(R.id.add_btn);

        //Set the fields to the correct input if subscription is not null
        // (If something is passed by the intent)
        if (subscription!= null){
            //Set the submit button to be hidden and the replace button to be visible
            submit_btn.setVisibility(View.GONE);
            replace_btn.setVisibility(View.VISIBLE);
            //Set the fields
            set(subscription);
        }
    }

    //Validate the user inputs, make sure it meets all standards
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
            Date dates_input= dateFormat.parse(sdate);
        }
        catch(ParseException e){
            date.setError("DATE format is yyyy-mm-dd");
            valid=false;
            return valid;
        }
        //If checks are all good it will return true
        return valid;
    }



    //If the data entered is correct, then a new subscription of type Sub will be added to the list of subscriptions
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

            Sub_Controller subscriptions = new Sub_Controller();
            subscriptions.Addsub(sname, sdate, dcharge, scomment);

            //Call to show the added message
            add();

        }
        //Display a error message
        else if(dcharge<0){
            charge.setError("Enter a non negative currency");
        }
        else{
            return;
        }
    }



    //Display a edited message
    public void edit(){
        Toast.makeText(getBaseContext(),"Edited", Toast.LENGTH_LONG).show();
        done();
    }
    //Display a added message
    public void add(){
        Toast.makeText(getBaseContext(),"Added", Toast.LENGTH_LONG).show();
        done();
    }
    //Go to the mainActivity
    public void done(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    //Set the following fields to the subscriptions attributes, so name, date, charge, comment
    public void set(Sub subscription){
        String prev_name= subscription.getname();
        String prev_date= subscription.getdate();
        String prev_charge= subscription.getcharge();
        String prev_comment = subscription.getcomment();

        //Set the fields to the previous data passed by the intent
        //Name
        EditText  name= findViewById(R.id.name);
        name.setText(prev_name);

        //Date
        EditText date=findViewById(R.id.date);
        date.setText(prev_date);

        //Monthly charge
        EditText charge= findViewById(R.id.total_monthly_charge);
        charge.setText(prev_charge);

        //Comments
        EditText comment=findViewById(R.id.comment);
        comment.setText(prev_comment);
    }

    //Adds a new subscription that the user modified and removes the old subscription
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

            Sub_Controller subscriptions = new Sub_Controller();
            Sub_Controller.get().remove(location);
            subscriptions.Addsub(sname, sdate, dcharge, scomment);
            //Call to show the edited message
            edit();
        }

        //Display a error message
        else if(dcharge<0){
            charge.setError("Enter a non negative currency");
        }
        else{
            return;
        }
    }
}


