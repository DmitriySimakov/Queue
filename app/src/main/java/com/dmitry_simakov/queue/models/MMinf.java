package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

import java.math.BigInteger;

public class MMinf extends Model {
    
    public static final String NAME = "M/M/inf";
    
    public MMinf() {
        modelImage = R.drawable.m_m_inf__1;
        BDPImage = R.drawable.m_m_inf__2;
    }
    
    @Override
    public void getP(float[] P) {
        int kFactorial = 1; // 10! = 3628800, max int = 2147483647
        for (int k = 0; k < P.length; k++) {
            if (k >= 2) {
                kFactorial *= k;
            }
            P[k] = (float) Math.pow(ro, k)/kFactorial * (float) Math.exp(-ro);
        }
    }
    
    @Override
    public float getK() {
        if (ro == -1) {
            return -1;
        }
        return lambda/mu;
    }
    
    @Override
    public float getT() {
        if (ro == -1) {
            return -1;
        }
        return 1/mu;
    }
}