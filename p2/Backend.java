import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Backend implements BackendInterface {
    private GraphADT<String, Number> graph;
    String storeStartBuilding;
    String storeEndBuilding;

    public Backend(GraphADT<String, Number> graph) {
        this.graph = graph;
    }

    @Override
    public boolean readDOTData(String filePath) throws IOException, FileNotFoundException {
        File file = null;
        boolean val = false;
        BufferedReader bf = null;
        try {
            file = new File(filePath);
            bf = new BufferedReader(new FileReader(file));
            String line;
            // Pattern pattern = Pattern.compile("seconds");
            while ((line = bf.readLine()) != null) {
                if (line.contains("--")) {
                    String[] parts = line.split("--|\\[");
                    String start = parts[0].trim();
                    start = start.replaceAll("\"", "");
                    String end = parts[1].trim();
                    end = end.replaceAll("\"", "");
                    String doubleVal = parts[2].trim().replace("seconds=", "");
                    doubleVal = doubleVal.replaceAll("\\];", "");
                    double weight = Double.parseDouble(doubleVal);
                    graph.insertNode(start);
                    graph.insertNode(end);
                    graph.insertEdge(start, end, weight);
                    graph.insertEdge(end, start, weight);
                    val = true;
                }
            }
            return val;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        } finally {
            bf.close();
        }
    }

    @Override
    public ShortestPathInterface getShortestPath(String startBuilding, String destinationBuilding)
            throws IllegalArgumentException,
            NoSuchElementException {
        this.storeStartBuilding = startBuilding;
        this.storeEndBuilding = destinationBuilding;

        try {
            List<String> pathData = graph.shortestPathData(startBuilding, destinationBuilding);
            // System.out.println(pathData.toString());

            double pathCost = graph.shortestPathCost(startBuilding, destinationBuilding);
            // System.out.println(pathCost);

            List<Number> getWalkingTimes = new ArrayList<>();
            for (int i = 0; i < pathData.size() - 1; i++) {
                getWalkingTimes.add(graph.getEdge(pathData.get(i), pathData.get(i + 1)));
            }
            // System.out.println(getWalkingTimes.toString());

            ShortestPath realPath = new ShortestPath(pathData, getWalkingTimes, pathCost);
            realPath.paths = pathData;
            realPath.getWalkingTimes = getWalkingTimes;
            realPath.getTotalPathCost = pathCost;

            return realPath;

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getPathStats() {
        ShortestPathInterface shortPath = getShortestPath(storeStartBuilding, storeEndBuilding);
        if (shortPath == null) {
            return "Could be no Path or Wrong input";
        }
        double TotaledgeCount = shortPath.getTotalPathCost();
        int minutes = (int) TotaledgeCount / 60;

        String returnVal = "";
        for (int i = 0; i < shortPath.getPath().size() - 1; i++) {
            returnVal += shortPath.getPath().get(i);
            for (int j = 0; j < 1; j++) {
                returnVal += "--" + shortPath.getWalkingTimes().get(i) + "s--";
            }
        }
        returnVal += shortPath.getPath().get(shortPath.getPath().size() - 1) + "\nEstimated WalkTime: " + TotaledgeCount
                + " seconds 0R ~" + minutes + " minutes";
        // return "Number of Buildings : " + building + "\n" + "Number of WalkingTime
        // between Buidlings: " + walkTime
        // + "\n" + "Total walktime: "
        // + TotaledgeCount;
        return returnVal;
    }

    public static void main(String[] args) throws NullPointerException, IOException {
        GraphADT<String, Number> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        BackendInterface backend = new Backend(graph);
        backend.readDOTData("campus.dot");
        backend.getShortestPath("Memorial Union", "Sterling Hall");
        String s = backend.getPathStats();
        System.out.println(s);
    }

}
