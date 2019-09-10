//Mohammad Shafi (cssc0821) & Tanner Sutton (cssc0829)

package edu.sdsu.cs;
import edu.sdsu.cs.datastructures.DirectedGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class App
{
    public static void main( String[] args )
    {
        App app = new App();
        DirectedGraph graph = new DirectedGraph();

        Scanner reader;
        if(args.length == 0) {
             reader = app.openFile("layout.csv");
        }
        else{
             reader = app.openFile(args[0]);
        }
        DirectedGraph<String> csvGraph = new DirectedGraph();
        app.buildGraph(reader,csvGraph);


        reader.close();
        app.printGraph(csvGraph);


        Scanner input = new Scanner(System.in);

        System.out.println("Shortest Path\n\n Enter Start");
        String in1 = input.nextLine();


        System.out.println("Enter Destination");
        String in2 = input.nextLine();

        for(String find : csvGraph.vertices()){
            if(find.equals(in1)) {
                in1 = find;
            }
            if(find.equals(in2)) {
                in2 = find;
            }

        }


        System.out.println("Shortest Path Route " + csvGraph.shortestPath(in1,in2));
        System.out.println("Shortest Path Length " + csvGraph.shortestPath(in1,in2).size());





    }
    private Scanner openFile(String fileName) {
        if(!(fileName.substring(fileName.lastIndexOf('.'), fileName.length()).equals(".csv"))) {
            System.out.println("That filename is not supported");
            System.exit(0);
        }
        Scanner scan = null;
        try {
            scan =new Scanner(new File(fileName));
        }
        catch(FileNotFoundException e) {
            System.out.println(fileName + " could not be found");
            System.exit(0);
        }
        return scan;
    }


    public DirectedGraph<String> buildGraph(Scanner scan, DirectedGraph<String> graph){

        while(scan.hasNextLine()){

        String line = scan.nextLine();
            if(line.indexOf(",") == line.length()-1){
                graph.add(line.substring(0,line.length()-1));
            }


            else {


                String one = line.substring(0,line.indexOf(","));
                String two = line.substring(line.indexOf(",") + 1 , line.length());


                if(!graph.contains(one))
                    graph.add(one);
                if(!graph.contains(two))
                    graph.add(two);


               graph.connect(one,two);

            }






        }

        return graph;

    }

    private void printGraph(DirectedGraph<String> graphprinter) {
        System.out.println("The vertices of this graph are: \n\n");
        for(String vertex: graphprinter.vertices()) {
            System.out.println( vertex);
            System.out.println("This vertex's neighbors are:");
            for(String neighbor: graphprinter.neighbors(vertex)) {
                System.out.println("   " + neighbor);
            }
            System.out.println();
        }
    }




}
