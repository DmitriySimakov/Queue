package com.dmitry_simakov.queue;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmitry_simakov.queue.fragments.BarGraphFragment;
import com.dmitry_simakov.queue.fragments.ModelViewFragment;

public class ModelActivity extends AppCompatActivity {
    
    public static final String MODEL_NAME = "MODEL_NAME";
    
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    
    private String modelName;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LOG", "ModelActivity: onCreate");
        setContentView(R.layout.activity_model);
        
        Intent intent = getIntent();
        modelName = intent.getStringExtra(MODEL_NAME);
    
        Toolbar toolbar = (Toolbar) findViewById(R.id.modelToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(modelName);
        }
        Log.d("LOG", "ModelActivity: setSupportActionBar successful");
    
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        Log.d("LOG", "ModelActivity: setSectionsPagerAdapter successful");
    
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        Log.d("LOG", "ModelActivity: setTabLayout successful");
        
        //ModelViewFragment modelViewFragment = (ModelViewFragment) getFragmentManager().findFragmentById(R.id.model_view_fragment);
        //modelViewFragment.setModel(modelName);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("LOG", "ModelActivity: onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_model, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("LOG", "ModelActivity: onOptionsItemSelected");
        int id = item.getItemId();
        
        if (id == R.id.action_settings) {
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    ModelViewFragment modelViewFragment = new ModelViewFragment();
                    modelViewFragment.setModel(modelName);
                    return modelViewFragment;
                case 1:
                    BarGraphFragment barGraphFragment = new BarGraphFragment();
                    barGraphFragment.setModel(modelName);
                    return barGraphFragment;
            }
            return null;
        }
        
        @Override
        public int getCount() {
            return 2;
        }
        
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }
}