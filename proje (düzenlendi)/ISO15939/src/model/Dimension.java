package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a quality dimension that contains multiple metrics.
 */
public class Dimension {

    private String name;
    private int coefficient;   // weight within its scenario
    private List<Metric> metrics;

    public Dimension(String name, int coefficient) {
        this.name = name;
        this.coefficient = coefficient;
        this.metrics = new ArrayList<>();
    }

    public void addMetric(Metric metric) {
        metrics.add(metric);
    }

    /**
     * Weighted average of metric scores within this dimension.
     * dimensionScore = Σ(metricScore × metricCoeff) / Σ(metricCoeff)
     */
    public double calculateScore() {
        double weightedSum = 0;
        int totalCoeff = 0;
        for (Metric m : metrics) {
            weightedSum += m.calculateScore() * m.getCoefficient();
            totalCoeff  += m.getCoefficient();
        }
        if (totalCoeff == 0) return 0;
        return weightedSum / totalCoeff;
    }

    public String getName()          { return name; }
    public int getCoefficient()      { return coefficient; }
    public List<Metric> getMetrics() { return metrics; }
}
