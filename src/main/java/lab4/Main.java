package lab4;

import lab4.exception.IncorrectFunctionValueException;
import lab4.io.*;
import lab4.method.*;
import lab4.plot.Plot;
import lab4.plot.Series;
import lab4.rms.RMS;
import lab4.table.Table;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.function.Function;


@Slf4j
public class Main {
    private static final String commandFormat = "CM_Lab4 [-i file_path] [-o file_path]";
    private static TableFunctionReader in;
    private static TableFunctionWriter out;
    private static Map<String, ApproximationMethod> methods;

    public static void main(String[] args) {
        addMethodsToMap();
        configure(args);
        try {
            Table table = readTable();
            Plot plot = new Plot("Plot");
            String bestFunctionName = "suck dick";
            double minRMS = Double.MAX_VALUE;
            for (Map.Entry<String, ApproximationMethod> entry : methods.entrySet()) {
                String name = entry.getKey();
                try {
                    ApproximationMethod method = entry.getValue();
                    Function<Double, Double> function = method.getFunction(table);
                    if (method.getClass().equals(LinearApproximationMethod.class)) {
                        double corrCoefficient = ((LinearApproximationMethod) method).findCorrelationCoefficient(table);
                        log.info("Correlation coefficient: {}", corrCoefficient);
                        out.printInfo("Коэффициент корреляции: " + corrCoefficient);
                    }
                    double rms = RMS.findRMS(table, function);
                    log.info("rms={}", rms);
                    out.printInfo("СКО для " + name + " функции: " + rms);
                    if (rms < minRMS) {
                        minRMS = rms;
                        bestFunctionName = name;
                    }
                    addSeriesToChart(table, plot, name, function);
                } catch (IncorrectFunctionValueException e) {
                    log.error("Can't add series {} to chart. Invalid data interval\n{}", name, e.getMessage());
                    out.printError("Не удалось построить график " + name);
                    out.printError(e.getMessage());
                }
            }
            log.info("Minimal RMS has {} function: {}", bestFunctionName, minRMS);
            out.printInfo("Минимальное СКО у " + bestFunctionName + " функции: " + minRMS);
            Series inputPoints = addInputPointsToChart(table, plot);
            //save general plot
            plot.save("plot.png");
            Series bestSeries = new Series(bestFunctionName, methods.get(bestFunctionName).getFunction(table), table.getLeftBorder(), table.getRightBorder());
            bestSeries.setHidePoints(true);
            Plot bestPlot = new Plot("Best approximation", inputPoints, bestSeries);
            bestPlot.save("best_plot.png");
        } catch (InputMismatchException e) {
            log.error("Incorrect input type");
            out.printError("Введённые данные некоректны");
        } catch (NumberFormatException e) {
            log.error("Incorrect input type");
            out.printError("Введённые данные некоректны");
            out.printError(e.getMessage());
        } catch (Exception e) {
            out.printError(e.getMessage());
        }
    }

    private static void addSeriesToChart(Table table, Plot plot, String name, Function<Double, Double> function) {
        Series currentSeries = new Series(name, function, table.getLeftBorder(), table.getRightBorder());
        currentSeries.setHidePoints(true);
        plot.addSeries(currentSeries);
    }

    private static Series addInputPointsToChart(Table table, Plot plot) {
        Series inputPoints = new Series("Input points");
        inputPoints.setXData(table.getXData());
        inputPoints.setYData(table.getYData());
        inputPoints.setHideLines(true);
        plot.addSeries(inputPoints);
        return inputPoints;
    }

    private static void addMethodsToMap() {
        methods = new HashMap<>();
        methods.put("Linear", new LinearApproximationMethod());
        methods.put("Square", new SquareApproximationMethod());
        methods.put("Power", new PowerApproximationMethod());
        methods.put("Exponential", new ExponentialApproximationMethod());
        methods.put("Logarithmically", new LogarithmicallyApproximationMethod());
    }


    private static Table readTable() {
        int n = in.readIntWithMessage("Введите количество точек:");
        if (n < 12) {
            log.error("Less than 12 points");
            //todo: кинуть эксептион
        }
        out.printInfo("Вводите точки таблицы в формате: x(i) y(i)");
        return in.readTable(n);
    }

    @SneakyThrows
    private static void configure(String[] args) {
        in = new ConsoleTableFunctionReader();
        out = new ConsoleTableFunctionWriter();
        if (args.length > 4)
            throw new RuntimeException("Неверное количество аргументов\n" + commandFormat);
        if (args.length > 0) {
            if (args[0].equals("-i")) in = new FileTableFunctionReader(args[1]);
            else if (args[0].equals("-o")) out = new FileTableFunctionWriter(args[1]);
            else throw new RuntimeException("Неверный второй аргумент\n" + commandFormat);
        }
        if (args.length > 2) {
            if (args[2].equals("-i")) in = new FileTableFunctionReader(args[3]);
            else if (args[2].equals("-o")) out = new FileTableFunctionWriter(args[3]);
            else throw new RuntimeException("Неверный четвёртый аргумент\n" + commandFormat);
        }
        log.info("Reading with: {}", in.getClass().getTypeName());
        log.info("Writing with: {}", out.getClass().getTypeName());
    }
}
