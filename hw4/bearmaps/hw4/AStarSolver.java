package bearmaps.hw4;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private HashMap<Vertex, Double> vertexToDistance = new HashMap<>();
    private HashMap<Vertex, Vertex> vertexToPath = new HashMap<>();
    private double totTime;
    private int count;
    private SolverOutcome result;
    private Vertex destination;
    private Vertex begin;
    public ArrayList<Vertex> sol = new ArrayList<>();
    public double solWeight = 0;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        DoubleMapPQ<Vertex> pq = new DoubleMapPQ<>();
        HashSet<Vertex> marked = new HashSet<>();
        pq.add(start, 0);
        vertexToDistance.put(start, 0.0);
        destination = end;
        begin = start;

        Stopwatch timer = new Stopwatch();
        count = -1;
        while (pq.size() != 0) {
            Vertex p = pq.removeSmallest();
            marked.add(p);
            count += 1;
            if (p.equals(end)) {
                result = SolverOutcome.SOLVED;
                totTime = timer.elapsedTime();
                solWeight = vertexToDistance.get(p);
                Vertex curr = destination;
                while (!curr.equals(begin)) {
                    sol.add(0, curr);
                    curr = vertexToPath.get(curr);
                }
                sol.add(0, curr);
                return;
            }
            if (timer.elapsedTime() >= timeout) {
                result = SolverOutcome.TIMEOUT;
                totTime = timeout;
                return;
            }
            List<WeightedEdge<Vertex>> allEdgesOut = input.neighbors(p);
            for (WeightedEdge<Vertex> edge: allEdgesOut) {
                Vertex dest = edge.to();
                if (!marked.contains(dest)) {
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

        }
        result = SolverOutcome.UNSOLVABLE;
        totTime = timer.elapsedTime();

    }
    public SolverOutcome outcome() {
        return result;
    }
    public List<Vertex> solution() {
        return sol;
    }
    public double solutionWeight() {
        return solWeight;
    }
    public int numStatesExplored() {
        return count;
    }
    public double explorationTime() {
        return totTime;
    }
}
