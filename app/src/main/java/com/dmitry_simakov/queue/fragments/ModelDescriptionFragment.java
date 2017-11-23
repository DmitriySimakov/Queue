package com.dmitry_simakov.queue.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dmitry_simakov.queue.ImageViewDialog;
import com.dmitry_simakov.queue.ModelActivity;
import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.models.Model;

import static com.dmitry_simakov.queue.fragments.MainActivityFragment.MODELS;

public class ModelDescriptionFragment extends Fragment implements View.OnClickListener {
    
    Model model;
    
    ImageView modelImageView;
    ImageView BDPImageView;
    
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
        
        model = MODELS[id];
    
        modelImageView = v.findViewById(R.id.modelImageView);
        modelImageView.setImageResource(model.getModelImage());
        modelImageView.setOnClickListener(this);
    
        BDPImageView = v.findViewById(R.id.BDPImageView);
        BDPImageView.setImageResource(model.getBDPImage());
        BDPImageView.setOnClickListener(this);
        
        return v;
    }
    
    @Override
    public void onClick(View view) {
        Log.d("LOG", "ModelDescriptionFragment: onClick");
        switch (view.getId()) {
            case R.id.modelImageView:
                ImageViewDialog.createDialog(model.getModelImageHD(), "Схема модели", getActivity());
                break;
            case R.id.BDPImageView:
                ImageViewDialog.createDialog(model.getBDPImageHD(), "Диаграмма процесса рождения-смерти", getActivity());
                break;
        }
    }
}