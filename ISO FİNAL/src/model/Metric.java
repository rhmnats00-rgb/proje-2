package model;
 
/**
 * Bir boyut (dimension) içindeki tek bir ölçüm metriğini temsil eder.
 */
public class Metric {
 
    private String  name;
    private int     coefficient;       // Boyut içindeki ağırlık
    private boolean higherIsBetter;    // true → yüksek iyi, false → düşük iyi
    private double  rangeMin;
    private double  rangeMax;
    private String  unit;
    private double  value;             // Hard-coded ham veri değeri
 
    public Metric(String name, int coefficient, boolean higherIsBetter,
                  double rangeMin, double rangeMax, String unit) {
        this.name           = name;
        this.coefficient    = coefficient;
        this.higherIsBetter = higherIsBetter;
        this.rangeMin       = rangeMin;
        this.rangeMax       = rangeMax;
        this.unit           = unit;
    }
 
    /**
     * ISO 15939 formülüne göre 1-5 arası skor hesaplar.
     * Sonuç 0.5'in katlarına yuvarlanır.
     */
    public double calculateScore() {
        double raw;
        if (higherIsBetter) {
            raw = 1.0 + (value - rangeMin) / (rangeMax - rangeMin) * 4.0;
        } else {
            raw = 5.0 - (value - rangeMin) / (rangeMax - rangeMin) * 4.0;
        }
        raw = Math.max(1.0, Math.min(5.0, raw));          // 1-5 aralığına sıkıştır
        return Math.round(raw * 2.0) / 2.0;               // 0.5'e yuvarla
    }
 
    /** Tablo için yön etiketi döner: "Higher ↑" veya "Lower ↓" */
    public String getDirectionLabel() {
        return higherIsBetter ? "Higher \u2191" : "Lower \u2193";
    }
 
    /** Tablo için aralık etiketi döner: "0-100" gibi */
    public String getRangeLabel() {
        if (rangeMin == (long) rangeMin && rangeMax == (long) rangeMax) {
            return (long) rangeMin + "-" + (long) rangeMax;
        }
        return rangeMin + "-" + rangeMax;
    }
 
    // ── Getter / Setter ───────────────────────────────────────────────────────
    public String  getName()           { return name; }
    public int     getCoefficient()    { return coefficient; }
    public boolean isHigherIsBetter()  { return higherIsBetter; }
    public double  getRangeMin()       { return rangeMin; }
    public double  getRangeMax()       { return rangeMax; }
    public String  getUnit()           { return unit; }
    public double  getValue()          { return value; }
    public void    setValue(double v)  { this.value = v; }
}