package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMV extends Model_V {
    
    public MMV() {
        name = "M/M/v";
        description = "Очередь M/M/v представляет собой систему, в которой интенсивность " +
                "поступления вызовов и время обслуживания подчиняются экспоненциальному " +
                "распределению, и все заявки формируют единую очередь. Система имеет v каналов.\n" +
                "Практическим применением модели M/M/v является расчет систем коммутации " +
                "сообщений и управляющих устройств систем коммутации каналов. В некоторых " +
                "системах машинных АТС по системе с ожиданием работают устройства разговорного " +
                "тракта. По системе с ожиданием работают междугородные каналы при ручном " +
                "обслуживании.";
        modelImage = R.drawable.mmv_model;
        modelImageHD = R.drawable.mmv_model_hd;
        BDPImage = R.drawable.mmv_bdp;
        BDPImageHD = R.drawable.mmv_bdp_hd;
    
        conditionImage = R.drawable.model_v_condition;
        k_Formula = R.drawable.mmv_gamma;
        t_Formula = R.drawable.mmv_j;
        Pt_Formula = R.drawable.mmv_pt;
        Pk_Formula = R.drawable.mmv_pk;
    }
    
    public void setValues(double lambda, double mu, int V) throws ConditionException {
        if (lambda / (V * mu) >= 1) throw new ConditionException();
        super.setValues(lambda, mu, V);
    }
    
    @Override
    public void calculate() {
        P = new double[V + 6];
        
        // Считаем промежуточные значения
        double RO_pow_X = 1; // ro^0
        double X_factorial = 1; // 0!
        P[0] = 1; // Считаю числитель P[0]
        double sum = 1; // (ro^0) / 0! = 1
        
        for (int x = 1; x <= V - 1; x++) {
            RO_pow_X *= ro;
            X_factorial *= x;
    
            P[x] = RO_pow_X / X_factorial; // Считаю числитель и записываю в массив
            sum += P[x]; // Считаю сумму из знаменателя
        }
        // Считаю значение в последней точке
        RO_pow_X *= ro;
        X_factorial *= V;
        P[V] = RO_pow_X / X_factorial;
        
        double part2 = P[V] * V / (V - ro); // Считаю вторую часть знаменателя
        double denominator = sum + part2;
    
        // Считаем P
        // Левая часть графика
        for (int k = 0; k <= V; k++) {
            P[k] = P[k] / denominator;
        }
        // Правая часть графика
        double RO_div_V = ro / V;
        double RO_div_V_pow_J = 1; // (ro / V)^0
        
        for (int j = 1; j <= 5; j++) {
            RO_div_V_pow_J *= RO_div_V;
            
            P[V + j] = P[V] * RO_div_V_pow_J;
        }
        
        // Считаем Pt
        Pt = part2 / denominator;
    
        // Считаем γ_
        k_ = 1 / (mu * (V - ro));
    
        // Считаем j_
        t_ = lambda * k_;
    }
    
    @Override
    public String getK_Description() { return "Среднее время ожидания начала обслуживания"; }
    
    @Override
    public String getT_Description() { return "Среднее число требований в очереди"; }
}
