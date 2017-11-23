package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public abstract class Model {
    
    protected String name = "Model Name";
    
    protected int modelImage = R.drawable.empty_image;
    protected int modelImageHD = R.drawable.empty_image;
    protected int BDPImage = R.drawable.empty_image; // Birth–death process scheme
    protected int BDPImageHD = R.drawable.empty_image;
    protected int conditionImage = R.drawable.empty_image;
    
    protected double lambda;
    protected double mu;
    protected double ro = -1;
    
    protected double P[];
    protected int Pk_Formula = R.drawable.empty_image;
    
    protected double k_;
    protected int k_Formula = R.drawable.empty_image;
    
    protected double t_;
    protected int t_Formula = R.drawable.empty_image;
    
    public void setValues(double lambda, double mu) throws ConditionException {
        this.lambda = lambda;
        this.mu = mu;
        ro = lambda / mu;
    }
    
    public abstract void calculate();
    
    public String getName() { return name; }
    
    public int getModelImage() { return modelImage; }
    public int getModelImageHD() { return modelImageHD; }
    
    public int getBDPImage() { return BDPImage; }
    public int getBDPImageHD() { return BDPImageHD; }
    
    public double[] getP() { return  P; }
    public int getPk_Formula() { return Pk_Formula; }
    public String getPk_Description() { return "Вероятность наличия в системе ровно k вызовов"; }
    
    public double getK_() { return k_; }
    public int getK_Formula() { return k_Formula; }
    public String getK_Description() { return "Среднее число вызовов в системе"; }
    
    public double getT_() { return t_; }
    public int getT_Formula() { return t_Formula; }
    public String getT_Description() { return "Среднее время прибывания в системе"; }
    
    public class ConditionException extends Exception {
        public int getConditionImage() {
            return conditionImage;
        }
    }
}