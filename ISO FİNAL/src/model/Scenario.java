package model;
 
import java.util.ArrayList;
import java.util.List;
 
/**
 * Bir ölçüm senaryosunu temsil eder.
 * Birden fazla Dimension içerir; mode ve qualityType bilgisini taşır.
 */
public class Scenario {
 
    private String          name;
    private String          mode;          // "Health" veya "Education"
    private String          qualityType;   // "Product" veya "Process"
    private List<Dimension> dimensions;
 
    public Scenario(String name, String mode, String qualityType) {
        this.name        = name;
        this.mode        = mode;
        this.qualityType = qualityType;
        this.dimensions  = new ArrayList<>();
    }
 
    public void addDimension(Dimension dimension) {
        dimensions.add(dimension);
    }
 
    // ── Getter ────────────────────────────────────────────────────────────────
    public String          getName()        { return name; }
    public String          getMode()        { return mode; }
    public String          getQualityType() { return qualityType; }
    public List<Dimension> getDimensions()  { return dimensions; }
}