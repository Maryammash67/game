package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //initialize credit and winning to 0
    int credit = 0;
    int winnings = 0;
    //initialize fruit values to 0
    int fruit1 = 0;
    int fruit2 = 0;
    int fruit3 = 0;

    //create a list to store all fruit images
    List fruits = Arrays.asList(
            R.drawable.apple, R.drawable.banana,
            R.drawable.bar, R.drawable.cherries,
            R.drawable.grapes, R.drawable.lemon,
            R.drawable.melon, R.drawable.orange
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get reference to view - user interactions

        Button btnSpin = findViewById(R.id.btnSpin);
        Button btnCredit = findViewById(R.id.btnCredit);
        Button btnCollect = findViewById(R.id.btnCollect);
        TextView txtWinnings = findViewById(R.id.txtWinnings);
        TextView txtCredit = findViewById(R.id.txtCredit);
        ImageView imageView = findViewById(R.id.imageView);
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView4 = findViewById(R.id.imageView4);

        //disable Spin button and collect button when the app first lunching
        btnSpin.setEnabled(false);
        btnCollect.setEnabled(false);

        btnCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //increase credit by 1
                credit++;
                //enable Spin button
                btnSpin.setEnabled(true);
                //Display credit on screen
                txtCredit.setText(String.valueOf(credit));

            }

        });
        btnSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reduce credit value by 1
                credit--;
                //display credit inside txtCredit
                //for this credit must be converted to String
                txtCredit.setText(String.valueOf(credit));
                //check if credit is 0 and disable spin button
                if (credit==0){
                    btnSpin.setEnabled(false);
                }
                //if the credit is 1 or more
                else {
                    //call spinRells() function
                    spinReels();
                    //check if all the fruits are same
                    if (fruit1 == fruit2 && fruit2 == fruit3){
                        winnings += 10;
                        btnCollect.setEnabled(true);
                    }
                    //check if fruit2 and fruit3 are same,
                    else if (fruit2 == fruit3) {
                        winnings += 5;
                        btnCollect.setEnabled(true);
                    }
                    //display winnings
                    txtWinnings.setText(String.valueOf(winnings));

                }
            }

        });
        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //display pop up message
                Toast.makeText(getApplicationContext(),"you have collected "+winnings+"winnings",Toast.LENGTH_LONG).show();
                //reset winnings to 0
                winnings=0;
                //display winnings after converting int value to string
                txtWinnings.setText(String.valueOf(winnings));
                //disable collect button
                btnCollect.setEnabled(false);
            }
        });
        Log.d(TAG,"OnCreate()");
        if(savedInstanceState==null){
            credit=0;
            winnings=0;
            fruit1=0;
            fruit2=0;
            fruit3=0;
            spinReels();
        }
        else {
            credit=savedInstanceState.getInt("credit");
            winnings = savedInstanceState.getInt("winning");
            txtCredit.setText(String.valueOf(credit));
           txtWinnings.setText(String.valueOf(winnings));
            fruit1= savedInstanceState.getInt("fruit1");
            fruit2= savedInstanceState.getInt("fruit2");
            fruit3= savedInstanceState.getInt("fruit3");
            imageView.setImageResource((Integer) fruits.get(fruit1));
            imageView2.setImageResource((Integer) fruits.get(fruit2));
            imageView4.setImageResource((Integer) fruits.get(fruit3));
            btnSpin.setEnabled(savedInstanceState.getBoolean("spinEnabled"));
            btnCollect.setEnabled(savedInstanceState.getBoolean("collectEnable"));

        }
    }//end of onCreate
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"OnStart()");
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"onRestart()");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"onResume()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"onStop");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"OnPause");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("credit",credit);
        outState.putInt("winnings",winnings);
        outState.putInt("fruit1",fruit1);
        outState.putInt("fruit2",fruit2);
        outState.putInt("fruit3",fruit3);
        Button btnSpin= findViewById(R.id.btnSpin);
        Button btnCollect= findViewById(R.id.btnCollect);
        //Button btnCredit = findViewById(R.id.btnCredit);

        outState.putBoolean("spinEnabled",btnSpin.isEnabled());
        //outState.putBoolean("creditEnabled",btnCredit.isEnabled());
        outState.putBoolean("createCollect",btnCollect.isEnabled());
    }

    public void spinReels(){
        //create a new object from random class
        Random rand = new Random();
        //specifying upperbound as 8 since we have 8 images
        //range is from 0 to 7
        int upperbound = 8;
        //assigning random integers for the 3 fruits
        fruit1 = rand.nextInt(upperbound);
        fruit2 = rand.nextInt(upperbound);
        fruit3 = rand.nextInt(upperbound);

        //getting references to image views
        ImageView imageView = findViewById(R.id.imageView);
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView4 = findViewById(R.id.imageView4);

        //assigning random images from list
        imageView.setImageResource((Integer) fruits.get(fruit1));
        imageView2.setImageResource((Integer) fruits.get(fruit2));
        imageView4.setImageResource((Integer) fruits.get(fruit3));

    }

}