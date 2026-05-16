package model;

/**
 * Represents a single measurement metric within a quality dimension.
 */
public class Metric {

    private String name;
    private int coefficient;      // weight within its dimension
    private String direction;     // "Higher" or "Lower"
    private double rangeMin;
    private double rangeMax;
    private String unit;
    private double value;         // raw collected value

    public Metric(String name, int coefficient, String direction,
                  double rangeMin, double rangeMax, String unit, double value) {
        this.name = name;
        this.coefficient = coefficient;
        this.direction = direction;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        this.unit = unit;
        this.value = value;
    }

    /**
     * Calculates the score (1-5) based on direction and range.
     * Result is rounded to nearest 0.5.
     */
    public double calculateScore() {
        double raw;
        if (direction.equalsIgnoreCase("Higher")) {
            raw = 1.0 + (value - rangeMin) / (rangeMax - rangeMin) * 4.0;
        } else {
            raw = 5.0 - (value - rangeMin) / (rangeMax - rangeMin) * 4.0;
        }
        // Clamp between 1.0 and 5.0
        raw = Math.max(1.0, Math.min(5.0, raw));
        // Round to nearest 0.5
        return Math.round(raw * 2.0) / 2.0;
    }

    public String getName()        { return name; }
    public int getCoefficient()    { return coefficient; }
    public String getDirection()   { return direction; }
    public double getRangeMin()    { return rangeMin; }
    public double getRangeMax()    { return rangeMax; }
    public String getUnit()        { return unit; }
    public double getValue()       { return value; }

    public String getRangeString() {
        return (int) rangeMin + "–" + (int) rangeMax;
    }

    public String getDirectionArrow() {
        return direction.equalsIgnoreCase("Higher") ? "Higher ↑" : "Lower ↓";
    }
}
