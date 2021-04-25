package table;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Table {
    private Map<Double, Double> map;

    public Table(Map<Double, Double> map) {
        this.map = map;
    }
}
