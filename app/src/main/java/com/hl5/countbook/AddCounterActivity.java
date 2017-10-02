/**
 * this class add the function to the countbook where user can add a new counter

 */
package com.hl5.countbook;

import android.os.Environment;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class AddCounterActivity extends Activity {

    EditText nameEt;
    EditText initialEt;
    EditText commentEt;
    EditText dateEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String date = df.format(new Date());

        dateEt = (EditText)findViewById(R.id.date);
        dateEt.setText(date);

        nameEt = (EditText)findViewById(R.id.name);
        initialEt = (EditText)findViewById(R.id.init_value);
        commentEt = (EditText)findViewById(R.id.comment);

        Button addBtn = (Button) findViewById(R.id.add_button);
        Button cancelBtn = (Button) findViewById(R.id.cancel_button);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void add(){
        String name = nameEt.getText().toString();
        String date = dateEt.getText().toString();
        String initialValue = initialEt.getText().toString();
        String curentValue = initialValue;
        String comment = commentEt.getText().toString();

        ArrayList<String> lines = new ArrayList<String>();

        try {

            Scanner scanner = new Scanner(new FileInputStream(getFilePath()));
            while (scanner.hasNextLine()){
                lines.add(scanner.nextLine());
            }
            scanner.close();


        }catch (IOException e){
            e.printStackTrace();

        }
        lines.add(name);
        lines.add(date);
        lines.add(curentValue);
        lines.add(initialValue);
        lines.add(comment);
        try{
            PrintWriter printWriter = new PrintWriter(getFilePath());
            for (int i = 0; i < lines.size(); ++i) {
                printWriter.println(lines.get(i));
            }
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String getFilePath() {

        String sdpath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/data.txt";
        return sdpath;

    }
}
