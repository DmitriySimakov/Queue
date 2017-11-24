package com.dmitry_simakov.queue.models;

import com.dmitry_simakov.queue.R;

public class MMinf extends Model {
    
    public MMinf() {
        name = "M/M/∞";
        description = "Очередь M/M/∞ представляет собой систему, в которой интенсивность " +
                "поступления вызовов и время обслуживания подчиняются экспоненциальному " +
                "распределению. Система имеет бесконечное количество каналов, следовательно, " +
                "запросы не имеют времени ожидания. Эта модель является частным случаем модели " +
                "M/M/v, где число каналов становится очень большим.\n" +
                "В модели M/M/∞ бесконечно линейный пучок представляет собой систему, в которой " +
                "всегда найдётся новый обслуживающий прибор, доступный каждому вновь " +
                "поступающему вызову. Время пребывания вызова в системе равно времени его " +
                "обслуживания. Такая интерпретация бесконечнолинейного пучка позволяет " +
                "использовать модель M/M/∞ для получения данных о нагрузке, поступающей в " +
                "систему распределения информации. Вся поступающая нагрузка оказывается " +
                "обслуженной и может оцениваться через среднее число вызовов, находящихся в " +
                "системе в произвольный момент времени.";
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