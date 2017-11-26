package com.dmitry_simakov.queue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.dmitry_simakov.queue.fragments.calculaton.MMVVN_CalculationFragment;
import com.dmitry_simakov.queue.fragments.calculaton.Model_CalculationFragment;
import com.dmitry_simakov.queue.fragments.calculaton.model_v.MMV_CalculationFragment;
import com.dmitry_simakov.queue.fragments.ModelDescriptionFragment;
import com.dmitry_simakov.queue.fragments.calculaton.model_v.Model_V_CalculationFragment;
import com.dmitry_simakov.queue.models.Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;

import static com.dmitry_simakov.queue.fragments.MainActivityFragment.MODELS;

public class ModelActivity extends AppCompatActivity {
    
    Model model;
    public static final String MODEL_ID = "MODEL_ID";
    private int id;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_model);
        
        // Получаю id выбранной модели
        Intent intent = getIntent();
        id = intent.getIntExtra(MODEL_ID, 0);
        model = MODELS[id];
    
        // Устанавливаю тулбар
        Toolbar toolbar = findViewById(R.id.model_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(model.getName());
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
        getMenuInflater().inflate(R.menu.menu_model, menu);
        return true;
    }
    
    private String filename = "";
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
                
            case R.id.export:
                if (model.getRo() == -1) {
                    Toast.makeText(this, "Нечего экспортировать. Проведите рассчёт.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Экспорт");
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("Имя файла");
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        filename = input.getText().toString();
                        if (!filename.equals("")) export();
                    }
                });
                builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            case R.id.conventions:
                AlertDialog.Builder builder1;
                builder1 = new AlertDialog.Builder(this);
                builder1.setTitle(R.string.conventions);
                builder1.setItems(R.array.conventionsArray, null);
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
                return true;
            case R.id.about:
                AlertDialog.Builder builder2;
                builder2 = new AlertDialog.Builder(this);
                builder2.setTitle(R.string.about);
                builder2.setMessage(R.string.aboutMessage);
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void export() {
        String path = getExternalFilesDir(null).getPath();
        File file = new File(path, filename + ".csv");
        try {
            OutputStream out = new FileOutputStream(file);
            out.write("Входные параметры:\n".getBytes());
            Map<String, String> map = model.getInputParameters();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                out.write((entry.getKey() + ';').getBytes());
                out.write((entry.getValue() + '\n').getBytes());
            }
            out.write("Выходные параметры:\n".getBytes());
            map = model.getOutputParameters();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                out.write((entry.getKey() + ';').getBytes());
                out.write((entry.getValue() + '\n').getBytes());
            }
            double[] P = model.getP();
            for (int i = 0; i < P.length; i++) {
                out.write(("P[" + i +"];").getBytes());
                out.write((Double.toString(P[i]) + '\n').getBytes());
            }
            out.close();
            Toast.makeText(this, "Данные успешно экспортированы в файл: " + path, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Экспорт не удался", Toast.LENGTH_SHORT).show();
        }
    }
    
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        
        SectionsPagerAdapter(FragmentManager fm) {
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
                            Model_CalculationFragment modelCalculationFragment = new Model_CalculationFragment();
                            modelCalculationFragment.setArguments(args);
                            return modelCalculationFragment;
                        // MMV
                        case 2:
                            MMV_CalculationFragment mmvCalculationFragment = new MMV_CalculationFragment();
                            mmvCalculationFragment.setArguments(args);
                            return mmvCalculationFragment;
                        // MMVV
                        case 3:
                            Model_V_CalculationFragment mmvvCalculationFragment = new Model_V_CalculationFragment();
                            mmvvCalculationFragment.setArguments(args);
                            return mmvvCalculationFragment;
                        // MMVVN
                        case 4:
                            MMVVN_CalculationFragment mmvvnCalculationFragment = new MMVVN_CalculationFragment();
                            mmvvnCalculationFragment.setArguments(args);
                            return mmvvnCalculationFragment;
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