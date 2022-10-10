import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;




public class BFSandDFS{

    /**
     * Calculates the distance difference between dfs and bfs and returns it
     * @param myGraph Accepts a graph
     * @param start Accepts the starting position of the graph
     * @return Return Distance difference between BFS and DFS
     */
    public static double distanceDiff(MyGraph myGraph, int start)
    {
        double distanceBfs = BreadthFirstSearch(myGraph, start);
        double distanceDfs = DepthFirstSearch(myGraph, myGraph.getVertex(start), new HashSet<>(), 0);

        System.out.println("Distance BFS: " + distanceBfs);
        System.out.println("Distance DFS: " + distanceDfs);

        return Math.abs(distanceBfs-distanceDfs);
    }

    /**
     * Apply the breadth first search with shortest path
     * @param myGraph Takes a graph
     * @param start Takes the position of the starting point
     * @return returns the shortest path lentgh as double
     */
    private static double BreadthFirstSearch(MyGraph myGraph, int start){
        double BFSDistance = 0.0;
        Queue < Integer > theQueue = new LinkedList < Integer > ();
        double [] findMin = new double[myGraph.getNumV()+1];
        boolean[] identified = new boolean[myGraph.getNumV()+1];
        identified[start] = true;
        theQueue.offer(start);
        while (!theQueue.isEmpty()) {

            int current = theQueue.remove();
            List<Vertex> currentList = myGraph.getVertexList(current);

            if(currentList == null)
            continue;
            
            int j=1;
            Arrays.fill(findMin, Integer.MAX_VALUE);
            while ( j < currentList.size()) {
                findMin[j] = myGraph.getEdgeWeight(current, j);
                j++;
            }
            Arrays.sort(findMin);
            for (int j2 = 0; j2 < j-1; j2++) {

                int id = myGraph.getVertex(current, findMin[j2]).getID();
                 if (!identified[id]) {
                    identified[id] = true;
                    theQueue.offer(id);
                    BFSDistance += findMin[j2];
                }
            }
        }
        return BFSDistance;

    }

    /**
     * Apply the depth first search by selecting the shortest way
     * @param myGraph Takes a graph
     * @param current Takes the position of starting point
     * @param visited Holds the visited vertex
     * @param dist Holds the total distance
     * @return Returns the shortest distance 
     */
    public static double DepthFirstSearch(MyGraph myGraph, Vertex current, Set<Vertex> visited, double distance) {
        if (visited.contains(current)) {
            return 0.0;
        }

        double [] findMin = new double[myGraph.getNumV()];
        int i =1;
        List<Vertex> currentList = myGraph.getVertexList(current.getID());
        while ( i < currentList.size()) {
            findMin[i-1] = myGraph.getEdgeWeight(current.getID(), i);
            i++;
        }
        Arrays.sort(findMin);
        double value = distance;
        visited.add(current);

        for (int j = 0; j < findMin.length; j++) {
            Vertex neighbor = myGraph.getVertex(current.getID(), findMin[j]);
            double weight = myGraph.getEdgeWeightWithId(current.getID(), neighbor.getID());
            value += DepthFirstSearch(myGraph, neighbor, visited, weight);
        }

        return value;
    }
}

