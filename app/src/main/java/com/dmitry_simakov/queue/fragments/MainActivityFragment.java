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
import com.dmitry_simakov.queue.models.MMVV;
import com.dmitry_simakov.queue.models.MMVVN;
import com.dmitry_simakov.queue.models.MMinf;
import com.dmitry_simakov.queue.models.Model;

public class MainActivityFragment extends Fragment {
    
    public static final Model MODELS[] = {new MM1(), new MMinf(), new MMV(), new MMVV(), new MMVVN()};
    
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
