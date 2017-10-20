package com.dmitry_simakov.queue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dmitry_simakov.queue.fragments.ModelsListFragment;

public class MainActivity extends AppCompatActivity implements ModelsListFragment.ModelsListListener {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LOG", "MainActivity: onCreate");
        setContentView(R.layout.activity_main);
        Log.d("LOG", "setContentView successful");
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        Log.d("LOG", "setSupportActionBar successful");
    }
    
    @Override
    public void itemClicked(int id) {
        Log.d("LOG", "MainActivity: itemClicked");
        Intent intent = new Intent(this, ModelActivity.class);
        String modelName = ModelsListFragment.getModelName(id);
        intent.putExtra(ModelActivity.MODEL_NAME, modelName);
        startActivity(intent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_settings) {
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}