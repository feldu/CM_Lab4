package function;

import lombok.Getter;

import java.util.function.Function;

@Getter
public abstract class ApproximationFunction {
    private double a, b, c;
    private Function<Double, Double> function;

    public ApproximationFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.function = makeFunction(a, b, c);
    }

    abstract Function<Double, Double> makeFunction(double a, double b, double c);
}
