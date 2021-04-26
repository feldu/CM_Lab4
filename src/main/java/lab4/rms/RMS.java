package lab4.rms;

import lab4.table.Table;

import java.util.Map;
import java.util.function.Function;

public class RMS {
    public static double findRMS(Table table, Function<Double, Double> function) {
        int n = table.getMap().size();
        double RMS = 0;
        for (Map.Entry<Double, Double> entry : table.getMap().entrySet()) {
            Double x = entry.getKey();
            Double y = entry.getValue();
            RMS += Math.pow((function.apply(x) - y), 2);
        }
        return Math.sqrt(RMS / n);
    }
}
