import java.util.*;
import java.util.function.DoubleBinaryOperator;
import java.io.*;
import java.lang.reflect.Method;

/** Interface to specify a DynamicGraph. Comments of 
 * overriden methods are in DynamicGraph.java
*/

public class MyGraph 
    implements DynamicGraph {

    private List < Vertex > [] vertices;
    private List < Double > [] edgeWeight;
    private boolean isDirected;
    private int capacity;
    private int size = 0;


    /**
     * Constructor of the mygraph class
     * @param capacity Accepts beginning size of array
     * @param isDirected Decides if the graph is directed
     */
    @SuppressWarnings("unchecked")
    public MyGraph(int capacity, boolean isDirected) {
       
        this.capacity = capacity;
        this.isDirected = isDirected;
        vertices = new ArrayList[this.capacity];
        edgeWeight = new ArrayList[this.capacity];

        for (int i = 0; i < capacity; i++) {
          vertices[i] = new ArrayList < Vertex > ();
          edgeWeight[i] = new ArrayList < Double > ();
          edgeWeight[i].add(0.0);
        }
    }

    @Override
    public Vertex newVertex(String label, double weight) {
        Vertex newVertex = new Vertex(label, weight); 
        return newVertex;
    }

     
    @SuppressWarnings("unchecked")
    @Override
    public void addVertex(Vertex new_vertex) {

        int i = 0;
        List < Vertex > newVertexList = new ArrayList<>();  
        List < Double > newWeightList = new ArrayList<>();  

        newVertexList.add(new_vertex);
        newWeightList.add(0.0);

       if(size >= capacity){

            List < Vertex > [] newVertices= new ArrayList[size+1];
            List < Double > [] newWeights= new ArrayList[size+1];

            for (List<Vertex> list : vertices) {
                newWeights[i] = edgeWeight[i];
                newVertices[i] = list;

                ++i;
            }
            newVertices[i] = newVertexList;
            newWeights[i] = newWeightList;

            vertices = newVertices;
            edgeWeight = newWeights;
        }
        else{    
            vertices[size] = newVertexList;
            edgeWeight[size] = newWeightList;
        }
        ++size;

    }

    @Override
    public void addEdge(int vertexID1, int vertexID2, double weight) {

        vertices[vertexID1].add((Vertex) vertices[vertexID2].get(0));
        edgeWeight[vertexID1].add((Double) weight);
        if (!isDirected()) {
            vertices[vertexID2].add((Vertex) vertices[vertexID1].get(0));
            edgeWeight[vertexID2].add((Double) weight);
        }
    
    }

    @Override
    public boolean removeEdge(int vertexID1, int vertexID2) {
        return vertices[vertexID1].remove((Vertex) vertices[vertexID2].get(0));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean removeVertex(int vertexID) {

       int i = 0;
       int index = 0;
       boolean isDeleted = false;

       List < Vertex > [] newVertices = new ArrayList[size];
       List < Double > [] newWeights = new ArrayList[size-1];

       Vertex removedVertex = (Vertex) vertices[vertexID].get(0);
       List<Double> Weightlist;
        
        for (List<Vertex> list : vertices) {

            Weightlist = edgeWeight[index];

           if (list == vertices[vertexID]){
                ++index;
                isDeleted = true;
                newVertices[i] = null;
                continue; //don't add vertex that to be deleted to new graph
           }
           //If any other vertex has deleted vertex, delete the edge too
           else if(list.contains( removedVertex )){
                Weightlist.remove(list.indexOf(removedVertex));
                list.remove( removedVertex );
           }
            newVertices[i] = list;
            newWeights[i] = Weightlist;
            ++i;  
            ++index;
                     
        }
        vertices = newVertices;
        edgeWeight = newWeights;
        size--;
        return isDeleted;


    }

    @Override
    public void removeVertex(String label) {

        for (int i = 0; i < vertices.length; i++) 
           if (vertices[i] != null && vertices[i].get(0).getLabel() == label)
               removeVertex(i);
        
    }

    @Override
    public MyGraph filterVertices(String key, String filter) {

        MyGraph subGraph = new MyGraph(5,false );
        Vertex tempV, tempV2;
        var count = 0;

        for (int i = 0; i < vertices.length; i++) {

            if(vertices[i] == null)
                continue;
            tempV = vertices[i].get(0);
            if(tempV!=null && tempV.getKey() == key && tempV.getValue() == filter){
                subGraph.vertices[count].add(tempV);
            
                for (int j = 0; j < vertices[i].size(); j++) {
                    tempV2 = vertices[i].get(j);
                    if(j != 0 && tempV2.getKey() == key && tempV2.getValue() == filter){
                        subGraph.vertices[count].add(tempV2);
                        subGraph.edgeWeight[count].add( edgeWeight[i].get(j));
                    }

                    else;
                }
                
                ++count;
            }
        }
        subGraph.size = count;
        return subGraph;
    }

  

    @Override
    public void printGraph() {

        System.out.println("Number of Vertices: "+getNumV());
        for (int i = 0; i < vertices.length; i++) {

            if(vertices[i] == null){
                System.out.println();
                break;
            }
            for (int j = 0; j < vertices[i].size(); j++) {

                if(vertices[i] == null){
                    System.out.println();
                    break;
                }
                if(j==0)
                    System.out.print("[" + vertices[i].get(j).getID() +"]" + "==>  "); 
                else
                    System.out.print("[" + vertices[i].get(j).getID() + ", w: "+edgeWeight[i].get(j)+"]" + "  ");
                }
                System.out.println();

            }

        System.out.println("|------------------------------------------|\n");

    }

   
    @Override
    public double[][] exportMatrix() {
        
        double[][] matrix;
        matrix = new double[size][];

        for (int i = 0; i < size; i++) {
            matrix[i] = new double[size];
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Double.POSITIVE_INFINITY;
            }
            
        }

        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < vertices[i].size(); j++) {
                matrix[i][vertices[i].get(j).getID()] = edgeWeight[i].get(j);
            }
        }

        return matrix;
    }

    /**
     * Prints the matrix
     * @param matrix Takes matrix representation of graph
     */
    public void printMatrix(double[][] matrix){

        int indx=0;

        System.out.printf("%-7c",' ');
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("%-7d",i);
        }
        System.out.println();

         for (double[] is : matrix) {
            System.out.printf("%-7d",indx);
             for (Double is2 : is) {
                 if(is2 == Double.POSITIVE_INFINITY)
                     System.out.printf("%-7c",'x');
                 else
                     System.out.printf("%-7.1f",is2);
             }
            indx++;
            System.out.println();
         }
    }


    @Override
    public int getNumV() {
        return size;
    }


    @Override
    public boolean isDirected() {
        return isDirected;
    }


    @Override
    public void insert(Edge edge) {
        addEdge(edge.getSource(), edge.getDest(), edge.getWeight());
    }


    @Override
    public boolean isEdge(int vertexID1, int vertexID2) {
        Vertex DestVertex = vertices[vertexID2].get(0);
        return vertices[vertexID1].contains(DestVertex);
    }


    @Override
    public Edge getEdge(int source, int dest) {
        Edge edge = new Edge(source, dest);
        return edge;
    }


    @Override
    public Iterator<Vertex> edgeIterator(int source) {
        return vertices[source].iterator();
    }


    /**
     * returns the edge of given vertices
     * @param source Id of the source
     * @param dest index of the neighbor
     * @return Returns the weight of asked edge
     */
    public double getEdgeWeight(int source, int dest) {
        return edgeWeight[source].get(dest);
        
    }

    /**
     * returns the edge of given vertices
     * @param source Id of the source
     * @param vertexID2 id of the destination
     * @return Returns the weight of asked edge
     */
    public double getEdgeWeightWithId(int source, int vertexID2) {

        int dest = vertices[source].indexOf( getVertex(vertexID2));
        if(dest == -1)
            return Double.POSITIVE_INFINITY;
    
        return edgeWeight[source].get(dest);
    }

    /**
     * Gets the vertex with given id
     * @param vertexID Id of the vertex
     * @return Returns the vertex which is in given index
     */
    public Vertex getVertex(int vertexID) {
        return vertices[vertexID].get(0);
    }

    /**
    * Finds neighbor vertex of given vertex with it's with and return it
     * @param vertexID id of the vertex
     * @param askedWeight  
     * @return Return the asked vertex
     */
    public Vertex getVertex(int vertexID, double askedWeight) {

        int index = 0;
        for (Double weight : edgeWeight[vertexID]) {
            if (weight == askedWeight)
                return vertices[vertexID].get(index);
            index++;
        }   

        return vertices[vertexID].get(0);
    }

    /**
     * Returns the arraylist of given index
     * @param vertexID id of the vertex
     * @return Returns the arraylist of given index
     */
    public List<Vertex> getVertexList(int vertexID) {
        return vertices[vertexID];
    }
}