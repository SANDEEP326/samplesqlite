package com.example.zguest.samplesqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DisplayElement extends AppCompatActivity {
    DataBase mydb;
    EditText name, phone, place, email;
    Button save, edit, delete;
    int id_To_Update = 0;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_element);

        Log.e("in display element","Value");
        name=(EditText)findViewById(R.id.edit_name);
        phone=(EditText)findViewById(R.id.edit_phone);
        place=(EditText)findViewById(R.id.edit_place);
        email=(EditText)findViewById(R.id.edit_email);
        save=(Button)findViewById(R.id.button_save);
        edit=(Button)findViewById(R.id.button_edit);
        delete=(Button)findViewById(R.id.button_delete);
        mydb = new DataBase(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                Cursor cur = mydb.getElement(Value);
                id_To_Update = Value;
                cur.moveToFirst();

                String nam = cur.getString(cur.getColumnIndex(DataBase.column_name));
                String phon = cur.getString(cur.getColumnIndex(DataBase.column_number));
                String emai = cur.getString(cur.getColumnIndex(DataBase.column_email));
                String plac = cur.getString(cur.getColumnIndex(DataBase.column_place));

                if (!cur.isClosed()) {
                    cur.close();
                }

                save.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);

                name.setText((CharSequence) nam);
                name.setFocusable(false);
                name.setClickable(false);

                phone.setText((CharSequence) phon);
                phone.setFocusable(false);
                phone.setClickable(false);

                email.setText((CharSequence) emai);
                email.setFocusable(false);
                email.setClickable(false);

                place.setText((CharSequence) plac);
                place.setFocusable(false);
                place.setClickable(false);
            }
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save.setVisibility(View.VISIBLE);
                edit.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);

                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                phone.setEnabled(true);
                phone.setFocusableInTouchMode(true);
                phone.setClickable(true);

                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);

                place.setEnabled(true);
                place.setFocusableInTouchMode(true);
                place.setClickable(true);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydb.deleteElement(id_To_Update);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                Log.e("in save section:","extras="+extras);
                if(extras !=null) {
                    int Value = extras.getInt("id");
                    Log.e("in save section:","value="+Value);
                    id_To_Update = Value;
                }
                if(id_To_Update > 0){
                    if(mydb.updateElement(id_To_Update,name.getText().toString(),
                        place.getText().toString(),email.getText().toString(),
                        phone.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

                    } else{
                        Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    if(mydb.insertElement(name.getText().toString(),
                        place.getText().toString(),email.getText().toString(),
                        phone.getText().toString())){
                        Toast.makeText(getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getApplicationContext(), "not done",
                            Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                }
            }
        });
    }
}
