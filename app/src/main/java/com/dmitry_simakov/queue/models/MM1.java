package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MM1 extends Model {
    
    public MM1() {
        name = "M/M/1";
        
        modelImage = R.drawable.mm1_model;
        modelImageHD = R.drawable.mm1_model_hd;
        BDPImage = R.drawable.mm1_bdp;
        BDPImageHD = R.drawable.mm1_bdp_hd;
        
        k_Formula = R.drawable.mm1_k;
        t_Formula = R.drawable.mm1_t;
        Pk_Formula = R.drawable.mm1_pk;
    }
    
    @Override
    public String setValues(double lambda, double mu) {
        if (lambda/mu < 1) {
            return super.setValues(lambda, mu);
        }
        return "Должно выполняться: λ/μ < 1";
    }
    
    @Override
    public void calculate() {
        P = new double[11];
    
        // Считаем P
        double RO_pow_K = 1; // ro^0
        P[0] = (1 - ro); // Считаю P[0]
        
        for (int k = 1; k <= 10; k++) {
            RO_pow_K *= ro;
            
            P[k] = (1 - ro) * RO_pow_K;
        }
        
        // Считаем k_
        k_ = ro / (1 - ro);
    
        // Считаем t_
        t_ = (1 / mu) / (1 - ro);
    }
}