package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMVV extends Model_V {
    
    private double gamma;
    private double j;
    
    public MMVV() {
        name = "M/M/v/K, K=v";
        description = "Очередь M/M/v/K представляет собой модель, в которой заявки обслуживаются " +
                "без ожидания, а максимальное число заявок в системе ограничено числом K, есть " +
                "v каналов и имеется экспоненциальное время обслуживания.\n" +
                "Практическим применением модели M/M/v/K является расчет приборов " +
                "разговорного тракта систем коммутации каналов.";
        modelImage = R.drawable.mmvv_model;
        modelImageHD = R.drawable.mmvv_model_hd;
        BDPImage = R.drawable.mmvv_bdp;
        BDPImageHD = R.drawable.mmvv_bdp_hd;
    
        conditionImage = R.drawable.model_v_condition;
        k_Formula = R.drawable.mmvv_k;
        t_Formula = R.drawable.mmvv_t;
        Pt_Formula = R.drawable.mmvv_pt;
        Pk_Formula = R.drawable.mmvv_pk;
    }
    
    public void setValues(double lambda, double mu, int V) throws ConditionException {
        if (lambda / (V * mu) >= 1) throw new ConditionException();
        super.setValues(lambda, mu, V);
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
