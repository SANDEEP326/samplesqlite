package com.example.zguest.samplesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
    public static final String DATABASE="datalist";
    public static final String table_name="datatable";
    public static final String column_id="ID";
    public static final String column_name="NAME";
    public static final String column_place="PLACE";
    public static final String column_email="EMAIL";
    public static final String column_number="PHONE";
    public DataBase(Context context){
        super(context,DATABASE,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+table_name+"("+column_id+" integer primary key, "
                +column_name+" text, "+column_place+" text, "+column_email+" text, "
                +column_number+" text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+table_name);
        onCreate(db);
    }

    public boolean insertElement (String name, String place, String email, String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("place", place);
        contentValues.put("email", email);
        contentValues.put("phone", number);
        db.insert(table_name, null, contentValues);
        return true;
    }

    public Cursor getElement(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur = db.rawQuery("Select * from "+table_name+" where id="+id,null);
        return cur;
    }

    public Integer deleteElement(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(table_name,
                "id = ? ",
                new String[] { Integer.toString(id) });

    }
    public boolean updateElement(int id, String name, String address, String email, String number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("place", address);
        contentValues.put("email", email);
        contentValues.put("phone", number);
        db.update(table_name, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public ArrayList<String> getAllElements(){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("Select * from "+table_name,null);
        cur.moveToFirst();

        ArrayList<String> array=new ArrayList<String>();

        while(cur.isAfterLast() == false){
            array.add(cur.getString(cur.getColumnIndex(column_name)));
            cur.moveToNext();
        }
        return array;
    }
}
