package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MMVVN extends Model_V {
    
    private int N;
    
    private float alfa;
    private float A;
    
    private float Pb;
    private float PbFormula = R.drawable.empty_image;
    
    public MMVVN() {
        name = "M/M/V/V/N";
        
        modelImage = R.drawable.empty_image;
        BDPImage = R.drawable.empty_image;
    
        k_Formula = R.drawable.empty_image;
        t_Formula = R.drawable.empty_image;
        PtFormula = R.drawable.empty_image;
        pFormula = R.drawable.empty_image;
    }
    
    public String setValues(float A, float mu, int V, int N) {
        if (A >= 1) return "A должно быть < 1";
        if (((N - V) * A)/(V * mu) < 1) {
            this.A = A;
            this.mu = mu;
            this.V = V;
            this.N = N;
            alfa = (A * mu)/(1 - A);
            return null;
        }
        return "Должно выполняться: ((N - V) • A)/(V • μ) < 1";
    }
    
    @Override
    public void calculate() {
        // Считаем промежуточные значения
        P = new float[V + 1];
        float n = N;
        float drob = A/(1-A);
        BigDecimal drobK = BigDecimal.valueOf(1); // дробь в степени 0
        BigDecimal sochet = BigDecimal.valueOf(1); // Сочетание из N по 0
        BigDecimal num = sochet.multiply(drobK);
        BigDecimal sum1 = BigDecimal.valueOf(0);
        BigDecimal sum2 = BigDecimal.valueOf(0);
        
        for (int x = 0; x < V; x++) {
            sum1 = sum1.add(num);
            sum2 = sum2.add(num.multiply(BigDecimal.valueOf((n - x)/n))).setScale(10, BigDecimal.ROUND_HALF_UP);; // C(N-1)(k) = C(N)(k) * (N-k)/N
            
            sochet = sochet.multiply(BigDecimal.valueOf((n - x)/(x + 1))).setScale(10, BigDecimal.ROUND_HALF_UP); // C(N)(k+1) = C(N)(k) * (N-k)/(k+1)
            drobK = drobK.multiply(BigDecimal.valueOf(drob)).setScale(10, BigDecimal.ROUND_HALF_UP); // возвожу дробь в следущую степень
            num = sochet.multiply(drobK).setScale(10, BigDecimal.ROUND_HALF_UP);
        }
        // Считаю значения в последней точке
        sum1 = sum1.add(num);
        num = num.multiply(BigDecimal.valueOf((n - V)/n));
        sum2 = sum2.add(num);
    
        // Считаем Pb
        Pb = num.divide(sum2, 6, BigDecimal.ROUND_HALF_UP).floatValue();
    
        // Считаем P и k_
        sochet = BigDecimal.valueOf(1); // Сочетание из N по 0
        drobK = BigDecimal.valueOf(1); // дробь в степени 0
        k_ = 0;
        BigDecimal res;
        for (int k = 0; k < V; k++) {
            P[k] = sochet.divide(sum1, 6, BigDecimal.ROUND_HALF_UP).multiply(drobK).setScale(6, BigDecimal.ROUND_HALF_UP).floatValue();
            k_ += k * P[k];
            
            sochet = sochet.multiply(BigDecimal.valueOf((n - k)/(k + 1))).setScale(10, BigDecimal.ROUND_HALF_UP); // C(N)(k+1) = C(N)(k) * (N-k)/(k+1)
            drobK = drobK.multiply(BigDecimal.valueOf(drob)).setScale(10, BigDecimal.ROUND_HALF_UP); // возвожу дробь в следущую степень
        }
        // Считаю значения в последней точке
        P[V] = sochet.divide(sum1, 6, BigDecimal.ROUND_HALF_UP).multiply(drobK).setScale(6, BigDecimal.ROUND_HALF_UP).floatValue();
        k_ += V * P[V];
    
        // Считаем Pt
        Pt = P[V];
    
        // Считаем t_
        t_ = k_ / ((n - k_) * alfa);
    }
    
    public float getPb() {
        return Pb;
    }
    
    public float getPbFormula() {
        return PbFormula;
    }
}
