package com.dmitry_simakov.queue.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmitry_simakov.queue.ModelActivity;
import com.dmitry_simakov.queue.ModelsListAdapter;
import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.models.MM1;
import com.dmitry_simakov.queue.models.MMinf;
import com.dmitry_simakov.queue.models.Model;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment {
    
//    static final String[] modelsNames = {MM1.NAME, MMinf.NAME};
    private List<Model> models = new ArrayList<>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
    
        models.add(new MM1());
        models.add(new MMinf());
        
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ModelsListAdapter(models, getContext()));
        return v;
    }
}
