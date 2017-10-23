package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public abstract class Model {
    
    protected int modelImage = R.drawable.empty_image;
    protected int BDPImage = R.drawable.empty_image; // Birth–death process scheme
    
    protected float lambda;
    protected float mu;
    protected float ro = -1;
    
    
    public int getModelImage() {
        return modelImage;
    }
    
    public int getBDPImage() {
        return BDPImage;
    }
    
    public boolean setValues(float lambda, float mu) {
        this.lambda = lambda;
        this.mu = mu;
        ro = lambda/mu;
        return true;
    }
    
    abstract public void getP(float[] P);
    
    abstract public float getK();
    
    abstract public float getT();
}