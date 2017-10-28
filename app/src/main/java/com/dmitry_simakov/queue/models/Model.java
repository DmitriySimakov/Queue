package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public abstract class Model {
    
    protected int modelImage = R.drawable.empty_image;
    protected int BDPImage = R.drawable.empty_image; // Birth–death process scheme
    
    protected int kFormula = R.drawable.empty_image;
    protected int tFormula = R.drawable.empty_image;
    protected int pFormula = R.drawable.empty_image;
    
    protected float lambda;
    protected float mu;
    protected float ro = -1;
    
    public static Model getModelByName(String name) {
        String className = name.replace("/", "");
        String fullClassName = "com.dmitry_simakov.queue.models." + className;
        try {
            return (Model) Class.forName(fullClassName).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getModelImage() {
        return modelImage;
    }
    
    public int getBDPImage() {
        return BDPImage;
    }
    
    public int getKFormula() {
        return kFormula;
    }
    
    public int getTFormula() {
        return tFormula;
    }
    
    public int getPFormula() {
        return pFormula;
    }
    
    public String setValues(float lambda, float mu) {
        if ((lambda > 0) && (mu > 0)) {
            this.lambda = lambda;
            this.mu = mu;
            ro = lambda / mu;
            return null;
        }
        return "lambda и mu должны быть больше 0";
    }
    
    abstract public void getP(float[] P);
    
    abstract public float getK();
    
    abstract public float getT();
}