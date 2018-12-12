package com.amciof.imusic;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BandActivity extends AppCompatActivity {

    private final static String TAG = "BandActivity";

    EditText nameBox;
    EditText infoBox;
    Button delButton;
    Button saveButton;

    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor bandCursor;

    long userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band);

        nameBox = (EditText) findViewById(R.id.editName);
        infoBox = (EditText) findViewById(R.id.editInfo);
        delButton = (Button) findViewById(R.id.deleteButton);
        saveButton = (Button) findViewById(R.id.saveButton);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }

        if (userId > 0) {
            bandCursor = db.rawQuery("select * from " + dbHelper.TABLE_BANDS + " where " +
                    dbHelper.KEY_ID + "=?", new String[]{String.valueOf(userId)});
            bandCursor.moveToFirst();
            nameBox.setText(bandCursor.getString(1));
            infoBox.setText((bandCursor.getString(2)));
            bandCursor.close();
        } else {
            delButton.setVisibility(View.GONE);
        }

        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected  void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected  void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
    }

    public void save(View view){
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.KEY_NAME, nameBox.getText().toString());
        cv.put(dbHelper.KEY_INFO, infoBox.getText().toString());

        if (userId > 0) {
            db.update(dbHelper.TABLE_BANDS, cv, dbHelper.KEY_ID + "=" + String.valueOf(userId), null);
        } else {
            db.insert(dbHelper.TABLE_BANDS, null, cv);
        }
        goHome();
    }

    public void delete(View view){
        db.delete(dbHelper.TABLE_BANDS, "_id = ?", new String[]{String.valueOf(userId)});
        goHome();
    }


    private void goHome(){
        db.close();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
