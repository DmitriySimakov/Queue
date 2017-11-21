package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public abstract class Model {
    
    protected String name = "Model Name";
    
    protected int modelImage = R.drawable.empty_image;
    protected int BDPImage = R.drawable.empty_image; // Birth–death process scheme
    
    protected double lambda;
    protected double mu;
    protected double ro = -1;
    
    protected double P[];
    protected int pFormula = R.drawable.empty_image;
    
    protected double k_;
    protected int k_Formula = R.drawable.empty_image;
    
    protected double t_;
    protected int t_Formula = R.drawable.empty_image;
    
    public String setValues(double lambda, double mu) {
        this.lambda = lambda;
        this.mu = mu;
        ro = lambda / mu;
        return null;
    }
    
    public abstract void calculate();
    
    public String getName() { return name; }
    
    public int getModelImage() { return modelImage; }
    
    public int getBDPImage() { return BDPImage; }
    
    public double[] getP() { return  P; }
    public int getPFormula() { return pFormula; }
    
    public double getK_() { return k_; }
    public int getK_Formula() { return k_Formula; }
    
    public double getT_() { return t_; }
    public int getT_Formula() { return t_Formula; }
}