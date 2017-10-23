package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMinf extends Model {
    
    public static final String NAME = "M/M/inf";
    
    public MMinf() {
        modelImage = R.drawable.m_m_inf__1;
        BDPImage = R.drawable.m_m_inf__2;
    }
    
    @Override
    public void getP(float[] P) {
        
    }
    
    @Override
    public float getK() {
        return 0;
    }
    
    @Override
    public float getT() {
        return 0;
    }
}