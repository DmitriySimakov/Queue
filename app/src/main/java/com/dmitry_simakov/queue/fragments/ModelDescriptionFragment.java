package com.dmitry_simakov.queue.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dmitry_simakov.queue.ModelActivity;
import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.models.ModelAB;

import static com.dmitry_simakov.queue.fragments.MainActivityFragment.MODELS;

public class ModelDescriptionFragment extends Fragment {
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("LOG", "ModelDescriptionFragment: onCreateView");
        View v = inflater.inflate(R.layout.fragment_model_description, container, false);
    
        int id = 0;
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt(ModelActivity.MODEL_ID);
        }
        
        ModelAB model = MODELS[id];
    
        ImageView modelImageView = v.findViewById(R.id.modelImageView);
        modelImageView.setImageResource(model.getModelImage());
    
        ImageView RDDImageView = v.findViewById(R.id.BDPImageView);
        RDDImageView.setImageResource(model.getBDPImage());
        
        return v;
    }
}