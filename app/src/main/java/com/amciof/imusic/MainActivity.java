package com.amciof.imusic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import static com.amciof.imusic.DBHelper.KEY_INFO;
import static com.amciof.imusic.DBHelper.KEY_NAME;
import static com.amciof.imusic.DBHelper.TABLE_BANDS;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    TextView dbInfo;
    ListView bandList;

    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor bandCursor;
    SimpleCursorAdapter bandAdapter;

    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = getSharedPreferences("Settings", MODE_PRIVATE);
        String themeName = settings.getString("THEME", "Default Theme");
        if (themeName.equals("Green Theme")) {
            setTheme(R.style.AppThemeGreen);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbInfo = (TextView) findViewById(R.id.dbInfo);
        bandList = (ListView) findViewById(R.id.bandList);
        bandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), BandActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(this);

        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        db.close();
        bandCursor.close();

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

        db = dbHelper.getReadableDatabase();

        bandCursor =  db.rawQuery("select * from "+ TABLE_BANDS, null);

        String[] headers = new String[] {KEY_NAME, KEY_INFO};

        bandAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, bandCursor, headers, new int[] {android.R.id.text1, android.R.id.text2}, 0);
        dbInfo.setText("Найдено элементов: " + String.valueOf(bandCursor.getCount()));
        bandList.setAdapter(bandAdapter);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch(id){
            case R.id.action_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void add(View view){
        Intent intent = new Intent(this, BandActivity.class);
        startActivity(intent);
    }

}
