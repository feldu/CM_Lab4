package lab4.method;

import lab4.exception.IncorrectFunctionValueException;
import lab4.matrix.LinearSystem;
import lab4.table.Table;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class ExponentialApproximationMethod implements ApproximationMethod {
    private double SX, SXX, SLNY, SXLNY;

    @Override
    public Function<Double, Double> getFunction(Table table) {
        SX = 0;
        SXX = 0;
        SLNY = 0;
        SXLNY = 0;
        int n = table.getMap().size();
        table.getXData().forEach(x -> SX += x);
        table.getXData().forEach(x -> SXX += x * x);
        table.getYData().forEach(y -> SLNY += Math.log(y));
        table.getMap().forEach((x, y) -> SXLNY += x * Math.log(y));
        if (Double.isNaN(SX + SXX + SLNY + SXLNY)) throw new IncorrectFunctionValueException("Невозможно посчитать значения логарифма, аппроксимация экспонентой не допустима для данного набора точек");
        log.info("SX={}, SXX={}, SLNY={}, SXLNY={}", SX, SXX, SLNY, SXLNY);
        LinearSystem linearSystem = solveSystem(SX, SXX, SLNY, SXLNY, n);
        double a = Math.exp(linearSystem.getX()[1]), b = linearSystem.getX()[0];
        log.info("a={}, b={}", a, b);
        log.info("f(x) = {}e^({}x)", a, b);
        return x -> a * Math.exp(b * x);
    }
}
