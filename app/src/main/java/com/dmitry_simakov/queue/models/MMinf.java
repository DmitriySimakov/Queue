package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMinf extends Model {
    
    public MMinf() {
        name = "M/M/∞";
        
        modelImage = R.drawable.m_m_inf__1;
        BDPImage = R.drawable.m_m_inf__2;
    
        k_Formula = R.drawable.empty_image;
        t_Formula = R.drawable.empty_image;
        pFormula = R.drawable.empty_image;
    }
    
    @Override
    public void calculate() {
        // Считаем k_
        k_ = lambda/mu;
    
        // Считаем t_
        t_ = 1/mu;
    
        // Считаем P
        P = new float[11];
        int factorialK = 1; // 10! = 3628800, max int = 2147483647
        for (int k = 0; k <= 10; k++) {
            if (k >= 2) factorialK *= k;
            P[k] = (float) (Math.pow(ro, k)/factorialK * Math.exp(-ro));
        }
    }
}