package lab4.method;

import lab4.matrix.LinearSystem;
import lab4.matrix.LinearSystemSolver;
import lab4.table.Table;

import java.util.function.Function;

public interface ApproximationMethod {
    Function<Double, Double> getFunction(Table table);
    default LinearSystem solveSystem(double SX, double SXX, double SY, double SXY, int n) {
        double[][] matrixA = new double[][]{
                {SXX, SX},
                {SX, n}};
        double[] matrixB = new double[]{SXY, SY};
        LinearSystem linearSystem = new LinearSystem(2, matrixA, matrixB);
        LinearSystemSolver solver = new LinearSystemSolver();
        solver.solve(linearSystem);
        return linearSystem;
    }
}
