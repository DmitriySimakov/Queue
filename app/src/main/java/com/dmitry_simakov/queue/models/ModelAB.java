package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public abstract class ModelAB {
    
    protected String name = "Model Name";
    
    protected int modelImage = R.drawable.empty_image;
    protected int BDPImage = R.drawable.empty_image; // Birth–death process scheme
    
    protected float lambda;
    protected float mu;
    protected float ro = -1;
    
    protected float k;
    protected float t;
    protected float P[];
    
    protected int kFormula = R.drawable.empty_image;
    protected int tFormula = R.drawable.empty_image;
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
    
    public float getK() { return k; }
    public int getKFormula() { return kFormula; }
    
    public float getT() { return t; }
    public int getTFormula() { return tFormula; }
    
    public float[] getP() { return  P; }
    public int getPFormula() { return pFormula; }
}