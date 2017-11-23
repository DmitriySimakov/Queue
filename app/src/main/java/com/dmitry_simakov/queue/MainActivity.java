package com.dmitry_simakov.queue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LOG", "MainActivity: onCreate");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("LOG", "MainActivity: onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("LOG", "MainActivity: onOptionItemSelected");
    
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        switch (item.getItemId()) {
            case R.id.conventions:
                builder.setTitle(R.string.conventions);
                builder.setItems(R.array.conventionsArray, null);
                break;
            case R.id.about:
                builder.setTitle(R.string.about);
                builder.setMessage(R.string.aboutMessage);
                break;
        }
        AlertDialog dialog = builder.create();
        dialog.show();
        
        return super.onOptionsItemSelected(item);
    }
}