package lab4.method;

import lab4.matrix.LinearSystem;
import lab4.table.Table;
import lombok.extern.slf4j.Slf4j;

import java.util.OptionalDouble;
import java.util.function.Function;

@Slf4j
public class LinearApproximationMethod implements lab4.method.ApproximationMethod {
    private double SX, SXX, SY, SXY;

    @Override
    public Function<Double, Double> getFunction(Table table) {
        int n = table.getMap().size();
        table.getXData().forEach(x -> SX += x);
        table.getXData().forEach(x -> SXX += x * x);
        table.getYData().forEach(y -> SY += y);
        table.getMap().forEach((x, y) -> SXY += x * y);
        log.info("SX={}, SXX={}, SY={}, SXY={}", SX, SXX, SY, SXY);
        LinearSystem linearSystem = solveSystem(SX, SXX, SY, SXY, n);
        double a = linearSystem.getX()[0], b = linearSystem.getX()[1];
        log.info("a={}, b={}", a, b);
        log.info("f(x) = {}x + {}", a, b);
        return x -> (a * x + b);
    }

    public double findCorrelationCoefficient(Table table) {
        OptionalDouble val = table.getXData().stream().mapToDouble(x -> x).average();
        double avgX = val.isPresent() ? val.getAsDouble() : 0;
        val = table.getYData().stream().mapToDouble(x -> x).average();
        double avgY = val.isPresent() ? val.getAsDouble() : 0;
        log.info("avgX={}, avgY={}", avgX, avgY);
        final double[] crutch = {0, 0, 0};
        table.getMap().forEach((x, y) -> crutch[0] += (x - avgX) * (y - avgY));
        table.getMap().forEach((x, y) -> crutch[1] += Math.pow((x - avgX), 2));
        table.getMap().forEach((x, y) -> crutch[2] += Math.pow((y - avgY), 2));
        return crutch[0] / Math.sqrt(crutch[1] * crutch[2]);
    }

}
