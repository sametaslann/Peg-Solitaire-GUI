import java.util.*;

/** A class for calling Dijkstra's algorithm.
 *  @author Koffman and Wolfgang
 */

public class DijkstrasAlgorithm {




  /** Dijkstra�s Shortest-Path algorithm.
      @param graph The weighted graph to be searched
      @param start The start vertex
                  in the shortest path
      @return Returns the distances of the paths
  */
  public static double[] dijkstrasAlgorithm(MyGraph myGraph,
                                        int startID) {


    int numV = myGraph.getNumV();
    HashSet < Integer > vMinusS = new HashSet < Integer > (numV);
    int[] pred = new int[numV];
    double[] dist = new double[numV];

    // Initialize V-S.
    for (int i = 0; i < numV; i++) {
      if (i != startID) {
        vMinusS.add(i);
      }
    }
    // Initialize pred and dist.
    for (int v : vMinusS) {
      pred[v] = startID;
      dist[v] = myGraph.getEdgeWeightWithId(startID, v);
      
    }
    // Main loop
    while (vMinusS.size() != 0) {
      // Find the value u in V-S with the smallest dist[u].
      double minDist = Double.POSITIVE_INFINITY;
      int u = -1;
      for (int v : vMinusS) {
        if (dist[v] <= minDist) {
          minDist = dist[v];
          u = v;
        }
      }
      // Remove u from vMinusS.
      vMinusS.remove(u);
      // Update the distances.
      for (int v : vMinusS) {

        if (myGraph.isEdge(u, v)) {
          double weight = myGraph.getEdgeWeightWithId(u, v);
          double boost =0.0;

          if(myGraph.getVertex(u).getKey() == "Boosting")
            boost = Double.parseDouble(myGraph.getVertex(u).getValue());  
          
          if (dist[u] + weight - boost< dist[v]) {            
              dist[v] = dist[u] + weight - boost;
              pred[v] = u;
          }
        }
      }
    }
    return dist;
  }
}
