import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class AdjacencyGraph {
    ArrayList<Vertex> vertices;

    public AdjacencyGraph() {
        vertices = new ArrayList<Vertex>();
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    // Function that will create a minimum spanning tree based on Prim's algorithm
    public void MSTPrims(){
        // ArrayList represting our minimum spanning tree
        MSTNode[] mst = new MSTNode[vertices.size()];               
        // Using given MinHeap with pairs of vertices and their current distance
        MinHeap<Pair>        minheap = new MinHeap<>();            
        // A HashMap to keep Pairs from the minheap connected to their Vertex. Make loop-up quicker 
        HashMap<Vertex,Pair> pairs   = new HashMap<>();             
        // Loops through all vertices and adds them and their attributes to different data structures
        for (int i = 0; i < vertices.size(); i++){                  // O(V log V)
            Vertex currentVertex = vertices.get(i);
            Pair pair = new Pair(currentVertex, currentVertex.dist);
            pair.setVertex(currentVertex);
            pairs.put(currentVertex, pair);
            minheap.insert(pair);
        }
        
        // Choosing a starting node as setting its distance to 0 and predecessor to itself
        Pair startNode = minheap.getMinHeap().get(0);               
        startNode.setDistance(0);                                   
        startNode.vertex.setPred(startNode.vertex);                 
        int mst_pos = 0;                                            
        // 
        while(!minheap.isEmpty()){                                  // Total: O(E*log(V) + V*log(V)) = 
            // Extracting minimum vertex
            Pair    extractedNode = minheap.extractMin();           // O(log V)
            Vertex  extractedVertex = extractedNode.vertex;         
            // Add to MST
            MSTNode mstNode = new MSTNode(extractedVertex, extractedVertex.pred, extractedNode.distance);                                    
            mst[mst_pos] = mstNode;                                 
            mst_pos++;                                              
            // Go through each edge connected to current extracted node to update current distance and predecessor dependent on whether current distance is shorter
            for(Edge edge : extractedVertex.OutEdges){              // Total: O(E*log(V))
                if (edge.weight < edge.to.dist){
                    edge.to.setDist(edge.weight);                   
                    edge.to.setPred(edge.from);                     
                    Pair currentPair = pairs.get(edge.to);          
                    currentPair.setDistance(edge.to.dist);          
                    int pos = minheap.getPosition(currentPair);     
                    minheap.decreasekey(pos);                       // O(log(V))
                }
            }
        }
               
        // Adding together distances from the minimum spanning tree
        int total = 0;
        for (MSTNode node : mst){      // O(V)
            System.out.println(node);
            total += node.distance;
        }
        // Printing the tree and costs
        System.out.println("total distance: " + total + " km");
        System.out.printf(Locale.US, "total price:    %,d kroner %n", 100000*total);
    }

    // Given by lecturer
    public void addEdge(Vertex f, Vertex t, Integer w) {
        if (!(vertices.contains(f) && vertices.contains(t))) {
            System.out.println(" Vertex not in graph");
            return;
        }
        Edge e = new Edge(f, t, w);
    }

    // Function that takes a CSV file and adds the towns to a list of vertices as well as adding edges between them
    // Made by us
    public void createFromFile(String file) {
        String line    = "";
        String splitBy = ",";
        try{
            // Using BufferedReader to read CSV file. Inspired by: https://www.javatpoint.com/how-to-read-csv-file-in-java
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null)
            {    
                String[] connection = line.split(splitBy); 

                // The fromVertex is the towns on the left side of the given table. toVertex is the right side.
                Vertex fromVertex = null;
                Vertex toVertex   = null;
                boolean from_notAlreadyInList = true;
                boolean to_notAlreadyInList   = true;

                for (Vertex vertex : vertices) {
                    // If there is no vertex with the same name as the town, without creating a new vertex. There will just be new edges added to the vertices
                    if (vertex.name.equals(connection[0])){
                        fromVertex = vertex;
                        from_notAlreadyInList = false;
                    }
                    if (vertex.name.equals(connection[1])){
                        toVertex = vertex;
                        to_notAlreadyInList = false;
                    }
                }
                // If the the towns have not yet been made verticies, we make new vertices with the currents towns' names.
                if (from_notAlreadyInList){
                    fromVertex = new Vertex(connection[0]);
                    vertices.add(fromVertex);
                }
                if (to_notAlreadyInList){
                    toVertex = new Vertex(connection[1]);
                    vertices.add(toVertex);
                }
                // Adding edges to the current from/to vertices
                if (fromVertex!=null && toVertex!=null) {         
                    addEdge(fromVertex,toVertex,  Integer.valueOf(connection[2]));
                    addEdge(toVertex,  fromVertex,Integer.valueOf(connection[2]));
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();;
        }
    }

    public void PrintGraph() {
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println(" From Vertex: " + vertices.get(i).name);
            Vertex currentfrom = vertices.get(i);
            for (int j = 0; j < currentfrom.OutEdges.size(); j++) {
                Edge currentEdge = currentfrom.OutEdges.get(j);
                System.out.println(" To: " + currentEdge.to.name + " weight: " + currentEdge.weight);
            }
            System.out.println(" ");
        }
    }
}

// "Nodes" meant to represent a vertex and its connection to its parent to add to a mininimum spanning tree
class MSTNode {
    Vertex self;
    Vertex parent;
    Integer distance;

    public MSTNode(Vertex self, Vertex parent, Integer distance){
        this.self = self;
        this.parent = parent;
        this.distance = distance;
    }

    public String toString(){
        return String.format("From: %-15s to: %-15s distance: %-2s km" , parent.name, self.name, Integer.toString(distance));
    }

}

// Vertices as given by lecutrer. Added some setters
class Vertex implements Comparable<Vertex> {
    String name;
    ArrayList<Edge> OutEdges;
    Vertex pred = null;
    Integer dist = Integer.MAX_VALUE;

    public Vertex(String id) {
        this.name = id;
        OutEdges = new ArrayList<Edge>();
    }

    public void setPred(Vertex pred){
        this.pred = pred;
    }

    public void setDist(Integer dist){
        this.dist = dist;
    }
    public void setName(String name){
        this.name = name;
    }

    public void addOutEdge(Edge e) {
        OutEdges.add(e);
    }
    /*
    @Override
    public boolean equals(Object vertex) {
        if (this == vertex)
            return true;
        return false;
    }
    */

    @Override
    public int compareTo(Vertex o) {
        if (this.dist < o.dist)
            return -1;
        if (this.dist > o.dist)
            return 1;
        return 0;
    }
}


// Edge as given by lecturer
class Edge {
    Integer weight;
    Vertex from;
    Vertex to;

    public Edge(Vertex from, Vertex to, Integer cost) {
        this.from = from;
        this.to = to;
        this.weight = cost;
        this.from.addOutEdge(this); 
        this.to.addOutEdge(this);
    }
}

// A pair consisting of a vertex and its current distance. Used for prims in order to keep track of each vertex' current distance
class Pair implements Comparable<Pair>{
    Integer distance = Integer.MAX_VALUE;
    Vertex vertex;
 
    public Pair(Vertex vertex, Integer distance){
        this.distance = distance;
        this.vertex   = vertex;
    }

    public void setVertex(Vertex vertex){
        this.vertex = vertex;
    }
   
    public void setDistance(Integer distance){
        this.distance = distance;
    }

    @Override
    public int compareTo(Pair p){
        return this.distance.compareTo(p.distance);
    }
    
    public String toString(){
        return ("(" + distance + ", " + vertex.name + ")");
    }
}