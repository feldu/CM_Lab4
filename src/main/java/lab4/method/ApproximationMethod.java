package lab4.method;

import lab4.table.Table;

import java.util.List;

public interface ApproximationMethod {
    public List<Double> getCoefficients(Table table);
}
