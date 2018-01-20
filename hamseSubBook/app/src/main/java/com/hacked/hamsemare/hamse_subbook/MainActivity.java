package com.hacked.hamsemare.hamse_subbook;

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


/*Citations-
Used and watched Abrams Hindle's Student picker tutorial on youtube as reference and a guide
https://www.youtube.com/watch?v=k9ZNbsc0Qgo&t=7s
*/


/*
Purpose of the MainActivity Class is to display the main layout, which has a list of Subscriptions type Sub, and  "add subscription" button.
Also, we update the array list when a call to the listener of list of subscriptions, and add all of the subscriptions to the array list. We constantly
any changes because of the listener of our list of subs.The Main Activity class also takes care of when user deletes a subscription and wants to
edit a subscription. We also have a Text field which displays the total monthly charges for all the subscriptions combined.
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
        Sub_Manager.init(this.getApplicationContext());

        //Show the list of subscriptions
        ListView list = findViewById(R.id.listView);
        Collection<Sub> subs = Sub_Controller.get().get();
        //Make the list of subs and the adapter constant so it can not be reassigned
        final ArrayList<Sub> list_of_subs = new ArrayList<Sub>(subs);
        final ArrayAdapter<Sub> adapter = new ArrayAdapter<Sub>(this, android.R.layout.simple_list_item_1, list_of_subs);
        list.setAdapter(adapter);

        //Set the initial Total Monthly Charge
        TextView edit_charge= findViewById(R.id.edit_charge);
        edit_charge.setText(String.format("$%.2f", total_money(list_of_subs)));

        //Retrieves and updates the Subscription list
        Sub_Controller.get().add_listener(new Listener() {
            @Override
            //Update the subscriptions list
            public void update() {
                list_of_subs.clear();
                Collection<Sub> subs = Sub_Controller.get().get();
                //Adds all the subsciptions to the array list list of subs
                list_of_subs.addAll(subs);
                adapter.notifyDataSetChanged();

                //Set the Total Monthly Charge when update occurs
                TextView edit_charge= findViewById(R.id.edit_charge);
                edit_charge.setText(String.format("$%.2f", total_money(list_of_subs)));
            }
        });




        //DELETE or EDIT From the list of Subscriptions
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder btn = new AlertDialog.Builder(MainActivity.this);
                btn.setMessage("DELETE OR EDIT SUBSCRIPTION");

                //Set the integer position1 to be constant so it can not be reassigned
                final int position1=position;

                //DELETE the Subscription from the Subscription list
                btn.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete it from both the list of subs and the sub controller
                        list_of_subs.remove(position1);
                        adapter.notifyDataSetChanged();
                        Sub_Controller.get().remove(position1);
                    }
                });


                //EDIT the Subscription in the Subscription list
                btn.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Sub sub= list_of_subs.get(position);
                        //Call edit method passing in a location type int  and subscription type Sub
                        edit(position1, sub);
                    }
                });
                // Call to show the Alert Message
                btn.show();
            }
        });
    }



    //Returns the total monthly charge for all the subscriptions combined
    public Double total_money(ArrayList<Sub> list_of_subs){
        Double echarge=0.0;
        for(int i=0;i<list_of_subs.size(); i++){
            Double ch=list_of_subs.get(i).getc();
            echarge+=ch;
        }
        //Returns a Double
        return echarge;
    }

    //Call to go to the next activity with the Subscription
    public void edit(Integer location, Sub sub){
        Intent intent= new Intent(this, add.class);
        intent.putExtra("subscription", sub);
        intent.putExtra("location", location);
        startActivity(intent);
    }
    //Add button which changes the view to the add activity
    public void add_btn(View view) {
        Intent intent = new Intent(this, add.class);
        startActivity(intent);
    }
}
