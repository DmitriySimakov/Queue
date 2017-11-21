package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMinf extends Model {
    
    public MMinf() {
        name = "M/M/∞";
        
        modelImage = R.drawable.mminf_model;
        modelImageHD = R.drawable.mminf_model_hd;
        BDPImage = R.drawable.mminf_bdp;
        BDPImageHD = R.drawable.mminf_bdp_hd;
    
        k_Formula = R.drawable.mminf_k;
        t_Formula = R.drawable.mminf_t;
        Pk_Formula = R.drawable.mminf_pk;
    }
    
    @Override
    public void calculate() {
        P = new double[11];
    
        // Считаем P
        double RO_pow_K = 1; // ro^0
        int K_factorial = 1; // 0!
        P[0] = Math.exp(-ro); // Считаю P[0]
        
        for (int k = 1; k <= 10; k++) {
            RO_pow_K *= ro;
            K_factorial *= k;
            
            P[k] = RO_pow_K / K_factorial * Math.exp(-ro);
        }
        
        // Считаем k_
        k_ = lambda/mu;
    
        // Считаем t_
        t_ = 1/mu;
    }
}