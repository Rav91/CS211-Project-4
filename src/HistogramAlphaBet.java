import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.stream.StreamSupport;

public class HistogramAlphaBet {
    private HashMap<Character, Double> charMap;
    private double total;

    HistogramAlphaBet(){
        this.charMap = new HashMap<Character, Double>();
        this.total = 0;
    }

    public void increment(){
        this.total++;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public HashMap<Character, Double> getCharMap() {
        return charMap;
    }

    public void setCharMap(HashMap<Character, Double> charMap) {
        this.charMap = charMap;
    }

    public void mapFromFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while(scanner.hasNext()){
            char[] line = scanner.nextLine().toUpperCase().toCharArray();
            for(int i=0; i<line.length; i++){
                System.out.println("\t" + line[i]);
            }
            for (Character character : line) {
                if (Character.isLetter(character)){
                    increment();
                    if (charMap.containsKey(character)) {
                        charMap.put(character, charMap.get(character) + 1.0);
                    } else {
                        charMap.put(character, 1.0);
                    }
                }
            }
        }
        scanner.close();
        for (Map.Entry<Character, Double> entry : charMap.entrySet()) {
            charMap.replace(entry.getKey(), (entry.getValue() / total));
        }
    }

    public HashMap<Character, Double> sortHashMapByValues(HashMap<Character, Double> passedMap) {
        List<Character> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Double> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<Character, Double> sortedMap = new LinkedHashMap<>();

        Iterator<Double> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Double val = valueIt.next();
            Iterator<Character> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Character key = keyIt.next();
                Double comp1 = passedMap.get(key);
                Double comp2 = val;

                System.out.println(key);

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

}