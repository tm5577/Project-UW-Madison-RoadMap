// --== CS400 File Header Information ==--
// Name: <Tahmid Mahbub>
// Email: <tmahbub@wisc.edu>
// Group and Team: <your group name: G21>
// Group TA: <name of your group's ta>
// Lecturer: <Florine>
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node start, double cost, SearchNode predecessor) {
            this.node = start;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     * 
     * @param map the map that the graph uses to map a data object to the node
     *            object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        if (!containsNode(start) || !containsNode(end)) {
            throw new NoSuchElementException("Does not contain either " + start + " or " + end);
        }
        PriorityQueue<SearchNode> pq = new PriorityQueue<>();
        PlaceholderMap<Node, Node> visitedNode = new PlaceholderMap<>();
        SearchNode intialNode = new SearchNode(nodes.get(start), 0, null);

        pq.add(intialNode);

        while (!pq.isEmpty()) {
            SearchNode currentNode = pq.poll();
            if (currentNode.node.data.equals((end))) {
                return currentNode;
            }

            if (!visitedNode.containsKey(currentNode.node)) {
                visitedNode.put(currentNode.node, currentNode.node);
            }
            for (Edge edge : currentNode.node.edgesLeaving) {
                if (!visitedNode.containsKey(edge.successor)) {
                    pq.add(new SearchNode(edge.successor, currentNode.cost + edge.data.doubleValue(), currentNode));
                }
            }

        }
        throw new NoSuchElementException("No path found from " + start + " to " + end);
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        List<NodeType> pathData = new ArrayList<>();
        SearchNode curr = computeShortestPath(start, end);
        while (curr != null) {
            pathData.add(0, curr.node.data);
            curr = curr.predecessor;
        }
        return pathData;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        SearchNode curr = computeShortestPath(start, end);
        return curr.cost;
    }

    // TODO: implement 3+ tests in step 4.1
    /**
     * this test check that if the computeShortestPath return correct path and
     * correct cost.
     * This method also checks for the valid implementation of shortestPathCost()
     * and shortestPathData() method.
     */
    @Test
    public void testComputeShortestPathAndCost() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("B", "E", 10);
        graph.insertEdge("B", "D", 1);
        graph.insertEdge("C", "D", 5);
        graph.insertEdge("D", "F", 0);
        graph.insertEdge("F", "D", 2);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("F", "H", 4);
        graph.insertEdge("G", "H", 4);

        { // Path from A to E
            ArrayList<String> pathData = new ArrayList<>();
            DijkstraGraph<String, Integer>.SearchNode current = graph.computeShortestPath("A", "E");
            Assertions.assertEquals(8, current.cost);

            while (current != null) {
                pathData.add(0, current.node.data);
                current = current.predecessor;
            }

            Assertions.assertEquals(graph.shortestPathData("A", "E").toString(), pathData.toString());
        }
        { // Path from A to F
            ArrayList<String> pathData = new ArrayList<>();
            DijkstraGraph<String, Integer>.SearchNode current = graph.computeShortestPath("A", "F");
            Assertions.assertEquals(5, current.cost);

            while (current != null) {
                pathData.add(0, current.node.data);
                current = current.predecessor;
            }

            Assertions.assertEquals(graph.shortestPathData("A", "F").toString(), pathData.toString());
        }

    }

    /**
     * This test check for the valid function of shortestPathCost method if the cost
     * doesnt exist.
     */
    @Test
    public void testShortestPathCost() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");

        graph.insertEdge("A", "B", 2);
        graph.insertEdge("B", "C", 3);
        graph.insertEdge("D", "C", 2);
        { // Testing shortestPathCost method on a tree that doesnt have a shortest path
            Assertions.assertThrows(NoSuchElementException.class, () -> graph.shortestPathCost("A", "D"));
        }
        { // Testing shortestPathData method on a tree that doesnt have shortest path
            Assertions.assertThrows(NoSuchElementException.class, () -> graph.shortestPathData("A", "D"));
        }

    }

    /**
     * This is a test if shortestPathData return correct list, if there are one node
     * in the Graph.
     */
    @Test
    public void testOneNodeGraph() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode("A");
        // graph.insertNode("B");

        // graph.insertEdge(null, null, 0);
        // graph.insertEdge("A", null, 3);

        List<String> shortPath = graph.shortestPathData("A", "A");

        Assertions.assertEquals("[A]", shortPath.toString());
    }

    /**
     * This is a test if shortestPathData return proper error if an input is given
     * that doesnt exit in the graph
     */
    @Test
    public void testOneNodeGraphWithWrongNode() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");

        graph.insertEdge("A", "B", 2);
        graph.insertEdge("B", "C", 3);
        graph.insertEdge("D", "C", 2);

        // Testing shortestPathData method on a tree that doesnt have shortest path
        Assertions.assertThrows(NoSuchElementException.class, () -> graph.shortestPathData("Q", "D"));

    }

}
