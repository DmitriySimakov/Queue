package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public abstract class ModelABV extends ModelAB {
    
    protected int V;
    protected float Pt;
    
    protected int PtFormula = R.drawable.empty_image;
    
    public String setValues(float lambda, float mu, int V) {
        super.setValues(lambda, mu);
        this.V = V;
        ro = ro / V;
        return null;
    }
    
    public float getPt() { return  Pt; };
    public int getPtFormula() { return PtFormula; }
}
