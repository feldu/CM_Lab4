package lab4.function;

import java.util.function.Function;

public class LinearApproximationFunction extends ApproximationFunction {

    public LinearApproximationFunction(double a, double b, double c) {
        super(a, b, c);
    }

    @Override
    Function<Double, Double> makeFunction(double a, double b, double c) {
        return x -> a*x + b;
    }
}
