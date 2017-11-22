package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MM1 extends Model {
    
    public MM1() {
        name = "M/M/1";
        
        modelImage = R.drawable.mm1_model;
        modelImageHD = R.drawable.mm1_model_hd;
        BDPImage = R.drawable.mm1_bdp;
        BDPImageHD = R.drawable.mm1_bdp_hd;
        
        conditionImage = R.drawable.mm1_condition;
        k_Formula = R.drawable.mm1_k;
        t_Formula = R.drawable.mm1_t;
        Pk_Formula = R.drawable.mm1_pk;
    }
    
    @Override
    public void setValues(double lambda, double mu) throws ConditionException {
        if (lambda/mu >= 1) throw new ConditionException();
        super.setValues(lambda, mu);
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