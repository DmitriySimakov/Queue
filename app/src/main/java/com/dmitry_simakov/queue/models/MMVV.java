package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMVV extends ModelABV {
    
    private float gamma;
    private float j;
    
    public MMVV() {
        name = "M/M/V/V";
    
        modelImage = R.drawable.empty_image;
        BDPImage = R.drawable.empty_image;
    
        kFormula = R.drawable.empty_image;
        tFormula = R.drawable.empty_image;
        PtFormula = R.drawable.empty_image;
        pFormula = R.drawable.empty_image;
    }
    
    public String setValues(float lambda, float mu, int V) {
        if (lambda / (V * mu) < 1) {
            return super.setValues(lambda, mu, V);
        }
        return "Должно выполняться: λ/(V • μ) < 1";
    }
    
    @Override
    public void calculate() {
    
    }
}
