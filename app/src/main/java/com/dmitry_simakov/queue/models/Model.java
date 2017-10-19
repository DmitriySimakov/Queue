package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public abstract class Model {
    
    protected int modelImage = R.drawable.empty_image;
    protected int RDDImage = R.drawable.empty_image; // Reproducing/death diagram
    
    public int getModelImage() {
        return modelImage;
    }
    
    public int getRDDImage() {
        return RDDImage;
    }
}