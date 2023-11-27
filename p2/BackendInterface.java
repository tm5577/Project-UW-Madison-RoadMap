import java.io.IOException;
import java.util.NoSuchElementException;

public interface BackendInterface {

	/**
	 * Constructor for backend which takes an instance of GraphADT to use for
	 * shortest path searches
	 * 
	 * @param graph the instance of GraphADT that the Backend will use
	 */
	// public Backend(GraphADT<String, Number> graph) {
	//
	// }

	/**
	 * Reads valid UW Path data from a file (specifically a DOT file) and inserts
	 * the data into the graph
	 * 
	 * @param filePath is a String that contains the path of the file containing
	 *                 data the user wants to read in
	 * @return boolean indicating whether data could be successfully read in and
	 *         inserted into the graph. True if data load was successful, and
	 *         false if the data was not formatted properly, causing issues when
	 *         trying to read in data and insert it into the graph.
	 * @throws IOException          when the filePath the user provided does not
	 *                              lead to a file
	 * @throws NullPointerException when data for a node (building) that is read in
	 *                              is null, and cannot be inserted
	 */
	public boolean readDOTData(String filePath) throws IOException, NullPointerException;

	/**
	 * Returns the shortest path between two buildings using Dijkstra's shortest
	 * path algorithm. If the user inputs the same building as the start
	 * and destination, the shortestPath list of buildings should be an empty list
	 * as well as the shortestPath list of walking times. The total
	 * cost (walking time) from the start to the destination should also be zero.
	 * 
	 * @param start       is a String that contains the name of the building the
	 *                    user wants to start at
	 * @param destination is a String containing the destination (the building) the
	 *                    user wants to end up at
	 * @return an instance of ShortestPathInterface which contains information about
	 *         the shortest path, or null if no data has been loaded in yet.
	 * @throws IllegalArgumentException when the start or destination are not
	 *                                  buildings that exist after loading in the
	 *                                  data
	 * @throws NoSuchElementException   when the start and destination buildings
	 *                                  exist, but there is no path between them
	 */
	public ShortestPathInterface getShortestPath(String startBuilding, String destinationBuilding)
			throws IllegalArgumentException,
			NoSuchElementException;

	/**
	 * Returns a string with statistics about the dataset that includes the number
	 * of nodes (buildings) in the dataset, the number of edges in the
	 * dataset, the total walking time (sum of weights) for all edges in the graph.
	 * 
	 * @return String with statistics about the dataset or null if data has not been
	 *         loaded in yet
	 */
	public String getPathStats();
}
