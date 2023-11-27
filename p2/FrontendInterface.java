public interface FrontendInterface {
  
  /**
   * Constructor to initialize the frontend with a reference to the backend and a Scanner instance
   *
   * @param backend a reference to the backend
   * @param scanner a scanner instance to read the user's input
   */
  /*
  Backend backend = null;
  Scanner scanner = null;
  public Frontend(Backend backend, Scanner scanner) {
    this.backend = backend;
    this.scanner = scanner;
  }
  */


  /**
   * runs the main menu command loop
   */
  public void runMainCommandLoop();
  
  /**
   * loads data from a string filepath
   * @param filepath representation of the filepath
   */
  public void loadData(String filepath);


  /**
   * returns the main text menu with instructions for user input
   */
  public void showMainMenu();
  
  /**
   * Returns a string displaying the statistics about the dataset that includes the number of nodes
   * (buildings), the number of edges, and the total walking time (sum of weights) for all edges in 
   * the graph.
   */
  public String graphStats();

  /**
   * Returns string displaying the shortest path from start building to destination building,
   * including all buildings on the way, estimated walking time for each segment, and the total
   * time it takes to walk the path
   * @param start - name of building to start path from
   * @param destination - destination building to find path to
   */
  public String shortestPath(String start, String destination);


  /**
   * exits the program
   * @param loopCondition - boolean var which will be turned to false. This is the var that is 
   * used by the mainCommandLoop to keep the loop running. Exit method will turn it to false.
   */
  //public void exit(boolean loopCondition);


}
