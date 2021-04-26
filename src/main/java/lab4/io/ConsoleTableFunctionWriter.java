package lab4.io;


public class ConsoleTableFunctionWriter implements TableFunctionWriter {
    @Override
    public void printInfo(String s) {
        System.out.println(s);
    }

    @Override
    public void printError(String s) {
        System.err.println(s);
    }

}
