package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMinf extends ModelAB {
    
    public MMinf() {
        name = "M/M/∞";
        
        modelImage = R.drawable.m_m_inf__1;
        BDPImage = R.drawable.m_m_inf__2;
    
        kFormula = R.drawable.empty_image;
        tFormula = R.drawable.empty_image;
        pFormula = R.drawable.empty_image;
    }
    
    @Override
    public void calculate() {
        // Считаем k
        k = lambda/mu;
    
        // Считаем t
        t = 1/mu;
    
        // Считаем P
        P = new float[11];
        int kFactorial = 1; // 10! = 3628800, max int = 2147483647
        for (int k = 0; k <= 10; k++) {
            if (k >= 2) kFactorial *= k;
            P[k] = (float) (Math.pow(ro, k)/kFactorial * Math.exp(-ro));
        }
    }
}