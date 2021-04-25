package lab4.matrix;

import java.util.Arrays;

/**
 * Gauss lab4.lab4.method with selection diagonal elements by the column
 */
public class LinearSystemSolver {

    public void solve(LinearSystem linearSystem) {
        int n = linearSystem.getN();
        double[][] a = new double[n][n];
        for (int i = 0; i < linearSystem.getA().length; i++) {
            a[i] = Arrays.copyOf(linearSystem.getA()[i], linearSystem.getA()[i].length);
        }

        double[] b = Arrays.copyOf(linearSystem.getB(), linearSystem.getB().length);
        double[] x = linearSystem.getX();
        //Прямой ход
        int swapCnt = forwardTraverse(a, b, n);


        double det = findDeterminant(a, swapCnt);
        if (Math.abs(det) < Math.pow(10, -40))
            throw new RuntimeException("Система несовместна или неопределена (det = 0)");
        //Обратный ход
        reverseTraverse(a, b, x, n);
    }

    private void reverseTraverse(double[][] a, double[] b, double[] x, int n) {
        /*
         * Получение корней уравнения
         */
        double sum;
        for (int i = n - 1; i >= 0; i--) {
            sum = 0;
            for (int k = i + 1; k < n; k++) {
                sum = sum + a[i][k] * x[k];
            }
            x[i] = (b[i] - sum) / a[i][i];
        }
    }

    private int forwardTraverse(double[][] a, double[] b, int n) {
        int maxElementIndex;
        double max, tmp;
        int swapCnt = 0;
        for (int j = 0; j < n - 1; j++) {
            /*
                Выбор главного элемента столбца
            */
            //Поиск индекса строки максимального по модулю эл-та столбца
            max = a[j][j];
            maxElementIndex = j;
            for (int i = j + 1; i < n; i++) {
                if (Math.abs(a[i][j]) > Math.abs(max)) {
                    max = a[i][j];
                    maxElementIndex = i;
                }
            }
            //Перестановка иф ниидед
            if (maxElementIndex != j) {
                //коэффициенты матрицы
                for (int i = 0; i < n; i++) {
                    tmp = a[j][i];
                    a[j][i] = a[maxElementIndex][i];
                    a[maxElementIndex][i] = tmp;
                }
                //свободнные члены
                tmp = b[j];
                b[j] = b[maxElementIndex];
                b[maxElementIndex] = tmp;
                //Увеличиваем счётчик для вычисления детерминанта
                swapCnt++;
            }
            /*
                Приведение к треугольному виду
            */
            //коэффициенты матрицы
            for (int k = j + 1; k < n; k++) {
                tmp = (a[k][j] / a[j][j]);
                for (int i = 0; i < n; i++) {
                    a[k][i] = a[k][i] - a[j][i] * tmp;
                }
                //свободнные члены
                b[k] = b[k] - b[j] * tmp;
            }
        }
        return swapCnt;
    }

    private double findDeterminant(double[][] a, int k) {
        double det = 1;
        for (int i = 0; i < a.length; i++) {
            det = det * a[i][i];
        }
        return det * Math.pow(-1, k);
    }
}
