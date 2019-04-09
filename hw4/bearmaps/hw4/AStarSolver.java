package bearmaps.hw4;
import bearmaps.proj2ab.ArrayHeapMinPQ;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private HashMap<Vertex, Double> vertexToDistance = new HashMap<>();
    private HashMap<Vertex, Vertex> vertexToPath = new HashMap<>();
    private double totTime;
    private int count;
    private SolverOutcome result;
    private Vertex destination;
    private Vertex begin;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        ArrayHeapMinPQ<Vertex> pq = new ArrayHeapMinPQ<>();
        pq.add(start, 0);
        vertexToDistance.put(start, 0.0);
        destination = end;
        begin = start;

        Stopwatch timer = new Stopwatch();
        count = 0;
        while (pq.size() != 0) {
            if (timer.elapsedTime() >= timeout) {
                result = SolverOutcome.TIMEOUT;
                totTime = timeout;
                break;
            }
            Vertex p = pq.removeSmallest();
            count += 1;
            if (p.equals(end)) {
                result = SolverOutcome.SOLVED;
                totTime = timer.elapsedTime();
                break;
            }
            List<WeightedEdge<Vertex>> allEdgesOut = input.neighbors(p);
            for (WeightedEdge<Vertex> edge: allEdgesOut) {
                Vertex dest = edge.to();
                double w = edge.weight();
                double distP = vertexToDistance.get(p);
                if (!vertexToDistance.containsKey(dest)
                        || (distP + w < vertexToDistance.get(dest))) {
                    vertexToDistance.put(dest, distP + w);
                    vertexToPath.put(dest, p);
                    if (pq.contains(dest)) {
                        pq.changePriority(dest, distP + w
                                + input.estimatedDistanceToGoal(dest, end));
                    } else {
                        pq.add(dest, distP + w
                                + input.estimatedDistanceToGoal(dest, end));
                    }

                }
            }
        }
        if (pq.size() == 0 && result != SolverOutcome.SOLVED && result != SolverOutcome.TIMEOUT) {
            result = SolverOutcome.UNSOLVABLE;
            totTime = timer.elapsedTime();
        }

    }
    public SolverOutcome outcome() {
        return result;
    }
    public List<Vertex> solution() {
        if (!result.equals(SolverOutcome.SOLVED)) {
            return new ArrayList<>();
        }
        Vertex curr = destination;
        ArrayList<Vertex> ret = new ArrayList<>();
        while (curr != begin) {
            ret.add(0, curr);
            curr = vertexToPath.get(curr);
        }
        ret.add(0, curr);

        return ret;
    }
    public double solutionWeight() {
        if (!result.equals(SolverOutcome.SOLVED)) {
            return 0;
        }
        return vertexToDistance.get(destination);
    }
    public int numStatesExplored() {
        return count;
    }
    public double explorationTime() {
        return totTime;
    }
}
