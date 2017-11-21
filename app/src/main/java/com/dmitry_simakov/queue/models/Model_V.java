package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public abstract class Model_V extends Model {
    
    protected int V;
    
    protected double Pt;
    protected int PtFormula = R.drawable.empty_image;
    
    public String setValues(double lambda, double mu, int V) {
        super.setValues(lambda, mu);
        this.V = V;
        return null;
    }
    
    public double getPt() { return  Pt; };
    public int getPt_Formula() { return PtFormula; }
}
