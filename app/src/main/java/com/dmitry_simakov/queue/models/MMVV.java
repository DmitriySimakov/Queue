package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMVV extends Model_V {
    
    private double gamma;
    private double j;
    
    public MMVV() {
        name = "M/M/V/V";
    
        modelImage = R.drawable.empty_image;
        BDPImage = R.drawable.empty_image;
    
        k_Formula = R.drawable.mmvv_k;
        t_Formula = R.drawable.mmvv_t;
        Pt_Formula = R.drawable.mmvv_pt;
        Pk_Formula = R.drawable.mmvv_pk;
    }
    
    public String setValues(double lambda, double mu, int V) {
        if (lambda / (V * mu) < 1) {
            return super.setValues(lambda, mu, V);
        }
        return "Должно выполняться: λ/(V • μ) < 1";
    }
    
    @Override
    public void calculate() {
        P = new double[V + 1];
        
        // Считаем P
        double RO_pow_X = 1; // ro^0
        double X_factorial = 1; // 0!
        P[0] = 1; // Считаю числитель P[0]
        double sum = 1; // (ro^0) / 0! = 1
        
        for (int x = 1; x <= V; x++) {
            RO_pow_X *= ro;
            X_factorial *= x;
            
            P[x] = RO_pow_X / X_factorial; // Считаю числитель и записываю в массив
            sum += P[x]; // Считаю сумму из знаменателя
        }
        for (int k = 0; k <= V; k++) {
            P[k] = P[k] / sum;
        }
    
        // Считаем Pt
        Pt = P[V];
        
        // Считаем k_
        k_ = 0;
        for (int k = 0; k <= V; k++) {
            k_ += k * P[k];
        }
    
        // Считаем t_
        t_ = k_ / lambda;
    }
}
