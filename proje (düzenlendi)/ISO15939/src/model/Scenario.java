package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a measurement scenario (e.g. "Scenario C — Team Alpha").
 */
public class Scenario {

    private String name;
    private List<Dimension> dimensions;

    public Scenario(String name) {
        this.name = name;
        this.dimensions = new ArrayList<>();
    }

    public void addDimension(Dimension dimension) {
        dimensions.add(dimension);
    }

    public String getName()                { return name; }
    public List<Dimension> getDimensions() { return dimensions; }
}
