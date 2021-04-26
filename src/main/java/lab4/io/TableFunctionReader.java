package lab4.io;


import lab4.table.Table;

public interface TableFunctionReader {
    Table readTable(int size);

    int readInt();

    int readIntWithMessage(String message);

    double readDouble();

    double readDoubleWithMessage(String message);

    String readString();

    String readStringWithMessage(String message);

}
