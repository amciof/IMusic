package com.amciof.imusic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private final static String TAG = "SettingsActivity";

    CheckBox theme;
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
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        theme = (CheckBox) findViewById(R.id.enabled);
        if (themeName.equals("Green Theme")) {
            theme.setChecked(true);
        } else {
            theme.setChecked(false);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCheckboxClicked(View view) {
        theme = (CheckBox) findViewById(R.id.enabled);

        SharedPreferences.Editor prefEditor = settings.edit();
        if (theme.isChecked()) {
            prefEditor.putString("THEME", "Green Theme");
            Toast toast = Toast.makeText(this, "Change to green theme", Toast.LENGTH_LONG);
            toast.show();
        } else {
            prefEditor.putString("THEME", "Default Theme");
            Toast toast = Toast.makeText(this, "Change to default theme", Toast.LENGTH_LONG);
            toast.show();
        }
        prefEditor.apply();
    }

}
