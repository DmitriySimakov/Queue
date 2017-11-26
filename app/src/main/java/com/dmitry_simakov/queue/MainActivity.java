package com.dmitry_simakov.queue;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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