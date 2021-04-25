package lab4.matrix;

import lombok.Getter;

@Getter
public class LinearSystem {
    private int n;
    private double[][] a;
    private double[] b;
    private double[] x;

    public LinearSystem(int n, double[][] a, double[] b) {
        this.n = n;
        this.a = a;
        this.b = b;
        x = new double[n];
    }
}
