package lab4.method;

import lab4.matrix.LinearSystem;
import lab4.matrix.LinearSystemSolver;
import lab4.table.Table;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class SquareApproximationMethod implements ApproximationMethod {
    private double SX, SXX, SXXX, SXXXX, SY, SXY, SXXY;

    @Override
    public Function<Double, Double> getFunction(Table table) {
        int n = table.getMap().size();
        table.getXData().forEach(x -> SX += x);
        table.getXData().forEach(x -> SXX += x * x);
        table.getXData().forEach(x -> SXXX += x * x * x);
        table.getXData().forEach(x -> SXXXX += x * x * x * x);
        table.getYData().forEach(y -> SY += y);
        table.getMap().forEach((x, y) -> SXY += x * y);
        table.getMap().forEach((x, y) -> SXXY += x * x * y);
        log.info("SX={}, SXX={}, SXXX={}, SXXXX={}, SY={}, SXY={}, SXXY={}", SX, SXX, SXXX, SXXXX, SY, SXY, SXXY);
        LinearSystem linearSystem = solveSystem(n);
        double a = linearSystem.getX()[2], b = linearSystem.getX()[1], c = linearSystem.getX()[0];
        log.info("a={}, b={}, c={}", a, b, c);
        log.info("f(x) = {}x^2 + {}x + {}", a, b, c);
        return x -> a*x*x + b*x + c;
    }

    private LinearSystem solveSystem(int n) {
        double[][] matrixA = new double[][]{
                {n, SX, SXX},
                {SX, SXX, SXXX},
                {SXX, SXXX, SXXXX}};
        double[] matrixB = new double[]{SY, SXY, SXXY};
        LinearSystem linearSystem = new LinearSystem(3, matrixA, matrixB);
        LinearSystemSolver solver = new LinearSystemSolver();
        solver.solve(linearSystem);
        return linearSystem;
    }
}
