package com.dmitry_simakov.queue.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmitry_simakov.queue.ModelsListAdapter;
import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.models.MM1;
import com.dmitry_simakov.queue.models.MMV;
import com.dmitry_simakov.queue.models.MMinf;
import com.dmitry_simakov.queue.models.ModelAB;

public class MainActivityFragment extends Fragment {
    
    public static final ModelAB MODELS[] = {new MM1(), new MMinf(), new MMV()};
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ModelsListAdapter(getContext()));
        return v;
    }
}
