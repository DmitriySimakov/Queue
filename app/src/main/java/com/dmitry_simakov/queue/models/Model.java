package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public abstract class Model {
    
    protected String name = "Model Name";
    
    protected int modelImage = R.drawable.empty_image;
    protected int BDPImage = R.drawable.empty_image; // Birth–death process scheme
    
    protected float lambda;
    protected float mu;
    protected float ro = -1;
    
    protected float k_;
    protected float t_;
    protected float P[];
    
    protected int k_Formula = R.drawable.empty_image;
    protected int t_Formula = R.drawable.empty_image;
    protected int pFormula = R.drawable.empty_image;
    
    public String setValues(float lambda, float mu) {
        this.lambda = lambda;
        this.mu = mu;
        ro = lambda / mu;
        return null;
    }
    
    public abstract void calculate();
    
    public String getName() { return name; }
    
    public int getModelImage() { return modelImage; }
    
    public int getBDPImage() { return BDPImage; }
    
    public float getK_() { return k_; }
    public int getK_Formula() { return k_Formula; }
    
    public float getT_() { return t_; }
    public int getT_Formula() { return t_Formula; }
    
    public float[] getP() { return  P; }
    public int getPFormula() { return pFormula; }
}