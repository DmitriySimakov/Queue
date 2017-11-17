package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MM1 extends Model {
    
    public static final String NAME = "M/M/1";
    
    public MM1() {
        name = NAME;
        
        modelImage = R.drawable.m_m_1__1;
        BDPImage = R.drawable.m_m_1__2;
        
        kFormula = R.drawable.mm1_k;
        tFormula = R.drawable.mm1_t;
        pFormula = R.drawable.mm1_p;
    }
    
    public String setValues(float lambda, float mu) {
        if (lambda/mu < 1) {
            return super.setValues(lambda, mu);
        }
        return "Для модели M/M/1 должно выполняться:\nlambda < mu";
    }
    
    @Override
    public void getP(float[] P) {
        for (int k = 0; k < P.length; k++) {
            P[k] = (1 - ro) * (float) Math.pow(ro, k);
        }
    }
    
    @Override
    public float getK() {
        if (ro == -1) {
            return -1;
        }
        return ro/(1-ro);
    }
    
    @Override
    public float getT() {
        if (ro == -1) {
            return -1;
        }
        return (1/mu)/(1 - ro);
    }
}