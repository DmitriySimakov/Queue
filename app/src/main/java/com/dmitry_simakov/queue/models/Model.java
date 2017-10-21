package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public abstract class Model {
    
    protected int modelImage = R.drawable.empty_image;
    protected int BDPImage = R.drawable.empty_image; // Birth–death process scheme
    
    public int getModelImage() {
        return modelImage;
    }
    
    public int getBDPImage() {
        return BDPImage;
    }
}