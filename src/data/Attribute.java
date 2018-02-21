package data;

import java.util.ArrayList;

public class Attribute {

    String name;
    ArrayList<String> values;

    public Attribute(String name, ArrayList<String> values) {
        this.name = name;
        this.values = values;
    }

    public Attribute(String name) {
        this.name = name;
        this.values = new ArrayList<String>(0);
    }

    public int numValues() {
        return values.size();
    }

    public void addValue(String value) {
        this.values.add(value);
    }

    public ArrayList<String> getValues() {
        return new ArrayList<String>(values);
    }
}
