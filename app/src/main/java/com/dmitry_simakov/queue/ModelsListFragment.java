package com.dmitry_simakov.queue;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ModelsListFragment extends ListFragment {
    
    static interface ModelsListListener {
        void itemClicked(int id);
    };
    
    private ModelsListListener listener;
    
    static final String[] modelsNames = {MM1.NAME, MMinf.NAME};
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1, modelsNames);
        setListAdapter(adapter);
        
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ModelsListListener) activity;
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.itemClicked((int)id);
        }
    }
}