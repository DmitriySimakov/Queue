package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public abstract class Model_V extends Model {
    
    protected int V;
    
    protected double Pt;
    protected int Pt_Formula = R.drawable.empty_image;
    
    public void setValues(double lambda, double mu, int V) throws ConditionException {
        super.setValues(lambda, mu);
        this.V = V;
    }
    
    public double getPt() { return  Pt; };
    public int getPt_Formula() { return Pt_Formula; }
}
