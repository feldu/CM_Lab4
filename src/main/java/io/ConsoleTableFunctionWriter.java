package io;


public class ConsoleTableFunctionWriter implements TableFunctionWriter {
    @Override
    public void printInfo(String s) {
        System.out.println(s);
    }

}
