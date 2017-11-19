package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMVVN extends Model_V {
    
    private int N;
    
    private float alfa;
    private float A;
    
    private float Pb;
    private float PbFormula = R.drawable.empty_image;
    
    public MMVVN() {
        name = "M/M/V/V/N";
        
        modelImage = R.drawable.empty_image;
        BDPImage = R.drawable.empty_image;
    
        k_Formula = R.drawable.empty_image;
        t_Formula = R.drawable.empty_image;
        PtFormula = R.drawable.empty_image;
        pFormula = R.drawable.empty_image;
    }
    
    public String setValues(float A, float mu, int V, int N) {
        if (((N - V)/V) * (A/(1-A)) < 1) {
            this.A = A;
            this.mu = mu;
            this.V = V;
            this.N = N;
            alfa = (A * mu)/(1 - A);
            ro = ((N - V) * alfa)/(V * mu);
            return null;
        }
        return "Должно выполняться: ((N - V)/V) • (A/(1-A)) < 1";
    }
    
    @Override
    public void calculate() {
    
    }
}
