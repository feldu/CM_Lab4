package lab4.io;

import lab4.table.Table;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class FileTableFunctionReader implements TableFunctionReader {
    private final List<String> lines;
    private int currentLine = 0;

    public FileTableFunctionReader(String FILE_NAME) throws IOException {
        lines = Files.readAllLines(Paths.get(FILE_NAME), StandardCharsets.UTF_8);
    }

    @Override
    public Table readTable(int size) {
        SortedMap<Double, Double> tableMap = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            String[] pair = lines.get(currentLine++).replace(',', '.').split("\\s+");
            tableMap.put(Double.valueOf(pair[0]), Double.valueOf(pair[1]));
        }
        return new Table(tableMap);
    }

    @Override
    public int readInt() {
        return Integer.parseInt(lines.get(currentLine++));
    }

    @Override
    public int readIntWithMessage(String message) {
        return readInt();
    }

    @Override
    public double readDouble() {
        return Double.parseDouble(lines.get(currentLine++).replace(',', '.'));
    }

    @Override
    public double readDoubleWithMessage(String message) {
        return readDouble();
    }

    @Override
    public String readString() {
        return lines.get(currentLine++);
    }

    @Override
    public String readStringWithMessage(String message) {
        return readString();
    }
}
