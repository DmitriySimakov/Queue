package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMVVN extends Model_V {
    
    private int N;
    
    private double alfa;
    private double A;
    
    private double Pb;
    private int PbFormula;
    
    public MMVVN() {
        name = "M/M/v/K/N, K=v";
        description = "Очередь M/M/v/K/N представляет собой систему, в которой интенсивность " +
                "поступления вызовов и время обслуживания подчиняются экспоненциальному " +
                "распределению, и все заявки формируют единую очередь. Система имеет явные " +
                "потери, т.е. K заявок обслуживаются без ожидания, а остальные теряются, при " +
                "этом количество поступающих заявок не превышает N.\n" +
                "Практическим применением модели M/M/v/K/N является расчет приборов разговорного " +
                "тракта, на которые поступает поток вызовов от ограниченного числа источников " +
                "(100 и менее). В частности, эта модель используется при расчете блоков АВ " +
                "ступени АИ АТСК и при расчете учрежденческих АТС малой емкости.";
        modelImage = R.drawable.mmvvn_model;
        modelImageHD = R.drawable.mmvvn_model_hd;
        BDPImage = R.drawable.mmvvn_bdp;
        BDPImageHD = R.drawable.mmvvn_bdp_hd;
    
        conditionImage = R.drawable.mmvvn_condition;
        k_Formula = R.drawable.mmvvn_k;
        t_Formula = R.drawable.mmvvn_t;
        Pt_Formula = R.drawable.mmvvn_pt;
        PbFormula = R.drawable.mmvvn_pb;
        Pk_Formula = R.drawable.mmvvn_pk;
    }
    
    public void setValues(double A, double mu, int V, int N) throws ConditionException {
        alfa = (A * mu) / (1 - A);
        if (((N - V) * alfa) / (V * mu) >= 1) throw new ConditionException();
        this.A = A;
        this.mu = mu;
        this.V = V;
        this.N = N;
    }
    
    @Override
    public void calculate() {
        P = new double[V + 1];
        double n = N; // Чтобы не кастовать
    
        // Считаем промежуточные значения
        double combination = 1; // Сочетание из N по 0
        double part = A/(1-A);
        double PART_pow_X = 1; // дробь в степени 0
        P[0] = 1; // Считаю числитель P[0]
        double sum1 = P[0];
        double sum2 = P[0];
        
        for (int x = 1; x <= V; x++) {
            combination *= (n - x + 1) / x; // C(N)(k) = C(N)(k) * (N-k+1)/k
            PART_pow_X *= part;
            
            P[x] = combination * PART_pow_X;
            sum1 += P[x];
            sum2 += P[x] * (n - x) / n; // C(N-1)(k) = C(N)(k) * (N-k)/N
        }
    
        // Считаем Pb
        Pb = (P[V] * (n - V) / n) / sum2;
    
        // Считаем P и k_
        combination = 1; // Сочетание из N по 0
        PART_pow_X = 1; // дробь в степени 0
        k_ = 0;
        
        for (int k = 0; k <= V; k++) {
            P[k] /= sum1;
            k_ += k * P[k];
        }
    
        // Считаем Pt
        Pt = P[V];
    
        // Считаем t_
        t_ = k_ / ((n - k_) * alfa);
    }
    
    public double getPb() {
        return Pb;
    }
    public int getPb_Formula() {
        return PbFormula;
    }
    public String getPb_Description() { return "Вероятность потерь по вызовам"; }
}
