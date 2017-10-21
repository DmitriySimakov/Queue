package com.dmitry_simakov.queue.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.models.Model;

public class ModelViewFragment extends Fragment {
    
    public static final String MODEL_NAME = "MODEL_NAME";
    
    private Model model;
    private String modelName;
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("LOG", "ModelViewFragment: onCreate");
        if (savedInstanceState != null) {
            modelName = savedInstanceState.getString(MODEL_NAME);
        }
    }
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("LOG", "ModelViewFragment: onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_model_view, container, false);
        return rootView;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        Log.d("LOG", "ModelViewFragment: onStart");
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
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(MODEL_NAME, modelName);
    }
    
    public void setModel(String modelName) {
        this.modelName = modelName;
    }
}