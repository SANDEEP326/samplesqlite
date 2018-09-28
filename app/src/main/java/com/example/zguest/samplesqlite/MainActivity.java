package com.example.zguest.samplesqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataBase mydb;
    ListView obj;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("entered into main","yay");
        add=(Button)findViewById(R.id.button_add);
        mydb = new DataBase(this);
        ArrayList array_list = mydb.getAllElements();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);

        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);

        obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);
                Log.e("obj section",""+dataBundle);

                Intent intent = new Intent(getApplicationContext(),DisplayElement.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                Log.e("add section",""+dataBundle);

                Intent intent = new Intent(getApplicationContext(),DisplayElement.class);
                intent.putExtras(dataBundle);

                startActivity(intent);

            }
        });
    }
}
