package com.example.shrey.defhacks;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    RelativeLayout parentLayout;
    private Button settingsButton;
    private ArrayList<String> values = new ArrayList<String>();
    ArrayList<String> favoriteList = new ArrayList<String>();
    int currentFavorite = 0;
    static final int REQUEST_CODE = 1;


    boolean state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Config.context = this;
        parentLayout = (RelativeLayout) findViewById(R.id.layout);

        settingsButton = (Button) findViewById(R.id.settingsButton);


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                values.add(".");
                Toast.makeText(getApplicationContext(), "dot", Toast.LENGTH_SHORT).show();
            }
        });

        parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getApplicationContext(), "dash", Toast.LENGTH_SHORT).show();
                values.add("-");
                System.out.println(values);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {
            Toast.makeText(getApplicationContext(), "space", Toast.LENGTH_SHORT).show();
            values.add("/");
            return true;
        }


        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            state = !state;
            String lala;
            if (state) {
                lala = "on";
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
            }
            else lala = "off";
            Toast.makeText(getApplicationContext(), lala, Toast.LENGTH_SHORT).show();

        }

        if (!state) { //mode 1

            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                Toast.makeText(getApplicationContext(), "backspace", Toast.LENGTH_SHORT).show();
                int size = values.size();
                if (size != 0) {
                    values.remove(size - 1);
                    return true;
                } else
                    Toast.makeText(getApplicationContext(), "Enter morse code", Toast.LENGTH_LONG).show();
            }

            if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {       //Go to next favorite number

                sendSMSMessage();
            }
        }

        if (state) {

            if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
                currentFavorite++;
                if (currentFavorite >= favoriteList.size()) {
                    currentFavorite = 0;
                }
                Toast.makeText(getApplicationContext(), "current favorite = " + currentFavorite, Toast.LENGTH_SHORT).show();
            }

            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                Toast.makeText(getApplicationContext(), "clear", Toast.LENGTH_SHORT).show();
                values.clear();
            }
        }
        return true;
    }



    protected void sendSMSMessage() {
        Log.i("Send SMS", "");
        //String phoneNo = txtphoneNo.getText().toString();
        String phoneNo = favoriteList.get(currentFavorite);
        System.out.println(values);
        String message = toEnglish(values);
        System.out.println(message);


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        values.clear();

    }

    public static String[] morse = {".-", "-...", "-.-.", "-..", ".", "..-.",
            "--.", "....", "..", ".---", "-.-", ".-..",
            "--", "-.", "---", ".--.", "--.-", ".-.",
            "...", "-", "..-", "...-", ".--", "-..-",
            "-.--", "--..", "/", ".-.-.-", "--..--", "---...",
            "..--..", ".----.", ".-..-.", "-----", ".----", "..---",
            "...--", "....-", ".....", "-....", "--...", "---..", "----."};
    public static String[] text = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
            "Z", " ", ".", ",", ":", "?", "'", "\"", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static String toEnglish(List<String> morseText) {

        List<String> newMorseText = new ArrayList<String>();

        String word = "";
        for (int i = 0; i < morseText.size(); i++) {

            if (morseText.get(i) != "/") {
                word += morseText.get(i);
                System.out.println(word);

            }
            if (morseText.get(i).equals("/") || i == morseText.size() - 1) {
                newMorseText.add(word);
                word = "";
            }
        }

        morseText = newMorseText;

        StringBuilder sb = new StringBuilder();
        String finalMessage = "";

        for (int i = 0; i < morseText.size(); i++) {       //go through user inputted morse code
            for (int j = 0; j < morse.length; j++) {
                if (morseText.get(i).equals(morse[j])) {
                    sb.append(text[j]);
                }
            }
        }

        finalMessage = sb.toString();

        return finalMessage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String result1 = data.getStringExtra("one");
                String result2 = data.getStringExtra("two");
                String result3 = data.getStringExtra("three");
                String result4 = data.getStringExtra("four");

                favoriteList.add(result1);
                favoriteList.add(result2);
                favoriteList.add(result3);
                favoriteList.add(result4);
            }

        }
    }
}



//receive messages through app
//need to save numbers
//type spaces
//fix closing when there is no message or number

