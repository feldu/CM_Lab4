package lab4.method;

import lab4.table.Table;

import java.util.function.Function;

public interface ApproximationMethod {
    Function<Double, Double> getFunction(Table table);
}
