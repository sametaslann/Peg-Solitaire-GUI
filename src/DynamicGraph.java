

public interface DynamicGraph extends Graph {



    /**
     *  Generate a new vertex by given parameters.
     * @param label Takes the label of vertexx
     * @param weight takes the weight of vertex
     * @return Returns the new generated vertex
     */
    public Vertex newVertex (String label, double weight);

    /**
     * Adds the new vertex to the graph
     * @param new_vertex Takes a new vertex
     */
    public void addVertex (Vertex new_vertex); 

    /**
     * Adds an edge between the given two vertices in the graph.
     * @param vertexID1 Id of the first vertex
     * @param vertexID2 Id of the second vertex
     * @param weight Weight of the edge
     * @return Return if it added 
     */
    public void addEdge (int vertexID1, int vertexID2, double weight); 

    /**
     * Removes the edge between the given two vertices.
     * @param vertexID1 Id of the first vertex
     * @param vertexID2 Id of the second vertex
     * @return Return true if it removed
     */
    public boolean removeEdge (int vertexID1, int vertexID2); 



    /**
     * Removes the vertex from the graph with respect to the given vertex id.
     * @param vertexID id of the given vertex
     * @return Return true if it removed
     */
    public boolean removeVertex (int vertexID); 


    /**
     * Removes the vertices that have the given label from the graph.
     * @param label Takes the label of the vertex
     */
    public void removeVertex (String label); 

    
    /**
     * Filters the vertices by the given user-defined property and returns a subgraph of the graph.
     * @param key Key of the vertex
     * @param filter filter of the vertex
     * @return Return subgraph
     */
    public Graph filterVertices (String key, String filter); 

    /**
     * Generates the adjacency matrix representation of the graph and returns the matrix.
     * @return Returns the generated matrix
     */
    public double[][] exportMatrix(); 


    /**
     * Print the graph in adjacency list format 
     */
    public void printGraph();


    
}
