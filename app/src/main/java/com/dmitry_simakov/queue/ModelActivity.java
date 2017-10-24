package com.dmitry_simakov.queue;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;

import com.dmitry_simakov.queue.fragments.ModelCalculationFragment;
import com.dmitry_simakov.queue.fragments.ModelDescriptionFragment;
import com.dmitry_simakov.queue.models.Model;

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
        
        // Получаю имя выбранной модели
        Intent intent = getIntent();
        modelName = intent.getStringExtra(MODEL_NAME);
    
        // Устанавливаю тулбар
        Toolbar toolbar = (Toolbar) findViewById(R.id.model_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(modelName);
        }
    
        // Устанавливаю ViewPager
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
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
            Bundle args = new Bundle();
            args.putString(MODEL_NAME, modelName);
            switch (position) {
                case 0:
                    ModelDescriptionFragment modelDescriptionFragment = new ModelDescriptionFragment();
                    modelDescriptionFragment.setArguments(args);
                    return modelDescriptionFragment;
                case 1:
                    ModelCalculationFragment modelCalculationFragment = new ModelCalculationFragment();
                    modelCalculationFragment.setArguments(args);
                    return modelCalculationFragment;
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
                    return getString(R.string.description);
                case 1:
                    return getString(R.string.calculation);
            }
            return null;
        }
    }
}