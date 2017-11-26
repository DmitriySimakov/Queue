package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

import java.util.HashMap;
import java.util.Map;

public abstract class Model {
    
    protected String name = "Model Name";
    protected String description = "Model Description";
    protected int modelImage = R.drawable.empty_image;
    protected int modelImageHD = R.drawable.empty_image;
    protected int BDPImage = R.drawable.empty_image; // Диаграмма размножения/гибели
    protected int BDPImageHD = R.drawable.empty_image;
    protected int conditionImage = R.drawable.empty_image;
    
    protected boolean hasQueue = true;
    
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
    public String getDescription() { return description; }
    public int getModelImage() { return modelImage; }
    public int getModelImageHD() { return modelImageHD; }
    
    public int getBDPImage() { return BDPImage; }
    public int getBDPImageHD() { return BDPImageHD; }
    
    public boolean hasQueue() { return hasQueue; }
    
    public double[] getP() { return  P; }
    public int getPk_Formula() { return Pk_Formula; }
    public String getPk_Description() { return "Вероятность наличия в системе ровно k вызовов"; }
    
    public double getK_() { return k_; }
    public int getK_Formula() { return k_Formula; }
    public String getK_Description() { return "Среднее число вызовов в системе"; }
    
    public double getT_() { return t_; }
    public int getT_Formula() { return t_Formula; }
    public String getT_Description() { return "Среднее время прибывания в системе"; }
    
    public double getRo() { return ro; }
    
    public Map<String, String> getInputParameters() {
        Map<String, String> map = new HashMap<>();
        map.put("λ", Double.toString(lambda));
        map.put("μ", Double.toString(mu));
        return map;
    }
    
    public Map<String, String> getOutputParameters() {
        Map<String, String> map = new HashMap<>();
        map.put("k_", Double.toString(k_));
        map.put("t_", Double.toString(t_));
        return map;
    }
    
    public class ConditionException extends Exception {
        public int getConditionImage() {
            return conditionImage;
        }
    }
}