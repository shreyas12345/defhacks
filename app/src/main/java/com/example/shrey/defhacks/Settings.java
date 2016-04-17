package com.example.shrey.defhacks;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Settings extends AppCompatActivity {
    private EditText one;
    private EditText two;
    private EditText three;
    private EditText four;
    private Button goBack;
    private Button save;
    public String input;
    public String input2;
    public String input3;
    public String input4;

    /**Context mContext;

    public Settings(Context mContext){
        this.mContext = mContext;
    }
     **/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        one = (EditText)findViewById(R.id.editText);
        two = (EditText)findViewById(R.id.editText2);
        three= (EditText)findViewById(R.id.editText3);
        four = (EditText)findViewById(R.id.editText4);

        goBack = (Button)findViewById(R.id.button);
        save = (Button)findViewById(R.id.button2);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = getIntent();
                returnIntent.putExtra("one", one.getText().toString());
                returnIntent.putExtra("two", two.getText().toString());
                returnIntent.putExtra("three", three.getText().toString());
                returnIntent.putExtra("four", four.getText().toString());
                setResult(RESULT_OK, returnIntent);
                finish();


            }
        });

       /** save.setOnClickListener(new View.OnClickListener() {
            @Override
            public boolean onClick(View view) {
                writeToFile(one.getText().toString());
                writeToFile(two.getText().toString());
                writeToFile(three.getText().toString());
                writeToFile(four.getText().toString());
            }
        });

    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(mContext.openFileOutput("config.txt", mContext.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
**/

    }
}
