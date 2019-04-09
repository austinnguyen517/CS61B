package bearmaps.hw4;
import bearmaps.proj2ab.DoubleMapPQ;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private HashMap<Vertex, Double> vertexToDistance = new HashMap<>();
    private HashMap<Vertex, Vertex> vertexToPath = new HashMap<>();
    private double totTime;
    private int count;
    private SolverOutcome result;
    private Vertex destination;
    private Vertex begin;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        DoubleMapPQ<Vertex> pq = new DoubleMapPQ<>();
        pq.add(start, 0);
        vertexToDistance.put(start, 0.0);
        destination = end;
        begin = start;

        long beginTime = System.nanoTime();
        count = 0;
        while (pq.size() != 0) {
            long currTime = System.nanoTime();
            if (((double) (currTime - beginTime) / 1000000000) >= timeout) {
                result = SolverOutcome.TIMEOUT;
                totTime = timeout;
                break;
            }
            Vertex p = pq.removeSmallest();
            count += 1;
            if (p.equals(end)) {
                result = SolverOutcome.SOLVED;
                totTime = (double) System.nanoTime() - beginTime;
                break;
            }
            List<WeightedEdge<Vertex>> allEdgesOut = input.neighbors(p);
            for (WeightedEdge<Vertex> edge: allEdgesOut) {
                Vertex dest = edge.to();
                double w = edge.weight();
                if (!vertexToDistance.containsKey(dest)
                        || (vertexToDistance.get(p) + w < vertexToDistance.get(dest))) {
                    vertexToDistance.put(dest, vertexToDistance.get(p) + w);
                    vertexToPath.put(dest, p);
                    if (pq.contains(dest)) {
                        pq.changePriority(dest, vertexToDistance.get(p) + w
                                + input.estimatedDistanceToGoal(dest, end));
                    } else {
                        pq.add(dest, vertexToDistance.get(p) + w
                                + input.estimatedDistanceToGoal(dest, end));
                    }

                }
            }
        }
        if (pq.size() == 0) {
            result = SolverOutcome.UNSOLVABLE;
            totTime = (double) System.nanoTime() - beginTime;
        }

    }
    public SolverOutcome outcome() {
        return result;
    }
    public List<Vertex> solution() {
        if (result.equals(SolverOutcome.TIMEOUT) || result.equals(SolverOutcome.UNSOLVABLE)) {
            return new ArrayList<>();
        }
        Vertex curr = destination;
        LinkedList<Vertex> ret = new LinkedList<>();
        while (curr != begin) {
            ret.addFirst(curr);
            curr = vertexToPath.get(curr);
        }
        ret.addFirst(curr);

        return ret;
    }
    public double solutionWeight() {
        if (result.equals(SolverOutcome.TIMEOUT) || result.equals(SolverOutcome.UNSOLVABLE)) {
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
