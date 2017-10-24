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
import com.dmitry_simakov.queue.models.Model;

public class ModelDescriptionFragment extends Fragment {
    
    private Model model;
    private String modelName;
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("LOG", "ModelDescriptionFragment: onCreateView");
        View v = inflater.inflate(R.layout.fragment_model_description, container, false);
        
        Bundle bundle = getArguments();
        if (bundle != null) {
            modelName = bundle.getString(ModelActivity.MODEL_NAME);
        }
        
        return v;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        Log.d("LOG", "ModelDescriptionFragment: onStart");

        // Загружаю класс модели по имени
        String className = modelName.replace("/", "");
        String fullClassName = "com.dmitry_simakov.queue.models." + className;
        try {
            model = (Model) Class.forName(fullClassName).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        
        View view = getView();
        if (view != null) {
            Log.d("LOG", "view!=null");
            ImageView modelImageView = view.findViewById(R.id.modelImageView);
            modelImageView.setImageResource(model.getModelImage());
            
            ImageView RDDImageView = view.findViewById(R.id.BDPImageView);
            RDDImageView.setImageResource(model.getBDPImage());
        }
    }
}