package lab4;

import lab4.io.*;
import lab4.plot.Series;
import lab4.table.Table;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lab4.plot.Plot;


import java.util.InputMismatchException;


@Slf4j
public class Main {
    private static final String commandFormat = "CM_Lab4 [-i file_path] [-o file_path]";
    private static TableFunctionReader in;
    private static TableFunctionWriter out;

    public static void main(String[] args) {
        configure(args);
        try {
            Table table = readTable();
            paintPoints(table);
        } catch (InputMismatchException e) {
            log.error("Incorrect input type");
            System.err.println("Введённые данные некоректны");
        } catch (NumberFormatException e) {
            log.error("Incorrect input type");
            System.err.println("Введённые данные некоректны");
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void paintPoints(Table table) {
        Series series = new Series("Точки");
        series.setXData(table.getXData());
        series.setYData(table.getYData());
        series.setHideLines(true);
        //rofl
        Series series2 = new Series("Линейная функция", x -> 1.4543 * x + 5.2911, table.getLeftBorder(), table.getRightBorder());
        series2.setHidePoints(true);
        Plot plot = new Plot("График", series, series2);
        plot.save("lab4.lab4.plot.png");
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
