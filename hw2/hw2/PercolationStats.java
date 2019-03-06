package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] fractions;
    private int times;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Invalid N or T");
        }

        times = T;

        fractions = new double[T];

        for (int i = 0; i < T; i += 1) {
            Percolation test = pf.make(N);
            while (!test.percolates()) {
                test.open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            double count = test.numberOfOpenSites();
            fractions[i] = count / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(fractions);
    }

    public double stddev() {
        return StdStats.stddev(fractions);
    }

    public double confidenceLow() {
        return mean() - ((1.96 * stddev()) / (Math.sqrt(times)));
    }

    public double confidenceHigh() {
        return mean() + ((1.96 * stddev()) / (Math.sqrt(times)));
    }
}
