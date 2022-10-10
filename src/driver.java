
public class driver {

    public static void main(String[] args) {

        MyGraph myGraph = new MyGraph(4, false);

        var a1 = new Vertex("A", 2,"red","blue");

        myGraph.addVertex(a1);
        myGraph.addVertex(new Vertex("B", 2,"Boosting","2.0"));
        myGraph.addVertex(new Vertex("C", 2,"Boosting","3.0"));
        myGraph.addVertex(new Vertex("D", 2,"red","blue"));
        myGraph.addVertex(new Vertex("E", 2,"red","blue"));
        myGraph.addVertex(new Vertex("F", 2,"red","blue"));
        

        myGraph.addEdge(0, 1, 7);
        myGraph.addEdge(0, 2, 9);
        myGraph.addEdge(0, 5, 14);


        myGraph.addEdge(1, 2, 10);
        myGraph.addEdge(1, 3, 15);
        myGraph.addEdge(2, 3, 11);
        myGraph.addEdge(2, 5, 2);

        myGraph.addEdge(3, 4, 6);
        myGraph.addEdge(4, 5, 9);


        myGraph.printGraph();
    
        System.out.println("|--------------Filter Vertices--------------|");
        MyGraph subGraph = myGraph.filterVertices("red", "blue");
        subGraph.printGraph();


        System.out.println("|---------------Export Matrix---------------|");
        myGraph.printMatrix( myGraph.exportMatrix());


        System.out.println("\n|--Difference Distance Between BFS and DFS--|");
        System.out.println("Distance Difference: " + BFSandDFS.distanceDiff(myGraph,0));
        System.out.println();


        System.out.println("|----Dijkstras Algo with Boosting Vertex----|");
        double[] distances = DijkstrasAlgorithm.dijkstrasAlgorithm(myGraph, 0);
        for (int i = 0; i < distances.length; i++) 
            System.out.println("StartID to " + i + " -> " + distances[i]);
        System.out.println();

        
        System.out.println("|---------------Delete Edge--------------|");
        myGraph.removeEdge(5, 0);
        System.out.println("|---------------[5,0] deleted------------|");
        myGraph.printGraph();


        System.out.println("|---------------Delete Vertex--------------|");
        myGraph.removeVertex("B");
        System.out.println("|------------1. index was deleted----------|");
        myGraph.printGraph();



        

        
    }
    
}
