package lab4.method;

import lab4.matrix.LinearSystem;
import lab4.table.Table;

import java.util.List;

public class LinearApproximationMethod implements lab4.method.ApproximationMethod {
    @Override
    public List<Double> getCoefficients(Table table) {
        LinearSystem linearSystem = new LinearSystem(1, new double[1][1], new double[1]);
        return null;
    }
}
