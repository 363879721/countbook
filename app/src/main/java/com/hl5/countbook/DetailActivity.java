/**
 * this class is for the detail of the counter
 * user can view the date of the current value is change
 * user can increase and decrease the count
 * user can reset the current value to the initial value
 * user can delete the counter
 */
package com.hl5.countbook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailActivity extends Activity {


    EditText nameEt;
    EditText initialEt;
    EditText commentEt;
    EditText dateEt;
    EditText currentEt;
    ArrayList<Counter> counters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dateEt = (EditText)findViewById(R.id.date);
        nameEt = (EditText)findViewById(R.id.name);
        initialEt = (EditText)findViewById(R.id.init_value);
        commentEt = (EditText)findViewById(R.id.comment);
        currentEt = (EditText)findViewById(R.id.counter_current);
        counters = MainActivity.readAllCounters();
        final Counter counter = counters.get(MainActivity.index);

        nameEt.setText(counter.name);
        dateEt.setText(counter.date);
        commentEt.setText(counter.comment);
        initialEt.setText("" + counter.initialValue);
        currentEt.setText("" + counter.currentValue);

        Button okBtn = (Button)findViewById(R.id.ok_button);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                counter.comment = commentEt.getText().toString();
                counter.date = dateEt.getText().toString();
                counter.currentValue = Integer.valueOf(currentEt.getText().toString().trim());
                counter.name = nameEt.getText().toString();
                counter.initialValue = Integer.valueOf(initialEt.getText().toString().trim());
                save();
                finish();
            }
        });

        Button cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button increaseBtn = (Button)findViewById(R.id.increase_btn);
        Button decreaseBtn = (Button)findViewById(R.id.decrease_btn);

        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.valueOf(currentEt.getText().toString().trim());
                n += 1;
                currentEt.setText(String.valueOf(n));
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String date = df.format(new Date());
                dateEt.setText(date);
            }
        });


        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.valueOf(currentEt.getText().toString().trim());
                n -= 1;
                currentEt.setText(String.valueOf(n));
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String date = df.format(new Date());
                dateEt.setText(date);
            }
        });

        Button delBtn = (Button)findViewById(R.id.del_button);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counters.remove(MainActivity.index);
                save();
                finish();
            }
        });

        Button resetBtn = (Button)findViewById(R.id.reset_button);
        resetBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                int x = Integer.valueOf(initialEt.getText().toString().trim());
                currentEt.setText(String.valueOf(x));
                save();
            }


        });
    }


    void save(){
        try{
            PrintWriter printWriter = new PrintWriter(AddCounterActivity.getFilePath());
            for (int i = 0; i < counters.size(); ++i) {
                printWriter.println(counters.get(i).toString());
            }
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
