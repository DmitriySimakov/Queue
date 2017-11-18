package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMV extends ModelABV {
    
    public MMV() {
        name = "M/M/V";
    
        modelImage = R.drawable.empty_image;
        BDPImage = R.drawable.empty_image;
    
        kFormula = R.drawable.empty_image;
        tFormula = R.drawable.empty_image;
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
        // Считаем γ
        k = 1/(mu * (V - ro));
    
        // Считаем j
        t = lambda * k;
    
        // Считаем промежуточные значения
        P = new float[V + 6];
        float roV = (float) Math.pow(ro, V);
        float i_Factorial = 1;
        for (int i = 0; i <= V; i++) {
            if (i != 0) i_Factorial *= i;
            P[i] = roV/i_Factorial;
        }
        double V_Factorial = i_Factorial;
        double part1 = P[V];
    
        double denominator;
        double sum = 0;
        double factorialX = 1;
        for (int x = 0; x <= V-1; x++) {
            if (x != 0) factorialX *= x;
            sum += Math.pow(ro, x)/factorialX;
        }
        double part2 = part1 * V/(V-ro);
        denominator = sum + part2;
        
        // Считаем Pt
        Pt = (float) (part2/denominator);
        
        // Считаем P
        for (int k = 0; k <= V; k++) {
            P[k] = (float) (P[k] / denominator);
        }
        float part3 = ro/V;
        for (int k = V + 1; k < P.length; k++) {
            P[k] = P[V] * part3;
            part3 *= ro/V;
        }
    }
}
