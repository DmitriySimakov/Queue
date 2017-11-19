package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMVV extends Model_V {
    
    private float gamma;
    private float j;
    
    public MMVV() {
        name = "M/M/V/V";
    
        modelImage = R.drawable.empty_image;
        BDPImage = R.drawable.empty_image;
    
        k_Formula = R.drawable.empty_image;
        t_Formula = R.drawable.empty_image;
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
        // Считаем P
        P = new float[V+1];
        float sum = 0;
        double factorialX = 1;
        for (int x = 0; x <= V; x++) {
            if (x >= 2) factorialX *= x;
            P[x] = (float) (Math.pow(ro, x)/factorialX);
            sum += P[x];
        }
        for (int k = 0; k <= V; k++) {
            P[k] = P[k]/sum;
        }
    
        // Считаем Pt
        Pt = P[V];
        
        // Считаем k_
        k_ = 0;
        for (int i = 1; i <= V; i++) {
            k_ += i * P[i];
        }
    
        // Считаем t_
        t_ = k_ /lambda;
    }
}
