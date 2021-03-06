package lab4.method;

import lab4.exception.IncorrectFunctionValueException;
import lab4.matrix.LinearSystem;
import lab4.table.Table;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class LogarithmicallyApproximationMethod implements ApproximationMethod {
    private double SLNX, SLNXX, SY, SYLNX;

    @Override
    public Function<Double, Double> getFunction(Table table) {
        SLNX = 0;
        SLNXX = 0;
        SY = 0;
        SYLNX = 0;
        int n = table.getMap().size();
        table.getXData().forEach(x -> SLNX += Math.log(x));
        table.getXData().forEach(x -> SLNXX += Math.log(x) * Math.log(x));
        table.getYData().forEach(y -> SY += y);
        table.getMap().forEach((x, y) -> SYLNX += y * Math.log(x));
        if (Double.isNaN(SLNX + SLNXX + SY + SYLNX))
            throw new IncorrectFunctionValueException("Невозможно посчитать значения логарифма, аппроксимация логарифмом не допустима для данного набора точек");
        log.info("SLNX={}, SLNXX={}, SY={}, SYLNX={}", SLNX, SLNXX, SY, SYLNX);
        LinearSystem linearSystem = solveSystem(SLNX, SLNXX, SY, SYLNX, n);
        double a = linearSystem.getX()[0], b = linearSystem.getX()[1];
        log.info("a={}, b={}", a, b);
        log.info("f(x) = {}ln(x)) + {}", a, b);
        return x -> {
            if (x <= 0)
                throw new IncorrectFunctionValueException("Для построения логарифмической функции X на всём промежутке должен быть больше нуля");
            else return a * Math.log(x) + b;
        };
    }
}
