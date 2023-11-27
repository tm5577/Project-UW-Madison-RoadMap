import java.util.List;

/**
 * This interface for a class that stores the results of a shortest path search.
 * It
 * should have a getter method for the path (stored as a list of buildings along
 * the path),
 * a list of the walking times of the path segments (the time it takes to walk
 * from one building to the next), and
 * the total path cost as the estimated time it takes to walk from the start to
 * the destination building.
 * 
 */
public interface ShortestPathInterface {
    /**
     * This method return a list of Strings that represent building names along the
     * path
     * from start to destination.
     * 
     * @return a list with building names
     */
    public List<String> getPath();

    /**
     * a list of the walking times of the path segments; the time it takes to walk
     * from one building to the next
     * 
     * @return a list of double rep walkingTimes
     */
    public List<Number> getWalkingTimes();

    /**
     * the total path cost as the estimated time it takes to walk from the start to
     * the destination building
     * 
     * @return a double rep the totalPathCost
     */
    public double getTotalPathCost();
}
