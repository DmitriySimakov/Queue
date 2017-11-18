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

import com.dmitry_simakov.queue.fragments.MM1_MMinf_CalculationFragment;
import com.dmitry_simakov.queue.fragments.MMV_CalculationFragment;
import com.dmitry_simakov.queue.fragments.ModelDescriptionFragment;

import static com.dmitry_simakov.queue.fragments.MainActivityFragment.MODELS;

public class ModelActivity extends AppCompatActivity {
    
    public static final String MODEL_ID = "MODEL_ID";
    private int id;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LOG", "ModelActivity: onCreate");
        setContentView(R.layout.activity_model);
        
        // Получаю id выбранной модели
        Intent intent = getIntent();
        id = intent.getIntExtra(MODEL_ID, 0);
    
        // Устанавливаю тулбар
        Toolbar toolbar = findViewById(R.id.model_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(MODELS[id].getName());
        }
    
        // Устанавливаю ViewPager
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    
        TabLayout tabLayout = findViewById(R.id.tabs);
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
            args.putInt(MODEL_ID, id);
            switch (position) {
                case 0:
                    ModelDescriptionFragment modelDescriptionFragment = new ModelDescriptionFragment();
                    modelDescriptionFragment.setArguments(args);
                    return modelDescriptionFragment;
                case 1:
                    switch (id) {
                        // MM1
                        case 0:
                        // MM∞
                        case 1:
                            MM1_MMinf_CalculationFragment modelCalculationFragment = new MM1_MMinf_CalculationFragment();
                            modelCalculationFragment.setArguments(args);
                            return modelCalculationFragment;
                        // MMV
                        case 2:
                            MMV_CalculationFragment mmvCalculationFragment = new MMV_CalculationFragment();
                            mmvCalculationFragment.setArguments(args);
                            return mmvCalculationFragment;
                        // MMVV
//                        case 3:
//                            MMVV_CalculationFragment mmvvCalculationFragment = new MMVV_CalculationFragment();
//                            mmvvCalculationFragment.setArguments(args);
//                            return mmvvCalculationFragment;
                    }
                    
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