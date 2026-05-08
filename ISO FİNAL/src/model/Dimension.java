package model;
 
import java.util.ArrayList;
import java.util.List;
 
/**
 * Bir kalite boyutunu temsil eder (örn: Usability, Reliability).
 * Birden fazla Metric içerir.
 */
public class Dimension {
 
    private String       name;
    private int          coefficient;   // Senaryo içindeki boyut ağırlığı
    private List<Metric> metrics;
 
    public Dimension(String name, int coefficient) {
        this.name        = name;
        this.coefficient = coefficient;
        this.metrics     = new ArrayList<>();
    }
 
    public void addMetric(Metric metric) {
        metrics.add(metric);
    }
 
    /**
     * Boyut skoru = Σ(metricScore × metricCoeff) / Σ(metricCoeff)
     */
    public double calculateDimensionScore() {
        double weightedSum = 0.0;
        int    totalCoeff  = 0;
        for (Metric m : metrics) {
            weightedSum += m.calculateScore() * m.getCoefficient();
            totalCoeff  += m.getCoefficient();
        }
        if (totalCoeff == 0) return 0;
        return weightedSum / totalCoeff;
    }
 
    // ── Getter ────────────────────────────────────────────────────────────────
    public String       getName()        { return name; }
    public int          getCoefficient() { return coefficient; }
    public List<Metric> getMetrics()     { return metrics; }
}
 