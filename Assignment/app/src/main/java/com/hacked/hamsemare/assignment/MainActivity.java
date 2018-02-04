package com.hacked.hamsemare.assignment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Purpose of the MainActivity Class is to display the main layout, which has a list of
 * Subscriptions type Subscription, and "add subscription" button. The Main Activity class also
 * takes care of when user deletes a subscription and wants to edit a subscription. We also have a
 * Text field which displays the total monthly charges for all the subscriptions combined.
 *
 * @author Hamse Mare
 * @see Subscription
 * @see SubscriptionList
 * @see SubController
 * @see Add
 * @see SubManager
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UNDERLINE Title
        TextView title = findViewById(R.id.title);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //Call to the init method in the Sub_manager class
        SubManager.init(this.getApplicationContext());

        //Show the list of subscriptions
        //Make the list of subs and the adapter constant so it can not be reassigned
        ListView list = findViewById(R.id.list);
        Collection<Subscription> subs = SubController.get().get();

        final ArrayList<Subscription> listOfSubs = new ArrayList<Subscription>(subs);
        final ArrayAdapter<Subscription> adapter = new ArrayAdapter<Subscription>(this, android.R.layout.simple_list_item_1, listOfSubs);
        list.setAdapter(adapter);

        //Set the chargeAmount to the sum of the charges in the arraylist
        TextView editCharge= findViewById(R.id.chargeAmount);
        editCharge.setText(String.format("$%.2f", totalMoney(listOfSubs)));


        //Retrieves and updates the Subscription list
        SubController.get().addListener(new Listener() {
            @Override
            /**
             * Update the subscriptions list
             */
            public void update() {
                listOfSubs.clear();
                Collection<Subscription> subs = SubController.get().get();
                //Adds all the subsciptions to the array list list of subs
                listOfSubs.addAll(subs);
                adapter.notifyDataSetChanged();

                //Set the Total Monthly Charge when update occurs
                TextView editCharge= findViewById(R.id.chargeAmount);
                editCharge.setText(String.format("$%.2f", totalMoney(listOfSubs)));
            }
        });



        //DELETE or EDIT From the list of Subscriptions
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            /**
             * DELETE or EDIT From the list of Subscriptions when Subscription CLicked on
             */
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder btn = new AlertDialog.Builder(MainActivity.this);
                btn.setMessage("DELETE OR EDIT SUBSCRIPTION");

                //Set the integer position1 to be constant so it can not be reassigned
                final int position1=position;

                //DELETE the Subscription from the Subscription list
                btn.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    /**
                     * DELETE the Subscription from the Subscription list
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete it from both the list of subs and the sub controller
                        listOfSubs.remove(position1);
                        adapter.notifyDataSetChanged();
                        SubController.get().remove(position1);
                    }
                });


                //EDIT the Subscription in the Subscription list
                btn.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    /**
                     * EDIT the Subscription in the Subscription list
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        Subscription sub= listOfSubs.get(position);
                        //Call edit method passing in a location type int  and subscription type Sub
                        edit(position1, sub);
                    }
                });
                // Call to show the Alert Message
                btn.show();
            }
        });
    }



    /**
     * Gets the total monthly charge for all the subscriptions combined
     *
     * @param subs is the list of subscriptions
     * @return the total charge of all the subscriptions added together
     */
    public Double totalMoney(ArrayList<Subscription> subs){
        Double echarge=0.0;
        for(int i=0;i<subs.size(); i++){
            Double ch=subs.get(i).getC();
            echarge+=ch;
        }
        //Returns a Double
        return echarge;
    }

    /**
     * Call to go to the next activity with the Subscription
     */
    public void edit(Integer location, Subscription sub){
        Intent intent= new Intent(this, Add.class);
        intent.putExtra("subscription", sub);
        intent.putExtra("location", location);
        startActivity(intent);
    }


    /**
     * Add button which changes the view to the add activity
     *
     * @param view is the next activity
     */
    public void addBtn(View view) {
        Intent intent = new Intent(this, Add.class);
        startActivity(intent);
    }
}
