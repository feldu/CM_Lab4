package lab4.method;

import lab4.matrix.LinearSystem;
import lab4.table.Table;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class PowerApproximationMethod implements ApproximationMethod {
    private double SLNX, SLNXX, SLNY, SLNXY;

    @Override
    public Function<Double, Double> getFunction(Table table) {
        int n = table.getMap().size();
        table.getXData().forEach(x -> SLNX += Math.log(x));
        table.getXData().forEach(x -> SLNXX += Math.log(x) * Math.log(x));
        table.getYData().forEach(y -> SLNY += Math.log(y));
        table.getMap().forEach((x, y) -> SLNXY += Math.log(x) * Math.log(y));
        log.info("SLNX={}, SLNXX={}, SLNY={}, SLNXY={}", SLNX, SLNXX, SLNY, SLNXY);
        LinearSystem linearSystem = solveSystem(SLNX, SLNXX, SLNY, SLNXY, n);
        double a = Math.exp(linearSystem.getX()[1]), b = linearSystem.getX()[0];
        log.info("a={}, b={}", a, b);
        log.info("f(x) = {}x^{}", a, b);
        return x -> a * Math.pow(x, b);
    }
}
