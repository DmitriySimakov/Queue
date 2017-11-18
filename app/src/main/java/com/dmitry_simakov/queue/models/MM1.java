package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MM1 extends ModelAB {
    
    public MM1() {
        name = "M/M/1";
        
        modelImage = R.drawable.m_m_1__1;
        BDPImage = R.drawable.m_m_1__2;
        
        kFormula = R.drawable.mm1_k;
        tFormula = R.drawable.mm1_t;
        pFormula = R.drawable.mm1_p;
    }
    
    @Override
    public String setValues(float lambda, float mu) {
        if (lambda/mu < 1) {
            return super.setValues(lambda, mu);
        }
        return "Должно выполняться: λ/μ < 1";
    }
    
    @Override
    public void calculate() {
        // Считаем k
        k = ro/(1-ro);
        
        // Считаем t
        t = (1/mu)/(1 - ro);
        
        // Считаем P
        P = new float[11];
        for (int k = 0; k <= 10; k++) {
            P[k] = (1 - ro) * (float) Math.pow(ro, k);
        }
    }
}