
package com.hl5.countbook;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends Activity {

    public static int index;

    public static ArrayList<Counter> readAllCounters(){
        ArrayList<Counter> counters = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileInputStream(AddCounterActivity.getFilePath()));
            while (scanner.hasNextLine()){

                String name = scanner.nextLine();
                Log.e("HL5Counter",name);
                String date = scanner.nextLine();

                Log.e("HL5Counter",date);
                String line = scanner.nextLine().trim();

                Log.e("HL5Counter",line);
                int currentValue = Integer.valueOf(line);
                line = scanner.nextLine().trim();

                Log.e("HL5Counter",line);
                int initialValue = Integer.valueOf(line);
                String comment = scanner.nextLine();

                Log.e("HL5Counter",comment);
                Counter counter = new Counter(name, date, currentValue, initialValue, comment);
                Log.e("HL5Counter", counter.toString());
                counters.add(counter);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("HL5Counter","readError");
            File file = new File(AddCounterActivity.getFilePath());
            file.delete();

        }
        return counters;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        super.onStart();
        final ListView listView = (ListView) findViewById(R.id.list_view);

        Button addBtn = (Button) findViewById(R.id.add_button);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddCounterActivity.class));
            }
        });
        Button delAll = (Button)findViewById(R.id.delete_button);
        delAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                ArrayList<Counter> counters = readAllCounters();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", "COUNTER NAME");
                map.put("current", "CURRENT VALUE");
                list.add(map);

                SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, list, R.layout.list_item,
                        new String[]{"name","current"},
                        new int[]{R.id.counter_name,R.id.counter_current});

                listView.setAdapter(adapter);

            }
        });




        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        ArrayList<Counter> counters = readAllCounters();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "COUNTER NAME");
        map.put("current", "CURRENT VALUE");
        list.add(map);
        for (int i = 0; i < counters.size(); ++i) {
            Counter counter = counters.get(i);
            map = new HashMap<String, Object>();
            map.put("name", counter.name);
            map.put("date", counter.date);
            map.put("initial", counter.initialValue);
            map.put("current", counter.currentValue);
            map.put("comment", counter.comment);
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.list_item,
                new String[]{"name","current"},
                new int[]{R.id.counter_name,R.id.counter_current});

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i - 1;
                startActivity(new Intent(MainActivity.this, DetailActivity.class));
            }
        });
    }

    void save(){
        ArrayList<Counter> counters = readAllCounters();
        counters.clear();
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

