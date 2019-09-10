import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;


public class NGram {


    public static void main(String[] args) {


        NGram ngram = new NGram();

        if (args.length != 2) {
            System.out.println("--Incorrect Arguments--");
            System.out.println("Argument 1: Input file name");
            System.out.println("Argrmnet 2: Output file name");
            System.exit(0);
        }


        BalancedMap biGram = ngram.populateBiGram(ngram.allTokens(ngram.openFile(args[0])));
        BalancedMap triGram = ngram.populateTriGram(ngram.allTokens(ngram.openFile(args[0])));


        ngram.writeToFile((biGram),(triGram), args[1]);

    }


    private LinkedList<String> allTokens(Scanner file) {
        LinkedList<String> tokenList = new LinkedList<>();
        while (file.hasNext()) {
            tokenList.add(file.next());
        }

        return tokenList;
    }




    public Scanner openFile(String fileName) {

        File inputFile = new File(fileName);
        Scanner scan = null;


        try {
            scan = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("No File Found");
            System.exit(0);
        }


        return scan;
    }


    public BalancedMap populateBiGram(LinkedList input) {
        BalancedMap<String, Integer> BiMap = new BalancedMap();
        LinkedList<String> allWords = new LinkedList(input);


        while (allWords.size() >= 2) {

            String token = allWords.get(0) + " " + allWords.get(1);

            if (BiMap.contains(token)) {
                Integer temp = BiMap.getValue(token);
                BiMap.delete(token);
                BiMap.add(token, temp + 1);
                allWords.remove(0);

            } else {
                BiMap.add(token, 1);
                allWords.remove(0);
            }


        }


        return BiMap;
    }

    public BalancedMap populateTriGram(LinkedList input) {

        BalancedMap<String, Integer> TriMap = new BalancedMap();
        LinkedList<String> allWords = new LinkedList(input);


        while (allWords.size() >= 3) {

            String token = allWords.get(0) + " " + allWords.get(1) + " " + allWords.get(2);

            if (TriMap.contains(token)) {
                Integer temp = TriMap.getValue(token);
                TriMap.delete(token);
                TriMap.add(token, temp + 1);
                allWords.remove(0);
            } else {
                TriMap.add(token, 1);
                allWords.remove(0);
            }


        }


        return TriMap;
    }





    public void writeToFile(BalancedMap<String,Integer> sortedBigram, BalancedMap<String,Integer> sortedTriGram, String fileName) {
        File file = new File(fileName);
        PrintWriter print = null;

        int maxvalue = 0;

        for(Integer value: sortedBigram.values()) {
            if(value > maxvalue) {
                maxvalue = value;
            }
        }





        try {
            print = new PrintWriter(file);


            print.format("%-30s %-30s %-30s %n %n %n ", "Word 1", "Word 2", "Count");

            for(int i = maxvalue; i >0; i--) {
                Set<String> keys = (Set<String>) sortedBigram.getKeys(i);



                for (String key : keys) {

                    String temp = key.substring(0, key.lastIndexOf(" "));
                    String temp2 = key.substring(key.lastIndexOf(" "), key.length());

                    print.format("%-30s %-30s %-30s %n", temp, temp2, sortedBigram.getValue(key));


                }
            }


            print.format("%n %n %n %-30s %-30s %-30s %-30s %n %n ", "Word 1", "Word 2", "Word 3", "Count");

            for(int i = maxvalue; i > 0; i--) {

                Set<String> keys = (Set<String>) sortedTriGram.getKeys(i);
                for (String key : keys) {

                    String temp = key.substring(0, key.indexOf(" "));
                    String temp2 = key.substring(key.indexOf(" "), key.lastIndexOf(" "));
                    String temp3 = key.substring(key.lastIndexOf(" "), key.length());


                    print.format("%-30s %-30s %-30s %-30s %n", temp, temp2, temp3, sortedTriGram.getValue(key));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        finally {
            print.close();
        }

    }


}
