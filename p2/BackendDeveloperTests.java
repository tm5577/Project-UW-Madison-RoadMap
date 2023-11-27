
import org.junit.jupiter.api.Test;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;

/**
 * This class contain junit testers which check the validation of
 * backendInterface's
 * method
 */
public class BackendDeveloperTests {

    GraphADT<String, Number> graph = new DijkstraGraph<>(new PlaceholderMap<>());
    // Constructer
    BackendInterface backend = new Backend(graph);

    /**
     * This tester method check if readDOTData method load the file when a valid
     * parameter is provided.
     * return true if it does, false otherwise
     */
    @Test
    public void readFromDotFileTest() {
        try {
            Assertions.assertTrue(backend.readDOTData("campus.dot"));
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method check that proper functionlity of throws when passed in wrong
     * input.
     */
    @Test
    public void invaldReadFromDOTFileTest() {

        try {
            Assertions.assertEquals(false, backend.readDOTData("compasu.dot"));
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method check if passed in null throw the right exception.
     */
    @Test
    public void nullReadFromDOTFileTest() {
        try {
            Assertions.assertEquals(false, backend.readDOTData(null));
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method return if the backend pathStats return the correct string.
     */
    @Test
    public void getPathStats() {
        try {
            backend.readDOTData("campus.dot");
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        backend.getShortestPath("Bascom Hall", "Computer Sciences and Statistics");
        Assertions.assertEquals(
                "Bascom Hall--106.3s--Van Vleck Hall--137.5s--Medical Sciences--76.6s--Service Memorial Institute--197.19999999999996s--The Crossing--179.10000000000002s--Meiklejohn House--164.20000000000002s--Computer Sciences and Statistics\n"
                        +
                        "Estimated WalkTime: 860.9 seconds 0R ~14 minutes",
                backend.getPathStats());
    }

    /**
     * This method return if the backend pathStats return the correct string if
     * there is one path in the building.
     */
    @Test
    public void getPathStatsWithOnePath() {
        try {
            backend.readDOTData("campus.dot");
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        backend.getShortestPath("Memorial Union", "Science Hall");
        Assertions.assertEquals(
                "Memorial Union--105.8s--Science Hall\nEstimated WalkTime: 105.8 seconds 0R ~1 minutes",
                backend.getPathStats());
    }

    /**
     * This method return if the backend pathStats return the correct string if
     * both input are same.
     */
    @Test
    public void getPathStatsWithBothSameInput() {
        try {
            backend.readDOTData("campus.dot");
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        backend.getShortestPath("Memorial Union", "Memorial Union");
        Assertions.assertEquals(
                "Memorial Union\nEstimated WalkTime: 0.0 seconds 0R ~0 minutes",
                backend.getPathStats());
    }

    /**
     * This method check if the unknown building data is passed would it throw the
     * right error.
     */
    @Test
    public void invalidBuildingParameter() {
        try {
            backend.readDOTData("campus.dot");
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        backend.getShortestPath("Beachhouse", "malinu");
        // Assertions.assertEquals(null, backend.getShortestPath("Beachhouse",
        // "malibu"));
        Assertions.assertEquals("Could be no Path or Wrong input", backend.getPathStats());
    }
}