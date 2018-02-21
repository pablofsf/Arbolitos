package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class DataProcessor {
    String name;
    ArrayList<Attribute> attributes;
    ArrayList<ArrayList<Integer>> examples;


    //Constructor
    public DataProcessor(String path) throws FileNotFoundException {
        File data = new File(path);
        Scanner linesReader = new Scanner(data);
        this.name = "";
        this.attributes = new ArrayList<Attribute>(0);
        this.examples = new ArrayList<ArrayList<Integer>>(0);
        Parse(linesReader);
    }

    //Copy Constructor
    public DataProcessor(DataProcessor original) {
        this.name = new String(original.getName());
        this.attributes = new ArrayList<Attribute>(original.getAttributes());
        this.examples = new ArrayList<ArrayList<Integer>>(original.getExamples());
    }

    //Getter for copy constructor
    public String getName() {
        return name;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public ArrayList<ArrayList<Integer>> getExamples() {
        return examples;
    }


    public int numAttributes() {
        return this.attributes.size();
    }

    public int numExamples() {
        return this.examples.size();
    }

    public Attribute getAttribute(int index) {
        return this.attributes.get(index);
    }

    public void deleteAttribute(int index) {
        this.attributes.remove(index);
        for (ArrayList<Integer> example : this.examples) {
            example.remove(index);
        }
    }

    public void deleteAttribute(Attribute attribute) {
        int index = this.attributes.indexOf(attribute);

        this.attributes.remove(attribute);
        for (ArrayList<Integer> example : this.examples) {
            example.remove(index);
        }
    }

    public void deleteExample(int index) {
        this.examples.remove(index);
    }

    public int[] attributeToArray(int index) {
        int[] array = new int[numExamples()];
        int i = 0;

        for (ArrayList<Integer> example : this.examples) {
            array[i++] = example.get(index);
        }

        return array;
    }

    public int[] attributeToArray(Attribute attribute) {
        int index = this.attributes.indexOf(attribute);

        int[] array = new int[numExamples()];
        int i = 0;

        for (ArrayList<Integer> example : this.examples) {
            array[i++] = example.get(index);
        }

        return array;
    }

    private void getRelation(Scanner linesReader) {
        String line = linesReader.nextLine();
        Scanner lineParser;

        while (line.startsWith("%") || line.hashCode() == 0) {
            line = linesReader.nextLine();
        }

        lineParser = new Scanner(line);

        if (lineParser.next().toUpperCase().equals("@RELATION")) {
            this.name = lineParser.next();
        } else {
            System.out.println("Error, no relation given or format error");
        }

        lineParser.close();
    }

    private String getAttributes(Scanner linesReader) {
        String line = linesReader.nextLine();
        Scanner lineParser;

        while (line.toUpperCase().startsWith("@ATTRIBUTE") || line.startsWith("%") || line.hashCode() == 0) {
            if (line.startsWith("%") || line.hashCode() == 0) {
                line = linesReader.nextLine();
                continue;
            }

            lineParser = new Scanner(line);
            String attributeName;
            ArrayList<String> attributeValues = new ArrayList<String>(0);

            if (lineParser.next().toLowerCase().equals("@attribute")) {
                attributeName = lineParser.next();

                lineParser.useDelimiter("\\s*[\\{\\},]+\\s*");
                while (lineParser.hasNext()) {
                    attributeValues.add(lineParser.next());
                }

                this.attributes.add(new Attribute(attributeName, attributeValues));

            } else {
                System.out.println("Error, no attribute given or format error");
            }

            lineParser.close();
            line = linesReader.nextLine();
        }

        return line;
    }

    private void getData(String line, Scanner linesReader) {
        Scanner lineParser;

        while (line.toLowerCase().startsWith("@data") || line.startsWith("%") || line.hashCode() == 0) {
            if (line.toLowerCase().startsWith("@data")) {
                break;
            }
            line = linesReader.nextLine();
        }

        while (linesReader.hasNext()) {
            line = linesReader.nextLine();

            if (line.startsWith("%") || line.hashCode() == 0) {
                continue;
            }

            ArrayList<Integer> exampleLine = new ArrayList<Integer>(numAttributes());
            Iterator<Attribute> attributesIt = this.attributes.iterator();
            lineParser = new Scanner(line);
            lineParser.useDelimiter("[\\s,]+");

            String exampleValue;

            while (attributesIt.hasNext()) {

                exampleValue = lineParser.next();
                ArrayList<String> attributeValues = attributesIt.next().getValues();

                for (int j = 0; j < attributeValues.size(); j++) {
                    if (attributeValues.get(j).equals(exampleValue)) {
                        exampleLine.add(j);
                        break;
                    }
                }
            }

            this.examples.add(exampleLine);
            lineParser.close();
        }
    }

    private void Parse(Scanner linesReader) {
        String line;

        //Get relation
        getRelation(linesReader);

        //Get attributes
        line = getAttributes(linesReader);

        //Get data
        getData(line, linesReader);

    }

	/*public static void main(String args[]) throws FileNotFoundException{
        @SuppressWarnings("unused")
		DataProcessor data = new DataProcessor("src/data/wait.arff");
	}*/
}
