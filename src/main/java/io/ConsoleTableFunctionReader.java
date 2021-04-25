package io;

import table.Table;

import java.util.*;

public class ConsoleTableFunctionReader implements TableFunctionReader {
    Scanner in = new Scanner(System.in);

    @Override
    public double readDoubleWithMessage(String message) {
        System.out.println(message);
        return readDouble();
    }

    @Override
    public Table readTable(int size) {
        SortedMap<Double, Double> tableMap = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            tableMap.put(in.nextDouble(), in.nextDouble());
        }
        return new Table(tableMap);
    }

    @Override
    public int readInt() {
        return in.nextInt();
    }

    @Override
    public int readIntWithMessage(String message) {
        System.out.println(message);
        return readInt();
    }

    @Override
    public double readDouble() {
        return in.nextDouble();
    }

    @Override
    public String readString() {
        return in.next();
    }

    @Override
    public String readStringWithMessage(String message) {
        System.out.println(message);
        return readString();
    }
}
