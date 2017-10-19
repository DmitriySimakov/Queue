package com.dmitry_simakov.queue;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class ModelViewFragment extends Fragment implements View.OnClickListener {
    
    public static final String MODEL_NAME_PREF = "MODEL_NAME";
    
    private Model model;
    private String modelName;
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("LOG", "ModelViewFragment: onCreate");
        if (savedInstanceState != null) {
            modelName = savedInstanceState.getString(MODEL_NAME_PREF);
        }
    }
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        return inflater.inflate(R.layout.fragment_model_view, container, false);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        Log.d("LOG", "ModelViewFragment: onCreateView");
        String className = modelName.replace("/", "");
        String fullClassName = "com.dmitry_simakov.queue." + className;
        Log.d("LOG", "BEFORE TRY");
        try {
            model = (Model) Class.forName(fullClassName).newInstance();
            Log.d("LOG", "TRY");
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
            
            ImageView RDDImageView = view.findViewById(R.id.RDDImageView);
            RDDImageView.setImageResource(model.getRDDImage());
            
            Button button = view.findViewById(R.id.startButton);
            button.setOnClickListener(this);
        }
    }
    
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ModelViewFragment.this.getActivity(), GraphActivity.class);
        startActivity(intent);
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(MODEL_NAME_PREF, modelName);
    }
    
    public void setModel(String modelName) {
        this.modelName = modelName;
    }
}