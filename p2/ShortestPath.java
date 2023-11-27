import java.util.List;

public class ShortestPath implements ShortestPathInterface {

    List<String> paths;
    List<Number> getWalkingTimes;
    double getTotalPathCost;

    public ShortestPath(List<String> paths, List<Number> getWalkTimes, double pathCost) {
        this.paths = paths;
        this.getWalkingTimes = getWalkTimes;
        this.getTotalPathCost = pathCost;
    }

    @Override
    public List<String> getPath() {
        return this.paths;
    }

    @Override
    public List<Number> getWalkingTimes() {
        return this.getWalkingTimes;
    }

    @Override
    public double getTotalPathCost() {
        return this.getTotalPathCost;
    }
}
