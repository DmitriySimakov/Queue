package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMV extends Model_V {
    
    public MMV() {
        name = "M/M/V";
    
        modelImage = R.drawable.empty_image;
        BDPImage = R.drawable.empty_image;
    
        k_Formula = R.drawable.empty_image;
        t_Formula = R.drawable.empty_image;
        PtFormula = R.drawable.empty_image;
        pFormula = R.drawable.empty_image;
    }
    
    public String setValues(float lambda, float mu, int V) {
        if (lambda / (V * mu) < 1) {
            return super.setValues(lambda, mu, V);
        }
        return "Должно выполняться: λ/(V • μ) < 1";
    }
    
    @Override
    public void calculate() {
        // Считаем промежуточные значения
        P = new float[V + 6];
        float roI = 1;
        float factorial_i = 1;
        double sum = 0;
        for (int i = 0; i < V; i++) {
            if (i >= 2) factorial_i *= i;
            P[i] = roI/factorial_i; // считаю числитель и записываю в массив
            sum += P[i]; // считаю сумму
            roI *= ro; // Возвожу ро в следующую степень
        }
        P[V] = roI/(factorial_i * V);
        double part1 = P[V] * V/(V-ro);
        double denominator = sum + part1;
    
        // Считаем P
        // Левая часть графика
        for (int k = 0; k <= V; k++) {
            P[k] = (float) (P[k] / denominator);
        }
        // Правая часть графика
        float part2 = ro/V;
        for (int k = V + 1; k < P.length; k++) {
            P[k] = P[V] * part2;
            part2 *= part2;
        }
        
        // Считаем Pt
        Pt = (float) (part1/denominator);
    
        // Считаем γ_
        k_ = 1/(mu * (V - ro));
    
        // Считаем j_
        t_ = lambda * k_;
    }
}
