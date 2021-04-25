package lab4.method;

import lab4.matrix.LinearSystem;
import lab4.matrix.LinearSystemSolver;
import lab4.table.Table;
import lombok.extern.slf4j.Slf4j;

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
        double[][] matrixA = new double[][]{
                {SXX, SX},
                {SX, n}};
        double[] matrixB = new double[]{SXY, SY};
        LinearSystem linearSystem = new LinearSystem(2, matrixA, matrixB);
        LinearSystemSolver solver = new LinearSystemSolver();
        solver.solve(linearSystem);
        double a = linearSystem.getX()[0], b = linearSystem.getX()[1];
        log.info("a={}, b={}", a, b);
        log.info("f(x) = {}x + {}", a, b);
        return x -> (a * x + b);
    }

}
