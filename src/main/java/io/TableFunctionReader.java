package io;


import table.Table;

public interface TableFunctionReader {
    Table readTable(int size);





    int readInt();

    int readIntWithMessage(String message);

    double readDouble();

    double readDoubleWithMessage(String message);

    String readString();

    String readStringWithMessage(String message);

}
