public class Main {

    public static void main(String[] args) {

        AdjacencyGraph ag = new AdjacencyGraph();   // Creates adjaceny graph instance
        ag.createFromFile("townsAndDists.csv");     // Creates adjacency graph from csv file
        ag.MSTPrims();                              // Runs prims algorithm on adjacency graph

    }
}
